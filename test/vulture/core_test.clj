(ns vulture.core-test
  (:use midje.sweet)
  (:use [vulture.core])
  (:require [vulture.processors :as proc]))


(facts "about `process-file`"
       (against-background [(before :contents (do
                                               (reset! line-processors ())
                                               (proc/add-processor! line-processors (proc/generate-counter :total #".*"))
                                               (proc/add-processor! line-processors (proc/generate-counter :errors #"^E "))))] 
                           (fact "Returns the total line count, even if no processors are specified"
                                 (process-file line-processors ...file-name...) => (contains {:total 3} {:errors 2})
                                 (provided
                                   (read-file ...file-name...) => (seq ["E line 1" "W line 2" "E line 3"])))))



