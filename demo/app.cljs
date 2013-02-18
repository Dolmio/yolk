(ns app
  (:require [yolk.bacon :as b]
            [yolk.model :as bm]
            [yolk.ui :as ui]
            [jayq.core :refer [$] :as j]
            [model :as model]))

(def item-template
  "<div class=\"item\" style=\"border-top: 1px solid black\"><p>
<span class=\"name\"/>
<span class=\"updated\"/>
</p>
<p>
  <a href=\"#\" class=\"update-button\">Update TS</a>
  |
  <a href=\"#\" class=\"remove-button\">Remove</a>
</p>
</div>")

(def $item-list ($ "#item-list"))

(defn display-item [$parent item]
  (let [$tmpl ($ item-template)]
    (ui/inner ($ ".name" $tmpl) (:name item))
    (ui/inner ($ ".updated" $tmpl) (-> (:last-updated item)
                                         (b/map pr-str)))
    (b/plug (:update-ts item) (ui/click ($ "a.update-button" $tmpl)))
    (b/plug (:mark-delete item) (ui/click ($ "a.remove-button" $tmpl)))
    (j/append $parent $tmpl)))

(defn ^:export main []
  (let [models (model/items-model model/items)]
    (b/on-value (:current models)
                (fn [items]
                  (j/empty $item-list)
                  (doseq [item items]
                    (display-item $item-list item))))))

(main)



