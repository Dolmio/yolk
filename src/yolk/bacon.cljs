(ns yolk.bacon
  (:refer-clojure :exclude [map next filter merge]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Creation
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn from-promise [promise]
  (js/Bacon.fromPromise promise))

(defn once
  ([]
     (js/Bacon.once))
  ([x]
     (js/Bacon.once x)))

(defn from-array [values]
  (js/Bacon.fromArray (into-array values)))

(defn interval [ms value]
  (js/Bacon.interval ms value))

(defn sequentially [ms values]
  (js/Bacon.sequentially ms values))

(defn repeatedly [ms values]
  (js/Bacon.repeatedly ms values))

(defn never []
  (js/Bacon.never))

(defn from-event-target [target event-name]
  (js/Bacon.fromEventTarget target event-name))

(defn from-poll [interval f]
  (js/Bacon.fromPoll interval f))

(defn later [delay value]
  (js/Bacon.later delay value))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Observables
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn map [stream f-or-property]
  (.map stream (if (keyword? f-or-property)
                 #(f-or-property %)
                 f-or-property)))

(defn delay [stream ms]
  (.delay stream ms))

(defn filter [stream f]
  (.filter stream f))

(defn on-value [stream f]
  (.onValue stream f))

(defn on-error [stream f]
  (.onError stream f))

(defn dispose [d]
  (.dispose d))

(defn scan [observable seed f]
  (.scan observable seed f))

(defn do-action [observable f]
  (.doAction observable f))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; EventStreams
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;

(defn event-stream [f]
  (js/Bacon.EventStream. f))

(defn to-property
  ([stream]
     (.toProperty stream))
  ([stream x]
     (.toProperty stream x)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Properties
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn sampled-by [prop stream f]
  (.sampledBy prop stream f))

(defn skip-duplicates [prop & [is-equal]]
  (.skipDuplicates prop is-equal))

(defn changes [prop]
  (.changes prop))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Combining
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn combine-as-array [streams]
  (js/Bacon.combineAsArray (into-array streams)))

(defn merge [stream stream2]
  (.merge stream stream2))

(defn merge-all [streams]
  (reduce (fn [result stream]
            (b/merge result stream))
          streams))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Bus
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn plug [bus stream]
  (.plug bus stream))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Util
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn log [stream]
  (.log stream))
