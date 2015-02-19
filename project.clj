(defproject jing-logic "0.1.0-SNAPSHOT"
  :description "A Tic-tac-toe game implemented in logic."
  :url "http://github.com/sorpaas/jing-logic"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/core.logic "0.8.8"]
                 [org.clojure/tools.macro "0.1.2"]]
  :main ^:skip-aot jing-logic.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
