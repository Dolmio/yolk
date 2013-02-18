(ns app
  (:require [yolk.bacon :as b]
            [yolk.model :as bm]
            [yolk.ui :as ui]
            [jayq.core :refer [$] :as j]
            [model :as model]))

(def item-template
  "<dl class=\"dl-horizontal\">
<dt>Name</dt>
<dd class=\"name\"></dd>
<dt>Last Updated</dt>
<dd class=\"updated\"></dd>
<dt>Button</dt>
<dd>
  <a href=\"#\" class=\"update-button btn\">Update TS</a>
  <a href=\"#\" class=\"remove-button btn\">Remove</a>
</dd>
</dl>")

(def $item-list ($ "#item-list"))

(defn display-item [$parent item]
  (let [$tmpl ($ item-template)]
    (ui/inner ($ "dd.name" $tmpl) (:name item))
    (ui/inner ($ "dd.updated" $tmpl) (:updated item))
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



