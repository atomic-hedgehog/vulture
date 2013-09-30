(ns vulture.core
  (:require [vulture.processors :as proc]
            [vulture.config :as config]))

(def line-processors (atom () ))

(defn read-file [filename]
  )

(defn process-file [processors file-name]
  (let [data-stream (read-file file-name)]
    (proc/apply-processors @processors data-stream)))
