(ns vulture.core)


(def line-processors (atom {}))


(defn add-processor!
 "adds a processor function to the list of processors.  Each processor function will be applied to each line in the analyzed log.
 processors-atom --> an atom containing a map of processor functions
 processor-name --> the name of the function.  This will be the key in the results map.
 processor-function --> a function of the form (regex line) --> any" 
  [processors-atom processor-name processor-function]
  (swap! processors-atom merge {processor-name processor-function}))

(defn read-file [file-name]
  )

(defn apply-processors [processors lines]
  (reduce (fn [results line]
            (merge results {:total (inc (:total results))}))
          {:total 0}
          lines))

(defn process-file [file-name]
  (let [data-stream (read-file file-name)]
    (apply-processors line-processors data-stream)))
