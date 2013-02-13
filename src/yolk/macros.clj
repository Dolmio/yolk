(ns yolk.macros)

(defmacro defmutator [name args value-args & body]
  `(defn ~name ~args
     (fn ~value-args
       ~@body)))
