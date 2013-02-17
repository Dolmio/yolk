(ns app
  (:require [yolk.bacon :as b]
            [yolk.model :as bm]
            [yolk.ui :as ui]
            [jayq.core :refer [$] :as j]
            [clojure.browser.repl :as repl]))

(defn make-item [name]
  {:id (gensym)
   :name name
   :created-at (js/Date.)
   :last-updated (js/Date.)})

(def items (vec (map #(make-item (str "Name" %))
                     (range 1 (inc 4)))))

(defn tag
  ([tag-name]
     ($ (str "<" tag-name "/>")))
  ([tag-name content]
     ($ (str "<" tag-name ">" content "</" tag-name ">"))))


(defn item-template [item]
  (let [$dl (tag "dl")
        di (fn [k v] [(tag "dt" (pr-str k))
                      (tag "dd" (pr-str v))])]
    (doseq [[k v] item
            :let [[$dt $dd] (di k v)]]
      (-> $dl
          (j/append $dt)
          (j/append $dd)))
    (j/prop $dl "id" (:id item))
    (j/add-class $dl "dl-horizontal")
    $dl))

(def $body ($ "body"))
(def $container ($ "#container"))
(def $item-list ($ "#item-list"))

(defn repl []
  (repl/connect "http://localhost:9000/repl"))

(defn ^:export main []
  (doseq [item items]
    (js/console.log (pr-str item))
    (let [$li (tag "li")]
      (j/append $li (item-template item))
      (j/append $item-list $li))))

(main)



