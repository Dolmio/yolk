(ns app
  (:require [yolk.bacon :as b]
            [yolk.model :as bm]
            [yolk.ui :as ui]
            [jayq.core :refer [$] :as j]
            [model :as model]
            [clojure.browser.repl :as repl]))
(def item-template
  "<dl class=\"dl-horizontal\">
<dt>Name</dt>
<dd class=\"name\"></dd>
<dt>Last Updated</dt>
<dd class=\"updated\"></dd>
<dt>Button</dt>
<dd>
  <a href=\"#\" class=\"update-button btn\">Update TS</a>
</dd>
</dl>")

(def $item-list ($ "#item-list"))

(defn repl []
  (repl/connect "http://localhost:9000/repl"))

(defn display-item [item]
  (let [$tmpl ($ item-template)]
    (ui/inner ($ "dd.name" $tmpl) (:name item))
    (ui/inner ($ "dd.updated" $tmpl) (:updated item))
    (b/plug (:update-ts item) (ui/click ($ "a.update-button" $tmpl)))
    (j/append $item-list $tmpl)))

(defn ^:export main []
  (doseq [item model/item-models]
    (display-item item)))

(main)



