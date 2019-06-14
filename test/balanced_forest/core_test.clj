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
