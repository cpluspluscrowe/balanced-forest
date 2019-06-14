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

(defn get-connections [edge edge-dict already-visited]
  (let [all-connections (get edge-dict edge)
        all-connections-as-hash-set (into #{} all-connections)]
    (clojure.set/difference all-connections-as-hash-set already-visited)
    ))

(defn get-forest
  ([edge edges]
   (let [edge-dict (edges-to-dict edges)]
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

(defn cost-pair-to-mapping [cost-pair]
  (let [index (first cost-pair)
        cost (second cost-pair)]
    (hash-map index cost)
    ))

(defn costs-as-hash-map [costs]
  (reduce merge (map cost-pair-to-mapping costs)))

(defn get-costs [costs]
  (let [indexes (range 1 (+ 1 (count costs)))]
    (map vector indexes costs)
    ))

(defn get-forest-cost [forest cost-map]
  (reduce + (map #(get cost-map %) forest)))

(defn map-cost-to-forest [forest cost-map]
  (let [forest-cost (get-forest-cost forest cost-map)]
    (hash-map forest-cost forest)
    ))

(defn get-sorted-forests-by-cost [forests cost-map]
  (into (sorted-map) (reduce merge (map #(map-cost-to-forest % cost-map) forests))))

(defn has-three-forests [forest1 forest2 forest3]
  (if
      (and
       (not (= forest1 forest2))
       (not (= forest2 forest3))
       (not (= forest1 forest3))
       )
    true
    false))

(defn -main
  "See tests for function examples"
  [& args]
  (println "Still need to setup inputs to main"))
