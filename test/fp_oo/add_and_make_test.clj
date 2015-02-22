(ns fp-oo.add-and-make-test
  (require [clojure.test :refer :all]
           [fp-oo.add-and-make :refer :all]))

(deftest Point-constructor-test
  (testing "can construct a Point and access 'instance variables'"
    (is (= 2 (:y (Point 1 2))))))

(deftest getter-methods-test
  (testing "verbose getter"
    (is (= 2 (verbose-y (Point 1 2)))))
  (testing "shorter getter"
    (is (= 2 (shorter-y (Point 1 2)))))
  (testing "shortest getter"
    (is (= 2 (y (Point 1 2))))
    (is (= 1 (x (Point 1 2))))))


(deftest class-of-test
  (testing "returns symbol for 'object'"
    (is (= 'Point (class-of (Point 1 2))))))

(deftest shift-test
  (testing "returns a new point with coordinates 'updated' by arguments"
    (is (= (Point 0 0)
           (shift (Point 1 200) -1 -200)))))

