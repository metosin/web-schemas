(ns web-schema.coerce-test
  (:require #+clj [clojure.test :refer [deftest testing is]]
    #+cljs [cljs.test :as test :refer-macros [deftest testing is]]
            [web-schema.coerce :as ws]
            [schema.core :as s])
  (:import [java.util.regex Pattern]
           [java.util Date]
           [org.joda.time DateTime LocalDate]))

(s/defn round-robin [p t x]
  (let [->identity (condp = t
                     Pattern str
                     Double double
                     Float float
                     identity)
        serialized (ws/serialize p x)
        de-serialized (ws/de-serialize p serialized)
        coerced (ws/coerce (ws/matcher p) t de-serialized)
        coerced-class (class coerced)
        assertion (= (->identity x) (->identity coerced))]
    [p assertion (s/explain t) x serialized de-serialized coerced-class coerced]))

(def tests
  {:protocols [:json]
   :values {String     ["kikka"]
            Integer    [123]
            s/Bool     [true]

            Pattern    [#".*"]
            s/Int      [123]
            Long       [123]
            Double     [1, 1.2]

            s/Inst     [(Date.)]
            DateTime   [(DateTime.)]
            LocalDate  [(LocalDate.)]

            Float      [1, 1.2]
            BigDecimal [123M, 1.2M]
            BigInteger [123]
            }})

;;
;; assertions -> tests
;;

(doseq [p (:protocols tests)
        [t xs] (:values tests)
        x xs
        :let [[_ pass :as rr] (round-robin p t x)]]
  (println rr)
  (assert (true? pass) (pr-str rr)))
