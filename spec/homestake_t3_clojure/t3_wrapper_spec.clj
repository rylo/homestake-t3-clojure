(ns homestake-t3-clojure.t3-wrapper-spec
  (:require [speclj.core :refer :all]
            [tictactoe.player :refer :all]
            [homestake-t3-clojure.t3-wrapper :refer :all]))

(def requested-move [5 "x"])
(def players [(tictactoe.player.EasyComputer. "o") (tictactoe.player.Human. "x")])
(def empty-board [nil nil nil nil nil nil nil nil nil])
(def almost-won-board [nil nil nil "x" "x" nil nil nil nil])
(def mock-new-game [empty-board [nil nil]])
(def mock-current-game [almost-won-board players])

(defn -contains? [string string-to-find]
  (.contains (java.lang.String. string) string-to-find))
  
(describe "t3-wrapper"

  (it "returns a move from a request"
    (should= 5 (-get-move requested-move)))
    
  (it "alters a board"
    (should= [nil "x" nil nil nil nil nil nil nil] (alter-board empty-board [1 "x"] (last players)))
    (should= empty-board (alter-board empty-board [10293402934 "x"] (last players)))
    (should-not= empty-board (alter-board empty-board [1 "o"] (first players)))
    (should-not= almost-won-board (alter-board almost-won-board [1 "o"] (tictactoe.player.UltimateComputer. "o"))))
    
  (it "returns a marker from a request"
    (should= "x" (-get-marker requested-move)))
    
  (it "processes a request and returns a string"
    (should (string? (process-request requested-move [empty-board players]))))
    
  (it "processes a request and returns the altered board"
    (should (-contains?
      (process-request requested-move [almost-won-board players]) "Player x wins!"))
    (should (-contains?
      (process-request requested-move [empty-board players]) "<div id='board'>"))
    (should (-contains?
      (process-request requested-move [almost-won-board players]) "<div id='3' class='x'>x</div><div id='4' class='x'>x</div>")))
  
  (it "returns a player with a given marker"
    (should= (last players) (get-player-by-marker players "x"))
    (should= (first players) (get-player-by-marker players "o")))
    
  (it "returns true if it's the first move"
    (should (first-move? ["first-move" "x"])))
            
  (it "returns true if it's a new game"
    (should (new-game? mock-new-game))
    (should-not (new-game? mock-current-game)))
  
  (it "returns the player type from t3"
    (should= tictactoe.player.Human (class (create-player "human" "x")))
    (should= tictactoe.player.Human (class (create-player "class+tictactoe.player.Human" "x")))
    (should= tictactoe.player.EasyComputer (class (create-player "easy" "x")))
    (should= tictactoe.player.EasyComputer (class (create-player "class+tictactoe.player.EasyComputer" "x")))
    (should= tictactoe.player.UltimateComputer (class (create-player "ultimate" "x")))
    (should= tictactoe.player.UltimateComputer (class (create-player "class+tictactoe.player.UltimateComputer" "x"))))
    
  (it "generates a list of players"
    (should= [#tictactoe.player.Human{:marker "x"} #tictactoe.player.Human{:marker "o"}] (generate-player-list ["x" "human" "o" "human"]))))

(run-specs)
