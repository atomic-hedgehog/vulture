(ns vulture.core-test
  (:use midje.sweet)
  (:use [vulture.core]))

(facts "about `add-processor`"
       (fact "calling add-processor returns the line processor map"
             (add-processor! line-processors ...processor...) => (contains [...processor...]))
       (fact "anything added will be in the line-processors map"
             @line-processors => (contains ...processor...)))

(facts "about `run-processors`"
       (against-background [(before :contents (do
                                               (reset! line-processors ())
                                               (add-processor! line-processors (generate-counter :total #".*"))
                                               (add-processor! line-processors (generate-counter :errors #"^E "))))] 
                           (fact "returns a vector of maps"
                                 (run-processors @line-processors "E line 1") => (contains {:errors 1} {:total 1}))))

(facts "about `process-file`"
       (fact "Returns the total line count, even if no processors are specified"
             (process-file line-processors ...file-name...) => (contains {:total 3} {:errors 2})
             (provided
               (read-file ...file-name...) => (seq ["E line 1" "W line 2" "E line 3"]))))

