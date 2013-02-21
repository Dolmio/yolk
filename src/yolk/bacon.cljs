(ns yolk.bacon
  (:refer-clojure :exclude [filter map merge next not repeatedly]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Events
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn next [value]
  (js/Bacon.Next. value))

(defn initial [value]
  (js/Bacon.Initial. value))

(defn end []
  (js/Bacon.End.))

(defn error [e]
  (js/Bacon.Error. e))


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

(defn- kw->fn [maybe-kw]
  (if (keyword? maybe-kw)
    #(maybe-kw %)
    maybe-kw))

(defn not [stream]
  (.not stream))

(defn map [stream f-or-property]
  (.map stream (kw->fn f-or-property)))

(defn delay [stream ms]
  (.delay stream ms))

(defn filter
  ([stream]
      (filter stream identity))
  ([stream f-or-property]
     (.filter stream  (kw->fn f-or-property)))  )

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

(defn flat-map [observable f]
  (.flatMap observable f))

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

(defn combine [prop prop2 f]
  (.combine prop prop2 f))

(defn sampled-by
  ([prop stream]
     (.sampledBy prop stream))
  ([prop stream f]
     (.sampledBy prop stream f)))

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
  (js/Bacon.mergeAll (into-array streams)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Bus
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn bus []
  (js/Bacon.Bus.))

(defn push [bus x]
  (.push bus x))

(defn end [bus]
  (.end bus))

(defn error [bus e]
  (.error bus e))

(defn plug [bus stream]
  (.plug bus stream))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Util
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn log [stream]
  (.log stream))

(defn log-pr [stream]
  (-> stream
      (map pr-str)
      (on-value #(js/console.log %))))
