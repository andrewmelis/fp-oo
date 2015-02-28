(ns fp-oo.ch7-scheduling-test
  (require [midje.sweet :refer :all]
           [fp-oo.ch7-scheduling :refer :all]))

(facts "answer-annotations"
  (fact "adds spaces-left and already-in? to course map"
    (answer-annotations [{:course-name "zigging" :limit 4, :registered 3}
                         {:course-name "zagging" :limit 1, :registered 1}]
                        {:courses ["zagging"]})
    => [{:already-in? false, :spaces-left 1, :registered 3, :limit 4, :course-name "zigging"}
        {:already-in? true, :spaces-left 0, :registered 1, :limit 1, :course-name "zagging"}]))

(facts "domain-annotations"
  (fact "adds empty? and full? to course map"
    (domain-annotations [{:registered 1, :spaces-left 1},
                         {:registered 0, :spaces-left 1},
                         {:registered 1, :spaces-left 0}])
    => [{:registered 1, :spaces-left 1, :full? false, :empty? false,}
        {:registered 0, :spaces-left 1, :full? false, :empty? true,}
        {:registered 1, :spaces-left 0, :full? true, :empty? false,}]))

(facts "note-unavailability"
  (fact "full classes are marked as unavailable"
    (note-unavailability [{:full? true}] 1 false)
    => [{:full? true, :unavailable? true}])
  (facts "when class is not full"
    (fact "class is unavailable when there are no instructors and it's empty"
      (note-unavailability [{:full? false, :empty? true}] 0 false)
      => [{:unavailable? true, :full? false, :empty? true}])
    (fact "class is available when there are remaining instructors"
      (note-unavailability [{:full? false, :empty? true}] 1 false)
      => [{:unavailable? false, :full? false, :empty? true}])
    (fact "class is available when the course is not empty"
      (note-unavailability [{:full? false, :empty? false}] 1 false)
      => [{:unavailable? false, :full? false, :empty? false}]))
  (fact "when registrant is a manager"
    (note-unavailability [{:morning? false}] 1 true)
    => [{:unavailable? true, :morning? false}]))

(facts "annotate fills the input maps with the necessary information"
  (fact "it adds spaces-left, already-in?, empty?, full?, and unavailable?"
    (annotate [{:course-name "Zigging", :morning? true, :limit 5, :registered 3}]
              {:courses ["Zigging"]}
              1)
    => [{:course-name "Zigging", :morning? true, :limit 5, :registered 3,
         :unavailable? false, :full? false, :empty? false, :spaces-left 2, :already-in? true}]))

(facts "separate"
  (fact "partitions elements of sequences into trues and falses"
    (let [predicate true?
          sequence [true false]]
      (separate predicate sequence))
    => [[true] [false]]))

(facts "visible-courses"
  (fact "separate all courses into gurantees and possibles. remove unavailable"
    (visible-courses [{:already-in? true},
                      {:unavailable? false},
                      {:unavailable? true}])
    => [{:already-in? true}, {:unavailable? false}]))

(facts "final-shape"
  (fact "removes keys from annotated course maps that aren't needed in output"
    (final-shape [{:course-name "Zigging", :morning? true, :limit 5, 
                   :registered 3, :unavailable? false, :full? false,
                   :empty? false, :spaces-left 2, :already-in? true}])
    => [{:course-name "Zigging", :morning? true, :registered 3, :spaces-left 2,
         :already-in? true}]))

(facts "half-day-solution"
  (fact "it annotates the input courses, calls visible-courses, sorts them, and puts into final-shape"
    (half-day-solution [{:course-name "Zigging", :morning? true, :limit 5, :registered 3}
                        {:course-name "Zagging", :morning? true, :limit 3, :registered 3}
                        {:course-name "Thinking", :morning? true, :limit 7, :registered 2}
                        {:course-name "Drinking", :morning? true, :limit 9, :registered 0}
                        {:course-name "Flopping", :morning? true, :limit 4, :registered 0}]
                       {:courses ["Zigging"]}
                       4)
    => [{:course-name "Drinking" :morning? true, :registered 0, :spaces-left 9, :already-in? false}
        {:course-name "Flopping" :morning? true, :registered 0, :spaces-left 4, :already-in? false}
        {:course-name "Thinking" :morning? true, :registered 2, :spaces-left 5, :already-in? false}
        {:course-name "Zigging" :morning? true, :registered 3, :spaces-left 2, :already-in? true}]))

(facts "solution"
  (fact "it processes half-days separately and outputs"
    (solution [{:course-name "Zigging", :morning? true, :limit 5, :registered 3}
               {:course-name "Zagging", :morning? true, :limit 3, :registered 3}
               {:course-name "Thinking", :morning? true, :limit 7, :registered 2}
               {:course-name "Drinking", :morning? true, :limit 9, :registered 0}
               {:course-name "Flopping", :morning? true, :limit 4, :registered 0}
               {:course-name "Zigging", :morning? false, :limit 5, :registered 3}
               {:course-name "Zagging", :morning? false, :limit 3, :registered 3}
               {:course-name "Thinking", :morning? false, :limit 7, :registered 2}
               {:course-name "Drinking", :morning? false, :limit 9, :registered 0}
               {:course-name "Flopping", :morning? false, :limit 4, :registered 0}]
              {:courses ["Zigging"]} ;; how does this account for morning or afternoon?
              4)
    => [[{:course-name "Drinking" :morning? true, :registered 0, :spaces-left 9, :already-in? false}
        {:course-name "Flopping" :morning? true, :registered 0, :spaces-left 4, :already-in? false}
        {:course-name "Thinking" :morning? true, :registered 2, :spaces-left 5, :already-in? false}
        {:course-name "Zigging" :morning? true, :registered 3, :spaces-left 2, :already-in? true}]
        [{:course-name "Drinking" :morning? false, :registered 0, :spaces-left 9, :already-in? false}
        {:course-name "Flopping" :morning? false, :registered 0, :spaces-left 4, :already-in? false}
        {:course-name "Thinking" :morning? false, :registered 2, :spaces-left 5, :already-in? false}
        {:course-name "Zigging" :morning? false, :registered 3, :spaces-left 2, :already-in? true}]]))
