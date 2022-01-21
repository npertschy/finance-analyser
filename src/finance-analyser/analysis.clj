(ns analysis
  (:require
    [clojure.string :as str]
    [clojure.set :as set]
    [data-preparation :as data]
    [visualization :as vis]
    [configuration :as config]))

(defn sum-up [predicate values]
  (reduce (with-precision 2 +) (filter predicate values)))

(defn sum-up-transactions [transactions]
  (->> transactions
       (map last)
       (sum-up number?)))

(defn in-out [values]
  (let [in (sum-up pos? values)
        out (sum-up neg? values)]
    {:in      in
     :out     out
     :balance (+ in out)}))

(defn summary [settingsFile user]
  (->> settingsFile
       (data/prepared-data user)
       (map last)
       in-out))

(comment
  (summary "settings.json" :niki))

(defn transaction-matches? [[_ info _] pattern]
  (str/includes? info pattern))

(defn transactions-by-category [data category]
  (filter #(transaction-matches? % category) data))

(defn transactions-by-categories [data categories]
  (mapcat #(transactions-by-category data %) categories))

(defn result-of-matching-transactions [_ matching-transactions]
  (reduce conj [(sum-up-transactions matching-transactions)] (vec matching-transactions)))

(defn transactions-by-topic [data [topic categories]]
  (let [matching-transactions (transactions-by-categories data categories)]
    {topic (result-of-matching-transactions topic matching-transactions)}))

(defn transactions-by-topics [data topics]
  (reduce conj {} (map #(transactions-by-topic data %) topics)))

(defn collect-transactions [keyValue]
  (-> keyValue
      second
      rest))

(defn group-by-categories [data settingsFile]
  (let [settings (config/read-settings settingsFile)
        result (transactions-by-topics data (settings :categories))
        matched (reduce conj #{} (mapcat collect-transactions result))
        not-matched (set/difference (set data) matched)
        ]
    (assoc result :not-mapped (result-of-matching-transactions :not-mapped not-matched))))

(defn analysis [settingsFile user]
  (-> user
      (data/prepared-data settingsFile)
      (group-by-categories settingsFile)))

(comment
  (analysis "settings.json" :niki))

(defn show-not-mapped [settingsFile user]
  (take 10 (-> settingsFile
               (analysis user)
               (get :not-mapped))))

(comment
  (show-not-mapped "settings.json" :niki))

(defn omit-transaction-details [data]
  (map (fn [category] [(first category) (first (second category))]) data))

(defn compact-analysis [settingsFile user]
  (-> user
      (data/prepared-data settingsFile)
      (group-by-categories settingsFile)
      omit-transaction-details))

(comment
  (compact-analysis "settings.json" :niki))

(defn visual-analysis [settingsFile user]
  (-> settingsFile
      (compact-analysis user)
      vis/bar-chart))

(comment
  (visual-analysis "settings.json" :niki))

(defn merged-analysis [settingsFile]
  (let [data-of-niki (data/prepared-data :niki settingsFile)
        data-of-lina (data/prepared-data :lina settingsFile)
        all-data (concat data-of-niki data-of-lina)]
    (-> all-data
        (group-by-categories settingsFile)
        )))

(comment
  (merged-analysis "settings.json"))

(defn compact-merged-analysis [settingsFile]
  (->> settingsFile
       merged-analysis
       omit-transaction-details))

(comment
  (compact-merged-analysis "settings.json"))

(defn merged-summary [settingsFile]
  (let [data-of-niki (data/prepared-data :niki settingsFile)
        data-of-lina (data/prepared-data :lina settingsFile)
        all-data (concat data-of-niki data-of-lina)]
    (->> all-data
         (map last)
         in-out)))

(comment
  (merged-summary "settings.json"))

(defn visual-merged-analysis [settingsFile]
  (-> settingsFile
      (compact-merged-analysis)
      vis/bar-chart))

(comment
  (visual-merged-analysis "settings.json"))