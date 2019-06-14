(ns balanced-forest.core
  (:gen-class))

(defn edge-to-hash-map [edge]
  (let [p1 (first edge)
        p2 (second edge)]
    (hash-map p1 p2 p2 p1)))

(defn edges-to-dict [edges]
  (reduce merge (map edge-to-hash-map edges)))

(defn get-forest [edge edges]
  (let [already-visited (list)]

  ))

(defn balancedForest [c edges]

  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
