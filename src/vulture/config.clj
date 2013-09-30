(ns vulture.config
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(defn load-config
  ([]
   (load-config "vulture.edn")) 
  ([file-name]
      (with-open [rdr (java.io.PushbackReader. (io/reader file-name))]
        (edn/read rdr))))

