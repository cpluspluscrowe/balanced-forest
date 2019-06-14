(ns balanced-forest.core
  (:require [clojure.set])
  (:gen-class))

(defn edge-to-hash-map [edge]
  (let [p1 (first edge)
        p2 (second edge)]
    (hash-map p1 (list p2) p2 (list p1))))
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

(into #{} [1 2 3 4])

(defn get-connections [edge edge-dict already-visited]
  (let [all-connections (get edge-dict edge)
        all-connections-as-hash-set (into #{} all-connections)]
    (clojure.set/difference all-connections-as-hash-set already-visited)
    ))

(defn get-forest
  ([edge edges]
   (let [edge-dict (edges-to-dict edges)]
     (println edge edge-dict (hash-set))
     (get-forest edge edge-dict (hash-set))))
  ([edge edge-dict already-visited]
   (let [connections (get-connections edge edge-dict already-visited)
         already-visited-updated (conj already-visited edge)]
         ; Since I added edge to already visited, it needs to somehow be returned as part of the forest
     (if (empty? connections) (hash-set edge)
         (reduce conj (reduce flatten
                 (map #(get-forest % edge-dict (conj already-visited-updated)) connections))
                 (hash-set edge))
                 ))
))

(defn balancedForest [c edges]

  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
