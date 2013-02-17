(ns model)

(defn make-item [id name]
  {:id id
   :name name
   :created-at (js/Date.)
   :last-updated (js/Date.)})

(def items (vec (map #(make-item % (str "Name" %)) (range 1 (inc 4)))))
