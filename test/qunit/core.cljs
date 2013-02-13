(ns qunit.core)

(defn module
  ([name]
     (QUnit.module name))
  ([name setup]
     (QUnit.module name (clj->js {:setup setup})))
  ([name setup teardown]
     (QUnit.module name (clj->js {:setup setup :teardown teardown}))))

(defn start
  ([]
     (QUnit.start))
  ([decrement]
     (QUnit.start decrement)))

(defn stop
  ([]
     (QUnit.stop))
  ([increment]
     (QUnit.stop increment)))

(defn ok? [state & [message]]
  (QUnit.ok state message))

(defn deep-equal? [actual expected & [message]]
  (QUnit.deepEqual actual expected message))

(defn equiv? [actual expected & [message]]
  (ok? (= actual expected)
       (or message
           (str "Not equal. Actual: [" actual "] Expected: [" expected "]"))))

(defn equal? [actual expected & [message]]
  (let [test? (if (satisfies? IEquiv actual)
               equiv?
               QUnit.equal)]
    (test? actual expected message)))

(defn not-deep-equal? [actual expected & [message]]
  (QUnit.notDeepEqual actual expected message))

(defn not-equal? [actual expected & [message]]
  (QUnit.notEqual actual expected message))

(defn not-strict-equal? [actual expected & [message]]
  (QUnit.notStrictEqual actual expected message))

(defn strict-equal? [actual expected & [message]]
  (QUnit.strictEqual actual expected message))

(defn throws? [block expected & [message]]
  (QUnit.throws block expected message ))