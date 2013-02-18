(ns model
  (:require [yolk.bacon :as b]
            [yolk.model :as bm]
            [yolk.ui :as ui]
            [jayq.core :refer [$] :as j])
  (:use-macros [yolk.macros :only [defmutator]]))

(defn make-item [id name]
  {:id id
   :name name
   :last-updated (js/Date.)})

(def items (vec (map #(make-item % (str "Name" %)) (range 1 (inc 4)))))

(defmutator update-ts [] [item]
  (assoc item :last-updated (js/Date.)))

(defn item-model [item-map]
  (let [model (bm/make-mutators item-map :update-ts update-ts)
        model (merge model
                     (bm/map->readers item-map (:current model)))]
    (assoc model :updated (-> model
                              :last-updated
                              (b/map pr-str)))))


(def item-models (vec (map item-model items)))