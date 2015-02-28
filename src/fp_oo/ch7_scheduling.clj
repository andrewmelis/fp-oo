(ns fp-oo.ch7-scheduling)
(use 'clojure.set)

(def answer-annotations
  (fn [courses registrant]
    (let [checking-set (set (:taking-now registrant))]
      (map (fn [course]
             (assoc course
                    :spaces-left (- (:limit course)
                                    (:registered course))
                    :already-in? (contains? checking-set
                                            (:course-name course))))
           courses))))

(def domain-annotations
  (fn [courses]
    (map (fn [course]
           (assoc course
                  :empty? (zero? (:registered course))
                  :full? (zero? (:spaces-left course))))
         courses)))

(def note-unavailability
  (fn [courses instructor-count registrant]
    (let [out-of-instructors?
          (= instructor-count
             (count (filter (fn [course] (not (:empty? course)))
                            courses)))]
      (map (fn [course]
             (assoc course
                    :unavailable? (or (not (superset? (set (:past-courses registrant))
                                                      (set (:prerequisites course))))
                                      (and (:manager? registrant)
                                           (not (:morning? course)))
                                      (:full? course)
                                      (and out-of-instructors?
                                           (:empty? course)))))
           courses))))

(def annotate
  (fn [courses registrant instructor-count]
    (-> courses
        (answer-annotations registrant)
        domain-annotations
        (note-unavailability instructor-count registrant))))

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
           (half-day-solution courses
                              registrant
                              instructor-count))
         (separate :morning? courses))))
