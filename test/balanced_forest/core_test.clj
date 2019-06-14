(ns balanced-forest.core-test
  (:require [clojure.test :refer :all]
            [balanced-forest.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 0))))

(deftest edges-to-dict-test
  (testing "converting a list of edges into a dictionary"
    (let [edges (list (list 1 2))]
      (is (= (edges-to-dict edges) (hash-map 1 (list 2) 2 (list 1))
             )))))

(deftest edges-to-dict-multiple-test
  (testing "converting a list of edges into a dictionary"
    (let [edges (list (list 1 2) (list 1 3))]
      (is (= (edges-to-dict edges) (hash-map 1 (list 3 2) 3 (list 1) 2 (list 1)
                                             ))))))

(deftest get-forest-test
  (testing "Getting a set of all nodes connected to the edge"
    (is (= (get-forest 1 (list (list 1 2)))
           ))))

(deftest get-forest-empty-test
  (testing "Getting a set of all nodes connected to the edge"
    (is (= (get-forest 3 (list (list 1 2))) (hash-set 3)
           ))))

(deftest get-forest-multiple-test
  (testing "Getting a set of all nodes connected to the edge"
    (is (= (get-forest 1 (list (list 1 2) (list 2 3))) (hash-set 1 2 3)
           ))))

(deftest get-connections-test
  (testing "Getting relevant connections to an edge"
    (is (= (get-connections 1 (hash-map 1 (list 2 3)) (hash-set 2)) (hash-set 3)))))

(deftest get-costs-test
  (testing "That indexes for cost are correct"
    (is (= (get-costs (list 3 2 1))
           (list [1 3] [2 2] [3 1])
           ))))

(deftest get-costs-in-hash-map-test
  (testing "We transform cost vectors into a map"
    (let [costs (get-costs (list 3 2 1))]
    (is (=
         (costs-as-hash-map costs)
         (hash-map 1 3 2 2 3 1)
         )))))

(deftest get-forest-cost-test
  (testing "Should calculate the cost within a forest"
    (let [costs (get-costs (list 3 2 1))
          cost-map (costs-as-hash-map costs)
          forest (get-forest 1 (list (list 1 2) (list 2 3)))]
      (is (=
           6
           (get-forest-cost forest cost-map)
           )))))

(deftest get-forest-cost-from-two-forests-test
  (testing "Should calculate the cost within a forest"
    (let [costs (get-costs (list 4 3 2 1))
          cost-map (costs-as-hash-map costs)
          forest (get-forest 1 (list (list 1 2) (list 4 3)))]
      (is (=
           (+ 4 3)
           (get-forest-cost forest cost-map)
           )))))

(deftest get-forest-cost-test
  (testing "Should return a hash-map of the forest and its cost"
    (let [costs (get-costs (list 4 3 2 1))
          cost-map (costs-as-hash-map costs)
          edges (list (list 1 2) (list 4 3))
          forest (get-forest 1 edges)]
      (is (= (map-cost-to-forest forest cost-map)
             (hash-map 7 forest)
             )))))

(deftest get-sorted-forests-by-cost-test
  (testing "Should return a sorted cost-forest hash-map"
    (let [costs (get-costs (list 4 3 2 1))
          cost-map (costs-as-hash-map costs)
          edges (list (list 1 2) (list 4 3))
          forest1 (get-forest 1 edges)
          forest1-cost (get-forest-cost forest1 cost-map)
          forest2 (get-forest 3 edges)
          forest2-cost (get-forest-cost forest2 cost-map)
          sorted-forests (get-sorted-forests-by-cost (list forest1 forest2) cost-map)]
       (is (= (get sorted-forests forest1-cost)
              forest1))
       (is (= (get sorted-forests forest2-cost)
              forest2))
       (is (= (second (first sorted-forests))
              forest2)) ; because it is sorted by cost
)))
