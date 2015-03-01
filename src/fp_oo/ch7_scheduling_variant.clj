(ns fp-oo.ch7-scheduling-variant)
(use 'clojure.set)

(def annotate
  (fn [courses registrant instructor-count]
    (let [out-of-instructors?     ;; only subroutine that uses ALL courses
          (= instructor-count
             (count (filter (fn [course] (not (:empty? course)))
                            courses)))

          answer-annotate
          (fn [course]
            (assoc course
                   :spaces-left (- (:limit course)
                                   (:registered course))
                   :already-in? (contains? (set (:taking-now registrant))
                                           (:course-name course))))
          domain-annotate
          (fn [course]
            (assoc course
                   :empty? (zero? (:registered course))
                   :full? (zero? (:spaces-left course))))

          missing-prerequisites?
          (fn [course]
            (not (superset? (set (:past-courses registrant))
                            (set (:prerequisites course)))))

          note-unavailability
          (fn [course]
            (assoc course
                   :unavailable? (or (:full? course)
                                     (and out-of-instructors?
                                          (:empty? course))
                                     (missing-prerequisites? course)
                                     (and (:manager? registrant)
                                          (not (:morning? course))))))

          annotate-one
          (fn [course]
            (-> course
                answer-annotate
                domain-annotate
                note-unavailability))]

      (map annotate-one
           courses))))

(def separate
  (fn [predicate sequence]
    [(filter predicate sequence) (remove predicate sequence)]))

(def visible-courses
  (fn [courses]
    (let [[guranteed possibles] (separate :already-in? courses)]
      (concat guranteed (remove :unavailable? possibles)))))

(def final-shape
  (fn [courses]
    (let [desired-keys [:course-name, :morning?, :registered, :spaces-left, :already-in?]]
      (map (fn [course]
             (select-keys course desired-keys))
           courses))))

(def sort-by-course-name
  (fn [courses]
    (sort-by :course-name courses)))

(def half-day-solution
  (fn [courses registrant instructor-count]
    (-> courses
        (annotate registrant instructor-count)
        visible-courses
        sort-by-course-name
        final-shape)))

(def solution
  (fn [courses registrant instructor-count]
    (map (fn [courses]
           (half-day-solution courses registrant instructor-count))
         (separate :morning? courses))))
