(defproject kryptos "0.1.0-SNAPSHOT"
  :description "Kryptos"
  :main kryptos.core
  :dependencies [[org.clojure/clojure "1.11.3"]]
  :repl-options {:init-ns kryptos.core}
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
