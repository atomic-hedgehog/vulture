(ns vulture.core)


(def line-processors (atom () ))

(defn generate-counter [processor-key regex]
  (fn [line]
    (let [match? (re-find regex line)]
      (if match?
        {processor-key 1} 
        {processor-key 0}))))

(comment def line-count-processor (generate-counter :total #".*"))
(comment def error-count-processor (generate-counter :errors #"^E .*"))


(defn add-processor!
 "adds a processor function to the list of processors.  Each processor function will be applied to each line in the analyzed log.
 processors-atom --> an atom containing a map of processor functions
 processor-function --> a function of the form (f [line]) --> {:name 0}" 
  [processors-atom processor-function]
  (swap! processors-atom conj processor-function))

(defn read-file [file-name]
  )

(defn run-processors [processors line]
  (map (fn [processor]
         (processor line))
       processors))

(defn apply-processors [processors lines]
  (reduce (fn [results line]
            (let [combined-map (into {} (run-processors processors line))]
              (merge-with + results combined-map)))
          {}
          lines))

(defn process-file [processors file-name]
  (let [data-stream (read-file file-name)]
    (apply-processors @processors data-stream)))
