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


