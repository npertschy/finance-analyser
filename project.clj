(defproject finance-analyser "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/data.json "2.4.0"]
                 [com.hypirion/clj-xchart "0.2.0"]]
  :main starter
  :aot [starter]
  ;:repl-options {:init-ns starter}
  :source-paths ["src/finance-analyser"]
  :resource-paths ["resources"]
  :test-paths ["test/finance-analyser"]
  :classes ["target/classes"])
