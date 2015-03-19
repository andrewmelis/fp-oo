(ns fp-oo.ch11-zipper
  (require [clojure.zip :as zip]))

;; ex1
(defn all-vectors [tree]
  (letfn [(extract-vectors [so-far zipper]
            (cond (zip/end? zipper)
                  so-far

                  (vector? (zip/node zipper))
                  (extract-vectors (cons (zip/node zipper) so-far)
                                   (zip/next zipper))

                  :else
                  (extract-vectors so-far (zip/next zipper))))]
    (reverse (extract-vectors '() (zip/seq-zip tree)))))

;; ex2
(defn easy-first-vector [tree]
  (first (all-vectors tree)))

(defn first-vector [tree]
  (letfn [(extract-first-vector [rest-of-zipper]
            (cond (zip/end? rest-of-zipper)
                  nil

                  (vector? (zip/node rest-of-zipper))
                  (zip/node rest-of-zipper)

                  :else
                  (extract-first-vector (zip/next rest-of-zipper))))]
    (extract-first-vector (zip/seq-zip tree))))
