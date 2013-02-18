(ns yolk.model
  (:require [yolk.bacon :as b]
            [clojure.string :as string]))

(defn- fname->kw [f]
  (-> f
      .-name
      (string/replace #"_" "-")
      keyword))

(defn buses [target & modifiers]
  (let [[buses change-streams] (reduce (fn [[bs streams] f]
                                         (let [bus (b/bus)]
                                           [(assoc bs (fname->kw f) bus)
                                            (conj streams (-> bus (b/map f)))]))
                                       [{} []]
                                       modifiers)
        all-changes (b/merge-all change-streams)
        current (b/scan all-changes target (fn [x f] (f x)))]
    (assoc buses
      :all-changes all-changes
      :current current)))

(defn map->properties [initial-map current]
  (reduce (fn [result [k v]]
            (assoc result k
                   (b/scan current v (fn [_ x]
                                       (get x k)))))
          {} initial-map))

(defn map-model [target & modifiers]
  (let [model (apply buses target modifiers)]
    (merge model
           (map->properties target (:current model)))))

(defn list-models [items f & modifiers]
  (let [children (map f items)
        model (apply buses children modifiers)]
    (assoc model :children children)))

(defn plug-children [model bus-name stream-name]
      (b/plug (get model bus-name)
            (b/merge-all (map #(get % stream-name) (:children model)))))

(defn map-current [model f]
   (-> model f (b/map (:current model))))

(defn matching [source k v]
   (b/map source (fn [xs] (filter #(= v (k %)) xs))))

(defn has-any? [property key-fn]
  (-> property
      (b/map #(some #{true} (map (comp boolean key-fn) %)))
      b/to-property))

(defn merge-streams [model keys]
  (b/merge-all (vals (select-keys model keys))))

(defn on-any [model update-streams on-update]
  (let [updated (-> (merge-streams model update-streams)
                    (b/merge (b/once))
                    (b/map (:current model)))]
    (b/on-value updated on-update)
    updated))
