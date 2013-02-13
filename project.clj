(defproject yolk "0.1.0-SNAPSHOT"
  :description "Bacon and Eggs"
  :url "https://github.com/cicayda/yolk"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.5.0-RC1"]
                 [jayq "2.2.0"]]
  :plugins [[lein-cljsbuild "0.3.0"]]
  :cljsbuild {:builds
              {:main
               {:source-paths ["src"]
                :compiler {:externs ["externs/jquery-1.8.js"
                                     "externs/bootstrap.js"
                                     "externs/bacon.js"
                                     "externs/bacon.ui.js"]
                           :optimizations :advanced
                           :pretty-print false}}
               :unit
               {:source-paths ["src" "test"]
                :compiler {:output-to "resources/js/unit-test.js"
                           :optimizations :whitespace
                           :pretty-print true}}}
              :test-commands
              {"unit" ["phantomjs"
                       "resources/js/runner.js"
                       "resources/test.html"]}})