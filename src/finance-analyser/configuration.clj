(ns configuration
  (:require
    [files :as f]))

(defn read-settings [file]
  (-> file
      f/read-json))

(comment
  (read-settings "settings.json"))
