(ns yolk.model
  (:require [yolk.bacon :as b]))

(defn property-bus [f]
  (b/map (js/Bacon.Bus.) f))

(defn make-mutators [target & pairs]
  (let [mutator-map (apply hash-map pairs)
        buses (reduce (fn [m [k v]]
                        (assoc m k (js/Bacon.Bus.)))
                      {} mutator-map)
        change-streams (map (fn [[k bus]]
                              (b/map bus (get mutator-map k)))
                            buses)
        all-changes (b/merge-all change-streams)
        current (b/scan all-changes target (fn [x f] (f x)))]
    (assoc buses
      :all-changes all-changes
      :current current)))

(defn map->readers [m current]
  (reduce (fn [result [k v]]
            (assoc result k
                   (b/scan current v (fn [_ x]
                                       (get x k)))))
          {} m))

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
