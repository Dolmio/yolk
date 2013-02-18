(ns model
  (:require [yolk.bacon :as b]
            [yolk.model :as bm]
            [yolk.ui :as ui]
            [jayq.core :refer [$] :as j])
  (:use-macros [yolk.macros :only [defbus]]))

(defn make-item [id name]
  {:id id
   :name name
   :last-updated (js/Date.)
   :delete? false})

(def items (vec (map #(make-item % (str "Name" %)) (range 1 (inc 4)))))

(defbus update-ts [] [item]
  (assoc item :last-updated (js/Date.)))

(defbus mark-delete [] [item]
  (assoc item :delete? true))

(defn item-model [item-map]
  (let [model (bm/buses
               item-map
               :update-ts update-ts
               :mark-delete mark-delete)
        model (merge model
                     (bm/map->properties item-map (:current model)))]
    (assoc model
      :id (:id item-map)
      :updated (-> model :last-updated (b/map pr-str))
      :delete-me (-> model :mark-delete (b/map (:current model))))))

(defbus remove-item [item] [items]
  (vec (remove #(= (:id item) (:id %)) items)))

(defn items-model [item-maps]
  (let [children (map item-model items)
        model (bm/buses
               children
               :remove-item remove-item)]
    (b/plug (:remove-item model)
            (b/merge-all (map :delete-me children)))
    (assoc model :children children)))