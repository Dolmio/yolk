(ns qunit.macros)

(defmacro deftest [s & body]
  `(QUnit.test ~s
            (fn []
              ~@body)))

(defmacro defasynctest [s expected & body]
  `(QUnit.asyncTest ~s ~expected
                 (fn []
                   ~@body)))