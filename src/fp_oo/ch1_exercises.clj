(ns fp-oo.ch1-exercises)

;; exercise 1
(def my-second
  (fn [list]
    (first
      (rest list))))

;; exercise 2
(def third-a
  (fn [list]
    (first
      (rest
        (rest list)))))

(def third-b
  (fn [list]
    (nth list 2)))

