(ns balanced-forest.core-test
  (:require [clojure.test :refer :all]
            [balanced-forest.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 0))))

(deftest edges-to-dict-test
  (testing "converting a list of edges into a dictionary"
    (let [edges (list (list 1 2))]
      (is (= (edges-to-dict edges) (hash-map 1 2 2 1)
             )))))
