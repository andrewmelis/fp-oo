(ns fp-oo.ch3-add-and-make-test
  (require [clojure.test :refer :all]
           [fp-oo.ch3-add-and-make :refer :all]))

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

(deftest Triangle-constructor-test
  (testing "can construct a Triangle"
    (is (= 'Triangle
           (class-of (Triangle (Point 0 0)
                               (Point 0 1)
                               (Point 1 0)))))))

(deftest shift-test
  (testing "returns a new point with coordinates 'updated' by arguments"
    (is (= (Point 0 0)
           (shift (Point 1 200) -1 -200)))))

(deftest add-test
  (testing "add implemented without shift"
    (is (= (Point 0 0)
           (add-without-shift (Point 100 200)
                              (Point -100 -200)))))
  (testing "add implemented with shift"
    (is (= (Point 0 0)
           (add (Point 100 200)
                (Point -100 -200))))))

(deftest make-test
  (testing "implement a Java-ish 'constructor'"
    (testing "point"
      (is (= (Point 1 2) (make Point 1 2))))
    (testing "triangle"
      (is (= (Triangle (Point 0 0)
                       (Point 0 1)
                       (Point 1 0))
             (make Triangle (make Point 0 0)
                            (make Point 0 1)
                            (make Point 1 0)))))))

(deftest equal-triangles?-test
  (testing "compare two triangles"
    (testing "identical triangles"
      (is (equal-triangles? right-triangle right-triangle)))
    (testing "not identical, but contents equal"
      (is (equal-triangles? right-triangle equal-right-triangle)))
    (testing "different"
      (not (equal-triangles? right-triangle different-triangle))))
  (testing "compare > 2 triangles"
    (testing "equal"
      (is (equal-triangles? right-triangle right-triangle equal-right-triangle)))
    (testing "different"
      (not (equal-triangles? right-triangle equal-right-triangle different-triangle)))))

(deftest valid-triangle?-test
  (testing "invalid when has duplicate points"
    (is (valid-triangle? (Point 0 0)
                         (Point 1 1)
                         (Point 2 2)))
    (not (valid-triangle? (Point 0 0)
                          (Point 0 0)
                          (Point 2 2)))))
