(ns data-preparation
  (:require
    [files :as f]
    [clojure.string :as str]))

(defn- split-line [line]
  (str/split line #";"))

(defn- split-columns [lines]
  (map split-line lines))

(defn remove-escaped-characters-per-line [line]
  (map #(str/replace % (str/re-quote-replacement "\"") "") line))

(defn- remove-escaped-characters [lines]
  (map remove-escaped-characters-per-line lines))

(defn- date? [value]
  (re-find #"\d\d\.\d\d\.\d\d\d\d" value))

(defn- starts-with-date? [line]
  (and (date? (first line)) (date? (second line))))

(defn- transactions-only [data]
  (filter starts-with-date? data))

(defn- value-for-index [line index]
  (-> line
      vec
      (get index)))

(defn- filter-by-indices [line indices]
  (map #(value-for-index line %) indices))

(defn- relevant-columns-only [data indices]
  (map (fn [line] (filter-by-indices line indices)) data))

(defn- transaction-in-frame? [line frame]
  (-> line
      first
      (str/ends-with? frame)))

(defn- in-time-frame [data frame]
  (filter #(transaction-in-frame? % frame) data))

(defn- concat-columns [line]
  (let [[date company reference price] line]
    [date (str company " - " reference) price]))

(defn- combine-transaction-info [data]
  (map concat-columns data))

(defn- fix-number-format [value]
  (-> value
      (str/replace "." "")
      (str/replace "," ".")))

(defn- to-number [value]
  (-> value
      fix-number-format
      bigdec))

(defn- amount-as-number [line]
  (let [[date info amount] line]
    [date info (to-number amount)]))

(defn- amounts-as-numbers [data]
  (map amount-as-number data))

(defn prepared-data [user settingsFile]
  (let [settings (f/read-json settingsFile)]
    (-> settings
        (get-in [user :file])
        f/read-file
        str/split-lines
        split-columns
        remove-escaped-characters
        transactions-only
        (relevant-columns-only (get-in settings [user :relevant-columns]))
        (in-time-frame (settings :time-frame))
        combine-transaction-info
        amounts-as-numbers)))

(comment
  (take 20 (prepared-data :niki "settings.json")))
