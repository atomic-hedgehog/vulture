(ns vulture.processors)

(defn generate-counter 
  "Generates a counter function.  If a name is supplies, it will always be present in the output (with a value of 0).
  If only a regex is supplied, the output will contain counts named by the matched regex (or the last capture group in the regex).  These counts will not be present unless there is at least one match in the file"
  ([regex]
   (fn [line]
    (let [match (re-find (re-pattern regex) line)]
      (cond 
        (vector? match) {(last match) 1} 
        match {match 1}
        :else nil))))
  ([processor-key regex]
   (fn [line]
    (let [match? (re-find (re-pattern regex) line)]
      (if match?
        {processor-key 1} 
        {processor-key 0})))))

(defn add-processor!
 "adds a processor function to the list of processors.  Each processor function will be applied to each line in the analyzed log.
 processors-atom --> an atom containing a map of processor functions
 processor-function --> a function of the form (f [line]) --> {:name 0}" 
  [processors-atom processor-function]
  (swap! processors-atom conj processor-function))


(defn run-processors [processors line]
  (filter identity 
          (map (fn [processor]
                 (processor line))
               processors)))

(defn apply-processors [processors lines]
  (reduce (fn [results line]
            (let [combined-map (into {} (run-processors processors line))]
              (merge-with + results combined-map)))
          {}
          lines))

(defn build-counter-from-config [processor-from-config]
  (cond
    (map? processor-from-config) (apply generate-counter (first processor-from-config))
    :else (generate-counter processor-from-config)))

(defn build-processors! [processors-atom processor-list]
  (doseq [processor processor-list]
    (let [proc-fn (build-counter-from-config processor)]
      (add-processor! processors-atom proc-fn))))


