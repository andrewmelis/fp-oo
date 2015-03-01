(ns fp-oo.ch7-scheduling-variant-test
  (require [midje.sweet :refer :all]
           [fp-oo.ch7-scheduling-variant :refer :all]))

(fact "not insane?"
  (= 1 2)
  =not=> true)


(facts "solution" ;; integration style test
  (fact "it processes half-days separately and outputs"
    (solution [{:prerequisites ["prereq"], :course-name "Zigging", :morning? true, :limit 5, :registered 3}
               {:course-name "Zagging", :morning? true, :limit 3, :registered 3}
               {:prerequisites ["Zigging"], :course-name "Thinking", :morning? true, :limit 7, :registered 2}
               {:course-name "Drinking", :morning? true, :limit 9, :registered 0}
               {:course-name "Flopping", :morning? true, :limit 4, :registered 0}
               {:course-name "Zigging", :morning? false, :limit 5, :registered 3}
               {:course-name "Zagging", :morning? false, :limit 3, :registered 3}
               {:course-name "Thinking", :morning? false, :limit 7, :registered 2}
               {:course-name "Drinking", :morning? false, :limit 9, :registered 0}
               {:course-name "Flopping", :morning? false, :limit 4, :registered 0}]
              {:past-courses ["prereq"], :manager? true, :taking-now ["Zigging"]} ;; how does this account for morning or afternoon?
              4)
    => [[{:course-name "Drinking" :morning? true, :registered 0, :spaces-left 9, :already-in? false}
         {:course-name "Flopping" :morning? true, :registered 0, :spaces-left 4, :already-in? false}
         {:course-name "Zigging" :morning? true, :registered 3, :spaces-left 2, :already-in? true}]
        [{:course-name "Zigging" :morning? false, :registered 3, :spaces-left 2, :already-in? true}]]))

