(ns app
  (:require [yolk.bacon :as b]
            [yolk.model :as bm]
            [yolk.ui :as ui]
            [jayq.core :refer [$] :as j]
            [model :as model]
            [clojure.browser.repl :as repl]))

(defn tag
  ([tag-name]
     ($ (str "<" tag-name "/>")))
  ([tag-name content]
     ($ (str "<" tag-name ">" content "</" tag-name ">"))))

(defn item-template [item]
  (let [$dl (tag "dl")
        di (fn [k v] [(tag "dt" (str (name k) ":"))
                      (tag "dd" (pr-str v))])]
    (doseq [[k v] item
            :let [[$dt $dd] (di k v)]]
      (-> $dl
          (j/append $dt)
          (j/append $dd)))
    (j/append $dl "<dt></dt><dd><a class=\"btn btn-small push-right\" href=\"#\">Update</a></dd>")

    (j/prop $dl "id" (:id item))
    (j/add-class $dl "dl-horizontal")
    $dl))

(def $body ($ "body"))
(def $container ($ "#container"))
(def $item-list ($ "#item-list"))

(defn repl []
  (repl/connect "http://localhost:9000/repl"))

(defn ^:export main []
  (doseq [item model/items]
    (let [$li (tag "li")]
      (j/append $li (item-template item))
      (j/append $item-list $li))))

(main)



