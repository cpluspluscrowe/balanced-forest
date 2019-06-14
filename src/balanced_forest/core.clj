(ns balanced-forest.core
  (:require [clojure.set])
  (:gen-class))

(defn edge-to-hash-map [edge]
  (let [p1 (first edge)
        p2 (second edge)]
    (hash-map p1 p2 p2 p1)))
(defn get-hash-map-value-as-list [key hm]
  (let [value (get hm key)]
    (if (list? value) value
        (list value))))

(defn merge-hash-maps-at-key [key hm1 hm2]
  (hash-map key (filter identity (reduce conj (get-hash-map-value-as-list key hm1) (get-hash-map-value-as-list key hm2)))))

(defn merge-hash-maps [hm1 hm2]
  (let [keys (reduce conj (keys hm1) (keys hm2))]
    (reduce merge (map #(merge-hash-maps-at-key % hm1 hm2) keys))))

(defn edges-to-dict [edges]
  (reduce merge-hash-maps (map edge-to-hash-map edges)))

(defn get-forest
  ([edge edges] (get-forest edge edges (hash-set edge)))
  ([edge edges already-visited]
   (let [edge-dict (edges-to-dict edges)
         connections (clojure.set/difference (hash-set (get edge-dict edge)) already-visited)]
     (if (empty? connections) (hash-set edge)
     (reduce conj (map #(get-forest % edges (conj already-visited %)) connections)))
)))

(defn balancedForest [c edges]

  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
