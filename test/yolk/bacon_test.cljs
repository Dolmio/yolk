(ns yolk.bacon-test
  (:require [qunit.core :refer [ok? equal? module start]]
            [yolk.bacon :as b])
  (:use-macros [qunit.macros :only [deftest defasynctest defblt]]))

(module "Bacon Tests")

(defblt "once" 1
  (b/once true)
  ok?)

(defblt "once no arg" 1
  (b/once)
  (comp ok? nil?))

(defblt "later" 1
  (b/later 5 true)
  ok?)

(defblt "from-array" 1
  (b/from-array [1])
  #(equal? 1 %))

(defblt "sequentially" 3
  (b/sequentially 10 [1 2 3])
  ok?)

(defblt "map" 1
  (-> (b/from-array [1])
      (b/map inc))
  #(equal? 2 %))

(defblt "map keyword" 1
  (-> (b/from-array [{:a 1}])
      (b/map :a))
  #(equal? 1 %))

(defblt "filter" 1
  (-> (b/from-array [1 2])
      (b/filter even?))
  #(equal? 2 %))

(defblt "filter true" 1
  (-> (b/from-array [false false true])
      b/filter)
  ok?)

(defblt "filter multiple true" 2
  (-> (b/from-array [true false true])
      b/filter)
  ok?)

(defblt "take-while" 2
  (-> (b/from-array [true true false])
      (b/take-while identity))
  ok?)

(defblt "take n" 2
  (-> (b/from-array [true true true])
      (b/take 2))
  ok?)

(let [other (b/bus)]
  (defblt "take-until" 2
    (-> (b/sequentially 1 [1 2 3])
        (b/do-action (fn [v]
                       (when (> v 2) (b/push other v))))
        (b/take-until other))
    ok?))

(defblt "skip n" 1
  (-> (b/from-array [false false true])
      (b/skip 2))
  ok?)


(defblt "not" 2
  (-> (b/from-array [false false])
      b/not)
  ok?)

(defblt "start-with" 1
        (-> (b/start-with (b/to-property (b/from-array [])) 1)
            (b/take 1))
        ok?)

(defblt "skip-while" 3
        (-> (b/from-array [false false true true true])
            (b/skip-while false?))
        ok?)

(defblt "end-on-error" 3
        (-> (b/from-array [true true true  (b/error "error") true])
            (b/end-on-error))
        ok?)

(defblt "end-on-error-with-predicate" 5
        (-> (b/from-array [true true true  (b/error "harmless")
                           true  true (b/error "fatal") true false])
            (b/end-on-error #(= "fatal" %)))
        ok?)

(defblt "sampled-by" 3
            (b/sampled-by (b/from-array [true]) (b/from-array [false false false]))
            ok?)

(defblt "sampled-by-with-combinator" 3
        (-> (b/from-array [true])
            (b/sampled-by (b/from-array [false false false]) #(inc 0)))
        #(equal? % 1))

(comment
  (defblt "skip-duplicates" 3
    (-> (b/from-array [true false true])
        b/to-property
        (b/sampled-by
         (-> (b/sequentially 5 [false true true true false true])
             b/skip-duplicates)))
    (fn [v]
      (js/console.log v)
      (ok? v))))


(comment
  (defblt "skip-duplicates"
    (-> (b/sequentially 5 [true true true true false true])
        b/skip-duplicates)
    [true false true]))