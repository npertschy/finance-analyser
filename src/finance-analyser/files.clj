(ns files
  (:require
    [clojure.data.json :as json]))

(defn read-in [file]
  (slurp file :encoding "utf-8"))

(defn read-file [file]
  (-> file
      read-in))

(defn read-json [file]
  (-> file
      read-in
      (json/read-str :key-fn keyword)))

(comment
  (read-file "/Users/niki/Downloads/Umsatzanzeige_11-20_11-21.csv"))