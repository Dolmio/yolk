(ns yolk.bacon
  (:refer-clojure :exclude [map next filter merge]))

(defn initial [x]
  (js/Bacon.Initial. x))

(defn next [x]
  (js/Bacon.Next. x))

(defn error [x]
  (js/Bacon.Error. x))

(defn end []
  (js/Bacon.End.))

(defn no-more? [reply]
  (= (.-noMore js/Bacon) reply))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Creation
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn event-stream [f]
  (js/Bacon.EventStream. f))

(defn from-event-target [target event-name]
  (js/Bacon.fromEventTarget target event-name))

(defn as-event-stream [f])

(defn once
  ([]
     (js/Bacon.once))
  ([x]
     (js/Bacon.once x)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Observables
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn delay [stream ms]
  (.delay stream ms))

(defn filter [stream f]
  (.filter stream f))

(defn map [stream f]
  (.map stream (if (keyword? f) #(f %) f)))

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
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

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
