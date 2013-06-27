(ns yolk.bacon
  (:refer-clojure :exclude [and filter map merge next not or repeatedly take take-while when]))

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

(def no-more js/Bacon.-noMore)

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
  (js/Bacon.sequentially ms (into-array values)))

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

(defn from-node-callback [f]
  (js/Bacon.fromNodeCallback [f]))

(defn from-callback [f]
  (js/Bacon.fromCallback [f]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Observables
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- kw->fn [maybe-kw]
  (if (keyword? maybe-kw)
    #(maybe-kw %)
    maybe-kw))

(defn map [observable f-or-property]
  (.map observable (kw->fn f-or-property)))

(defn map-apply [obs f]
  (map obs (fn [vs] (apply f vs))))

(defn map-error [observable f]
  (.map-error observable f))

(defn map-end [observable f-or-property]
  (.map-end observable (kw->fn f-or-property)))

(defn filter
  ([observable]
      (filter observable identity))
  ([observable f-or-property]
     (.filter observable  (kw->fn f-or-property)))  )

(defn take-while [observable f]
  (.takeWhile observable (kw->fn f)))

(defn take [observable n]
  (.take observable n))

(defn take-until [observable other]
  (.takeUntil observable other))

(defn skip [observable n]
  (.skip observable n))

(defn delay [observable ms]
  (.delay observable ms))

(defn throttle [observable ms]
  (.throttle observable ms))

(defn debounce [observable ms]
  (.debounce observable ms))

(defn debounce-immediate [observable ms]
  (.debounce observable ms))

(defn do-action [observable f]
  (.doAction observable f))

(defn not [observable]
  (.not observable))

(defn flat-map [observable f]
  (.flatMap observable f))

(defn flat-map-latest [observable f]
  (.flatMapLatest observable f))

(defn flat-map-first [observable f]
  (.flatMapFirst observable f))

(defn scan [observable seed f]
  (.scan observable seed f))

(defn fold [observable seed f]
  (.fold observable seed f))

(defn diff [observable start f]
  (.diff observable start f))

(defn sliding-window
  ([observable n min]
     (.slidingWindow observable n min))
  ([observable n]
     (.slidingWindow observable n)))

(defn log [stream]
  (.log stream))

(defn log-pr [stream]
  (-> stream
      (do-action #(js/console.log (pr-str %)))))

(defn on-value [observable f]
  (.onValue observable f))

(defn on-values [observable f]
  (.onValue observable f))

(defn on-error [observable f]
  (.onError observable f))

(defn on-end [observable f]
  (.onEnd observable f))

(defn on-if [obs on-true on-false]
  (-> obs filter (on-value on-true))
  (-> obs not filter (on-value on-false)))

(defn errors [observable]
  (.errors observable))

(defn end-on-error [observable]
  (.endOnError observable))

(defn subscribe [observable f]
  (.subscribe observable f))

(defn dispose [d]
  (.dispose d))

(defn skip-duplicates [observable & [is-equal]]
  (.skipDuplicates observable is-equal))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; EventStreams
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn event-stream [f]
  (js/Bacon.EventStream. f))

(defn merge [stream stream2]
  (.merge stream stream2))

(defn buffer-with-time [stream ms-or-defer-fn]
  (.bufferWithTime stream ms-or-defer-fn))

(defn buffer-with-count [stream n]
  (.bufferWithCount stream n))

(defn buffer-with-time-or-count [stream delay count]
  (.bufferWithTimeOrCount stream delay count))

(defn to-property
  ([stream]
     (.toProperty stream))
  ([stream x]
     (.toProperty stream x)))

(defn awaiting [stream stream2]
  (.awaiting stream stream2))

(defn zip [stream stream2 f]
  (.zip stream stream2 f))

(defn skip-until [stream starter]
  (.skipUntil stream starter))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Properties
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn constant [x]
  (js/Bacon.constant x))

(defn assign
  "SUBJECT TOO CHANGE.
   Different than baconjs version.
   Extra args will be applied after the property's value, i.e. (apply f v args)"
  [prop target f & args]
  (-> prop
      (on-value
       (fn [v]
         (apply f v args)))))

(defn combine
  ([prop prop2]
     (.combine prop prop2))
  ([prop prop2 f]
     (.combine prop prop2 f)))

(defn sample [prop ms]
  (.sample prop ms))

(defn sampled-by
  ([prop observable]
     (.sampledBy prop observable))
  ([prop observable f]
     (.sampledBy prop observable f)))

(defn changes [prop]
  (.changes prop))

(defn and [prop prop2]
  (.and prop prop2))

(defn or [prop prop2]
  (.or prop prop2))

(comment "todo"
         (defn decode [prop mapping]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Combining
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn combine-as-array [observables]
  (js/Bacon.combineAsArray (into-array observables)))

(defn merge-all [streams]
  (js/Bacon.mergeAll (into-array streams)))

(defn combine-with [observables f]
  (js/Bacon.combineWith (into-array observables) f))

(defn combine-template [template]
  (js/Bacon.combineTemplate (clj->js template)))

(defn zip-as-array [streams]
  (js/Bacon.zipAsArray (into-array streams)))

(defn zip-with [streams f]
  (js/Bacon.zipWith (into-array streams) f))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Bus
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn bus []
  (js/Bacon.Bus.))

(defn push [bus x]
  (.push bus x))

(defn end-bus [bus]
  (.end bus))

(defn bus-error [bus e]
  (.error bus e))

(defn plug [bus stream]
  (.plug bus stream))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Join Patterns
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- make-join-args [observables+fn-pairs]
  (reduce (fn [ results [observables fn]]
            (concat results [(clj->js observables) fn]))
          []
          (partition 2 observables+fn-pairs)))

(defn when [& observables+fn-pairs]
  (apply js/Bacon.when (make-join-args observables+fn-pairs)))

(defn update [init & observables+fn-pairs]
  (apply js/Bacon.update init (make-join-args observables+fn-pairs)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Util
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn log-with [prefix]
  #(js/console.log prefix (pr-str %)))

(defn log-action [sexp]
  (log-with (str sexp " =>")))
  

