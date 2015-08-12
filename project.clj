(defproject metosin/web-schemas "0.0.1"
  :description "Common web-based utilities for Prismatic Schema"
  :url "https://github.com/metosin/web-schemas"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "same as Clojure"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [prismatic/schema "0.4.3"]
                 [joda-time "2.8.1"]
                 [cheshire "5.5.0"]]
  :plugins [[codox "0.8.13"]]

  :cljx {:builds [{:rules :clj
                   :source-paths ["src"]
                   :output-path "target/generated/src"}
                  {:rules :cljs
                   :source-paths ["src"]
                   :output-path "target/generated/src"}
                  {:rules :clj
                   :source-paths ["test"]
                   :output-path "target/generated/test"}
                  {:rules :cljs
                   :source-paths ["test"]
                   :output-path "target/generated/test"}]}
  :prep-tasks [["cljx" "once"]]
  :source-paths ["src" "target/generated/src"]
  :test-paths ["test" "target/generated/test"]

  :codox {:src-dir-uri "http://github.com/metosin/web-schemas/blob/master/"
          :src-linenum-anchor-prefix "L"
          :src-uri-mapping {#"target/generated/src" #(str "src/" % "x")}}

  :profiles {:dev {:plugins [[com.keminglabs/cljx "0.6.0"]
                             [jonase/eastwood "0.2.1"]]
                   :dependencies [[criterium "0.4.3"]
                                  [org.clojure/clojure "1.7.0"]
                                  [org.clojure/clojurescript "0.0-3308"]]}
             :1.7 {:dependencies [[org.clojure/clojure "1.7.0"]]}}
  :aliases {"all" ["with-profile" "dev:dev,1.7"]
            "test-clj" ["all" "do" ["cljx" "once"] ["test"] ["check"]]})
