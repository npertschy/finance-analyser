(ns files
  (:require
    [clojure.java.io :as io]
    [clojure.data.json :as json]))

(def default-settings "settings.json")

(defn read-in [file]
  (slurp file :encoding "utf-8"))

(defn read-file [file]
  (-> file
      read-in))

(defn- file-to-load [file]
  (let [resource (io/resource file)]
    (if (nil? resource)
      (io/resource default-settings)
      resource)))



(defn read-json [file]
  (-> file
      file-to-load
      read-in
      (json/read-str :key-fn keyword)))


(comment
  (read-json "settings.json"))

(comment
  (read-json "noSettings.json"))

(comment
  (read-file "/Users/niki/Downloads/Umsatzanzeige_11-20_11-21.csv"))