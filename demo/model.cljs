(ns model
  (:require [yolk.bacon :as b]
            [yolk.model :as bm]
            [yolk.ui :as ui]
            [jayq.core :refer [$] :as j])
  (:use-macros [yolk.macros :only [defbus defnotice]]))

(defn make-item [id name]
  {:id id
   :name name
   :last-updated (js/Date.)})

(def items (vec (map #(make-item % (str "Name" %)) (range 1 (inc 4)))))

(defbus update-ts [] [item]
  (assoc item :last-updated (js/Date.)))

(defnotice mark-delete)

(defn item-model [item-map]
  (let [model (bm/map-model item-map
               :update-ts update-ts
               :mark-delete mark-delete)]
    (assoc model
      :id (:id item-map)
      :remove (-> model :mark-delete (b/map (:current model))))))

(defbus remove-item [item] [items]
  (vec (remove #(= (:id item) (:id %)) items)))

(defn items-model [items]
  (let [model (bm/list-models items item-model
                              :remove-item remove-item)]
    (bm/plug-children model :remove-item :remove)
    model))