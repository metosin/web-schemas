(ns web-schema.coerce
  (:require [schema.core :as s]
            [schema.coerce :as sc]
            [cheshire.core :as c]
            [cheshire.generate :as cg]
            [cheshire.generate :as cg])
  (:import [java.util.regex Pattern]
           [java.util Date]
           [com.fasterxml.jackson.core JsonGenerator]
           [org.joda.time DateTime LocalDate]
           [org.joda.time.format ISODateTimeFormat]))

(set! *warn-on-reflection* true)

; (def types (s/enum :clojure :json :transit :edn :yaml :string))

;;
;; Protocols
;;

(def ->JSON cg/JSONable)

#_(defprotocol <-JSON
  (from-json [this]))

;;
;; coercer
;;

(defn coerce [matcher schema value]
  (let [coercer (sc/coercer schema matcher)
        coerced (coercer value)]
    coerced))

(defn- safe-str [f]
  (sc/safe
    (fn [x]
      (if (string? x)
        (f x)))))

;;
;; Types
;;

; Pattern

(defn str->pattern ^Pattern [s] (re-pattern s))
(defn pattern->str ^String [pattern] (str pattern))

(extend-type Pattern
  ->JSON
  (to-json [x ^JsonGenerator jg] (.writeString jg (pattern->str x)))
  ;<-JSON
  ;(from-json [_] (safe-str str->pattern))
  )

; DateTime

(defn str->date-time ^DateTime [s] (.parseDateTime (org.joda.time.format.ISODateTimeFormat/dateTimeParser) s))
(defn date-time->str ^String [^DateTime date] (.print (org.joda.time.format.ISODateTimeFormat/dateTime) date))

(extend-type DateTime
  ->JSON
  (to-json [x ^JsonGenerator jg] (.writeString jg (date-time->str x))))

; Date

(extend-type Date
  ->JSON
  (to-json [x ^JsonGenerator jg] (.writeString jg (date-time->str (DateTime. x)))))

; LocalDate

;; TODO: allows loose syntax with time-stuff. good?
(defn str->local-date ^LocalDate [s] (LocalDate/parse s))
(defn local-date->str ^String [^DateTime date] (str date))

(extend-type LocalDate
  ->JSON
  (to-json [x ^JsonGenerator jg] (.writeString jg (local-date->str x))))

;;
;; Protocols
;;

; TODO: should this use r-m-f's existing serialize & de-serialize fns? just a coercion?
; TODO: adding ci

(defmulti serialize (fn [p _] p))
(defmulti de-serialize (fn [p _] p))
(defmulti matcher identity)

(defmethod serialize :json [_ x] (c/generate-string x))
(defmethod de-serialize :json [_ x] (c/parse-string x true))
(defmethod matcher :json [_]
  {Pattern    (safe-str str->pattern)
   s/Int      sc/safe-long-cast
   Long       sc/safe-long-cast
   Double     (sc/safe double)

   Date       (safe-str #(.toDate (str->date-time %)))
   DateTime   (safe-str str->date-time)
   LocalDate  (safe-str str->local-date)

   BigDecimal (sc/safe bigdec)
   BigInteger (sc/safe biginteger)
   Float      (sc/safe float)
   })

(set! *warn-on-reflection* false)
