(defproject kryptos "0.1.0"
  :description "Kryptos"
  :main kryptos.core
  :aot :all
  :global-vars {*warn-on-reflection* true}
  :license {:name "Eclipse Public License - v 2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.3"]]
  :repl-options {:init-ns kryptos.core}
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :repl {:plugins [[nrepl "1.1.1"]]}}
  :manifest {"Revision" ~(:out (clojure.java.shell/sh "git" "log" "-n1" "--pretty=format:%h"))
             "Built-On" ~(-> "America/Chicago"
                             (java.time.ZoneId/of)
                             (java.time.OffsetDateTime/now)
                             str)})
