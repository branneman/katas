(defproject katas "0.1.0-SNAPSHOT"
  :description "Software Craftsmanship Katas in Clojure"
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :main ^:skip-aot katas.main
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
