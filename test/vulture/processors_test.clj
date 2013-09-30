(ns vulture.processors-test
  (:use midje.sweet)
  (:use [vulture.processors]))

(def line-processors (atom ()))
(facts "about `add-processor`"
       (fact "calling add-processor returns the line processor map"
             (add-processor! line-processors ...processor...) => (contains [...processor...]))
       (fact "anything added will be in the line-processors map"
             @line-processors => (contains ...processor...)))

(facts "about `run-processors`"
       (against-background [(before :contents (do
                                                (reset! line-processors ())
                                                (add-processor! line-processors (generate-counter :total #".*"))
                                                (add-processor! line-processors (generate-counter #"(line)"))
                                                (add-processor! line-processors (generate-counter #"^E"))
                                                (add-processor! line-processors (generate-counter :errors #"^E "))))] 
                           (fact "returns a vector of maps"
                                 (run-processors @line-processors "E line 1") => (contains {:errors 1} {"E" 1} {"line" 1} {:total 1} ))))

