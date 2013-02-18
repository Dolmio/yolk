(ns yolk.macros)

(defmacro defbus [name args value-args & body]
  `(defn ~name ~args
     (fn ~value-args
       ~@body)))


(defmacro defnotice [name]
  `(defn ~name []
     (fn [x#]
       x#)))