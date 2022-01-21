(ns starter
  (:gen-class))

(require
  '[analysis])

(defn -main [& args]
  (let [[method path user] args
        params [path (keyword user)]
        f (resolve (symbol "analysis" method))]
    (loop [result (apply f params)]
      (println (first result))
      (if (empty? result)
        (println "Done")
        (recur (rest result))))))
