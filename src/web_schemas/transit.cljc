(ns web-schemas.transit
  (:require [cognitect.transit :as transit]
            [schema.core :as s]))

(def writers
  {schema.core.AnythingSchema
   (transit/write-handler
     (constantly "AnythingSchema")
     (constantly nil))

   schema.core.EqSchema
   (transit/write-handler
     (constantly "EqSchema")
     (fn [this] (:v this)))

   schema.core.EnumSchema
   (transit/write-handler
     (constantly "EnumSchema")
     (fn [this] (:vs this)))

   schema.core.Predicate
   (transit/write-handler
     (constantly "PredicateSchema")
     (fn [this] [(:p? this) (:pred-name this)]))

   schema.core.Protocol
   (transit/write-handler
     (constantly "ProtocolSchema")
     (fn [this] (:p this)))

   schema.core.Maybe
   (transit/write-handler
     (constantly "MaybeSchema")
     (fn [this] (:schema this)))

   schema.core.NamedSchema
   (transit/write-handler
     (constantly "NamedSchema")
     (fn [this] [(:schema this) (:name this)]))

   schema.core.Either
   (transit/write-handler
     (constantly "EitherSchema")
     (fn [this] (:schemas this)))

   schema.core.ConditionalSchema
   (transit/write-handler
     (constantly "ConditionalSchema")
     (fn [this] [(:preds-and-schemas this) (:error-symbol this)]))

   schema.core.CondPre
   (transit/write-handler
     (constantly "CondPreSchema")
     (fn [this] (:schemas this)))

   schema.core.Constrained
   (transit/write-handler
     (constantly "ConstrainedSchema")
     (fn [this] [(:schema this) (:postcondition this) (:post-name this)]))

   schema.core.Both
   (transit/write-handler
     (constantly "BothSchema")
     (fn [this] (:schemas this)))

   schema.core.Recursive
   (transit/write-handler
     (constantly "RecursiveSchema")
     (fn [this] (:derefable this)))

   schema.core.Atomic
   (transit/write-handler
     (constantly "AtomicSchema")
     (fn [this] (:schema this)))

   schema.core.RequiredKey
   (transit/write-handler
     (constantly "RequiredKeySchema")
     (fn [this] (:k this)))

   schema.core.OptionalKey
   (transit/write-handler
     (constantly "OptionalKeySchema")
     (fn [this] (:k this)))

   schema.core.MapEntry
   (transit/write-handler
     (constantly "MapEntrySchema")
     (fn [this] [(:key-schema this) (:val-schema this)]))

   schema.core.Queue
   (transit/write-handler
     (constantly "QueueSchema")
     (fn [this] (:schema this)))

   schema.core.One
   (transit/write-handler
     (constantly "OneSchema")
     (fn [this] [(:schema this) (:optional? this) (:name this)]))

   schema.core.Record
   (transit/write-handler
     (constantly "RecordSchema")
     (fn [this] [(:klass this) (:schema this)]))

   schema.core.FnSchema
   (transit/write-handler
     (constantly "FnSchema")
     (fn [this] [(:output-schema this) (:input-schema this)]))
   })

(def readers
  {"AnythingSchema"
   (transit/read-handler (fn [_] s/Any))

   "EqSchema"
   (transit/read-handler (fn [v] (s/eq v)))

   "EnumSchema"
   (transit/read-handler (fn [vs] (apply s/enum vs)))

   "PredicateSchema"
   (transit/read-handler (fn [[p? pred-name]] (s/pred p? pred-name)))

   "ProtocolSchema"
   (transit/read-handler (fn [p] (s/protocol p)))

   "MaybeSchema"
   (transit/read-handler (fn [schema] (s/maybe schema)))

   "NamedSchema"
   (transit/read-handler (fn [[schema name]] (s/named schema name)))

   "EitherSchema"
   (transit/read-handler (fn [schemas] (apply s/either schemas)))

   ; FIXME: error-symbol
   "ConditionalSchema"
   (transit/read-handler (fn [[preds-and-schemas error-symbol]] (apply s/conditional preds-and-schemas)))

   "CondPreSchema"
   (transit/read-handler (fn [schemas] (apply s/cond-pre schemas)))

   "ConstrainedSchema"
   (transit/read-handler (fn [[schema postcondition post-name]] (s/constrained schema postcondition post-name)))

   "BothSchema"
   (transit/read-handler (fn [schemas] (apply s/both schemas)))

   "RecursiveSchema"
   (transit/read-handler (fn [derefable] (s/recursive derefable)))

   "AtomicSchema"
   (transit/read-handler (fn [schema] (s/atom schema)))

   "RequiredKeySchema"
   (transit/read-handler (fn [k] (s/required-key k)))

   "OptionalKeySchema"
   (transit/read-handler (fn [k] (s/optional-key k)))

   "MapEntrySchema"
   (transit/read-handler (fn [[key-schema val-schema]] (s/map-entry key-schema val-schema)))

   "QueueSchema"
   (transit/read-handler (fn [schema] (s/queue schema)))

   ; FIXME: optional?
   "OneSchema"
   (transit/read-handler (fn [[schema optional? name]] (s/one schema name)))

   ; FIXME:
   ; "RecordSchema"
   ; (transit/read-handler (fn [[klass schema]] (s/record klass schema)))

   ; FIXME:
   "FnSchema"
   (transit/read-handler (fn [[output-schema input-schema]] nil))
   })
