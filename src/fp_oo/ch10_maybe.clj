(ns fp-oo.ch10-maybe)

(use 'clojure.algo.monads)

;; error utils
(def oops!
  "generates error map"
  (fn [reason & args]
    (with-meta (merge {:reason reason}
                      (apply hash-map args))
               {:type :error})))

(def oopsie?
  "checks whether a value represents an error by looking at type"
  (fn [value]
    (= (type value) :error)))

;; roll-your-own maybe monad
(def decider
  "homegrown 'bind'"
  (fn [step-value continuation]
    (if (nil? step-value)
      nil
      (continuation step-value))))

(def maybe-monad
  "homegrown 'maybe-m'"
  (monad [m-result identity
          m-bind decider]))

;; roll-your-own error monad
(def error-decider
  (fn [step-value continuation]
    (if (oopsie? step-value)
      step-value
      (continuation step-value))))

(def error-monad
  "homegrown 'error-m'"
  (monad [m-result identity
          m-bind error-decider]))


;; example error-monad blow-up
(def factorial
  (fn [n]
    (cond (< n 0)
          (oops! "Factorial can never be less than zero." :number n)

          (< n 2)
          1

          :else
          (* n (factorial (dec n))))))

(def result
  (with-monad error-monad
    (domonad [big-number (factorial -1)
              even-bigger (* 2 big-number)]
             (repeat :a even-bigger))))
