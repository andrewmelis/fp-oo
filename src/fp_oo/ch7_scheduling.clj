(ns fp-oo.ch7-scheduling)

(def answer-annotations
  (fn [courses registrant-info]
    (let [checking-set (set (:courses registrant-info))]
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
  (fn [courses instructor-count manager?]
    (let [out-of-instructors?
          (= instructor-count
             (count (filter (fn [course] (not (:empty? course)))
                            courses)))]
      (map (fn [course]
             (assoc course
                    :unavailable? (or (and manager?
                                           (not (:morning? course)))
                                      (:full? course)
                                      (and out-of-instructors?
                                           (:empty? course)))))
           courses))))

(def annotate
  (fn [courses registrant-info instructor-count]
    (let [manager? (or (:manager? registrant-info) false)]
      (-> courses
          (answer-annotations registrant-info)
          domain-annotations
          (note-unavailability instructor-count manager?)))))

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

(def half-day-solution
  (fn [courses registrant-info instructor-count]
    (-> courses
        (annotate registrant-info instructor-count)
        visible-courses
        ((fn [courses] (sort-by :course-name courses)))
        final-shape)))

(def solution
  (fn [courses registrant-info instructor-count]
    (map (fn [courses]
           (half-day-solution courses
                              registrant-info
                              instructor-count))
         (separate :morning? courses))))
