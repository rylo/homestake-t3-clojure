(defproject homestake-t3-clojure "0.1.0-SNAPSHOT"
  :description "Homestake (Java HTTP) Clojure Tic-Tac-Toe Server"
  :url "http://www.github.com/rylo/homestake-t3-clojure/"
  :dependencies [[org.clojure/clojure "1.5.0"]
                 [homestake-server "0.1.2"]
                 [tictactoe "0.1.0-SNAPSHOT"]]
  :profiles {:dev {:dependencies [[speclj "2.5.0"]]}}
  :plugins [[speclj "2.5.0"]]
  :test-paths ["spec/"]
  :aot [homestake-t3-clojure.HomestakeWrapper]
  :main homestake-t3-clojure.core)
  
