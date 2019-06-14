(ns balanced-forest.core
  (:require [clojure.set])
  (:gen-class))

(defn edge-to-hash-map [edge]
  (let [p1 (first edge)
        p2 (second edge)]
    (hash-map p1 p2 p2 p1)))

(defn edges-to-dict [edges]
  (reduce merge (map edge-to-hash-map edges)))

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
