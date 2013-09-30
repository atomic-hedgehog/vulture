(ns vulture.core
  (:gen-class)
  (:require 
    [clojure.java.io :as io]
    [vulture.processors :as proc]
    [vulture.config :as conf]))

(def line-processors (atom ()))
(def config (conf/load-config) )

(defn read-file [file-reader]
  (line-seq file-reader) )

(defn process-file [processors file-reader]
  (let [data-stream (read-file file-reader)]
    (proc/apply-processors @processors data-stream)))

(defn render-results [results-map]
  (doseq [result results-map]
    (println (str (first result) "\t" (second result)))))

(defn -main []
  (doseq [logfile config]
    (let [line-processors (atom ())
          filename (first logfile)
          processors (last logfile)]
      (println "Scanning " filename)
      (proc/build-processors! line-processors processors)
      (with-open [rdr (io/reader filename)]
        (render-results (process-file line-processors rdr))))))


