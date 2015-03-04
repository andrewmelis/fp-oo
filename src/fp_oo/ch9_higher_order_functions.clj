(ns fp-oo.ch9-higher-order-functions)

;; scratch space for in-chapter stuff

;; closing over values
(def manual-inc5
  (fn [x] (+ 5 x)))

(def make-incrementer
  (fn [increment]
    (fn [x] (+ increment x))))

(def inc5 (make-incrementer 5))

(def inc5-from-partial (partial + 5))

(def increment-all (partial map inc))

(def incish
  (partial map + [100 200 300]))
