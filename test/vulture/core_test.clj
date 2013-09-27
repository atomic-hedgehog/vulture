(ns vulture.core-test
  (:use midje.sweet)
  (:use [vulture.core]))

(facts "about `add-processor`"
       (fact "calling add-processor returns the line processor map"
             (add-processor! line-processors :processor ...processor...) => {:processor ...processor...})
       (fact "anything added will be in the line-processors map"
             (:processor @line-processors) => ...processor...))

(facts "about `process-file`"
       (fact "Returns the total line count, even if no processors are specified"
             (process-file ...file-name...) => {:total 3}
             (provided
               (read-file ...file-name...) => (seq ["line 1" "line 2" "line 3"] ))))

