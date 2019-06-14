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

(deftest edges-to-dict-multiple-test
  (testing "converting a list of edges into a dictionary"
    (let [edges (list (list 1 2) (list 1 3))]
      (is (= (edges-to-dict edges) (hash-map 1 (list 3 2) 3 (list 1) 2 (list 1)
                                             ))))))

(deftest get-forest-test
  (testing "Getting a set of all nodes connected to the edge"
    (is (= (get-forest 1 (list (list 1 2))) (hash-set 2)))
    ))

(deftest get-connections-test
  (testing "Getting relevant connections to an edge"
    (is (= (get-connections 1 (hash-map 1 (list 2 3)) (hash-set 2)) (hash-set 3)))))

;(deftest get-forest-test-larger
;  (testing "Getting a set of all nodes connected to the edge"
;    (is (= (get-forest 1 (list (list 1 2) (list 1 3))) (hash-set 2 3)))
;    ))
