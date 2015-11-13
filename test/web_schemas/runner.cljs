(ns web-schemas.runner
  (:require [cljs.test :as test]
            [cljs.nodejs :as nodejs]
            web-schemas.transit-test))

(nodejs/enable-util-print!)

(def status (atom nil))

(defn -main []
  (test/run-all-tests #"^web-schemas.*-test$")
  (js/process.exit @status))

(defmethod test/report [:cljs.test/default :end-run-tests] [m]
  (reset! status (if (test/successful? m) 0 1)))

(set! *main-cli-fn* -main)
