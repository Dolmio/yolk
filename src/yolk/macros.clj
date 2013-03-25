(ns yolk.macros)

(defmacro defbus [name args value-args & body]
  `(defn ~name ~args
     (fn ~value-args
       ~@body)))

(defmacro defnotice [name]
  `(defn ~name []
     (fn [x#]
       x#)))

(defn log-sexp [sexp]
  `(yolk.bacon/do-action (yolk.bacon/log-action '~sexp)))

(defmacro ->logi
  "Skips the fist expression. Useful for using inside a '->"
  [& body]
  `(-> ~(first body)
    ~@(interleave (rest body)
                  (map log-sexp (rest body)))))

(defmacro ->log [& body]
  `(-> ~@(interleave body (map log-sexp body))))


(comment
  (->log a
         (b/map true))

  (defn my-function [value]
    (do stuff ...)
    value)
  (->with my-function
          )
  (-> a
      (b/do-action (log-action 'a))
      (b/map true)
      (b/do-action (log-action '(b/map true))))
  )