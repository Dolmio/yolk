(ns qunit.macros)

(defmacro deftest [s & body]
  `(QUnit.test ~s
            (fn []
              ~@body)))

(defmacro defasynctest [s expected & body]
  `(QUnit.asyncTest ~s ~expected
                 (fn []
                   ~@body)))

(defmacro defblt [s expected observable on-value-fn]
  `(QUnit.asyncTest ~s ~expected
                 (fn []
                   (yolk.bacon/on-value ~observable ~on-value-fn)
                   (yolk.bacon/on-end ~observable #(qunit.core/start)))))
