(defproject metosin/web-schemas "0.1.0-SNAPSHOT"
  :description "Common utilities for Prismatic Schema"
  :url "https://github.com/metosin/web-schemas"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "same as Clojure"}
  :dependencies [[prismatic/schema "1.0.3"]
                 [com.cognitect/transit-cljs "0.8.232"]
                 [com.cognitect/transit-clj "0.8.285"]]
  :plugins [[funcool/codeina "0.3.0"]]

  :codeina {:target "doc"
            :src-uri "http://github.com/metosin/web-schemas/blob/master/"
            :src-uri-prefix "#L"}

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.7.0"]
                                  [org.clojure/clojurescript "1.7.170"]]}
             :1.8 {:dependencies [[org.clojure/clojure "1.8.0-rc1"]]}}
  :aliases {"all" ["with-profile" "dev:dev,1.8"]
            "test-clj" ["all" "do" ["test"] ["check"]]})
