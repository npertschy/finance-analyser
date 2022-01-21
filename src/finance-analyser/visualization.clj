(ns visualization
  (:require
    [com.hypirion.clj-xchart :as xchart]))

(defn x-y-map [data]
  (reduce conj {} (map (fn [[key value]] {(name key) value}) data)))

(defn skip-zero-amounts [data]
  (filter (fn [[_ value]] (not (zero? value))) data))

(defn- data-for-bar-chart [data]
  {"Übersicht" [(keys data) (vals data)]})

(defn bar-chart [data]
  (-> data
      skip-zero-amounts
      x-y-map
      data-for-bar-chart
      xchart/category-chart*
      xchart/view))