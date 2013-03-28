(ns homestake-t3-clojure.html-renderer-spec
  (:require [speclj.core :refer :all]
            [tictactoe.player :refer :all]
            [homestake-t3-clojure.html-renderer :refer :all]))

(def empty-board [nil nil nil nil nil nil nil nil nil])
(def fields [#"name='player1'"
             #"name='player1type'"
             #"name='player2'"
             #"name='player2type'"
             #"name='0'"
             #"name='1'"
             #"name='2'"
             #"name='3'"
             #"name='4'"
             #"name='5'"
             #"name='6'"
             #"name='7'"
             #"name='8'"
             #"name='move'"
             #"name='marker'"
             #"type='submit'"])

(defn -contains? [string matcher]
  (not (nil? (re-find matcher string))))

(describe "html-renderer"

  (it "formats a board into HTML"
    (should= "<div id='board'><div id='0'></div><div id='1'></div><div id='2'></div><div id='3'></div><div id='4'></div><div id='5'></div><div id='6'></div><div id='7'></div><div id='8'></div></div>" (render-board empty-board)))

  (it "formats a space into HTML"
    (should= "<div id='1'>TESTING!</div>" (render-space 1 "TESTING!"))
    (should= "<div id='1'></div>" (render-space 1 nil)))
  
  (it "renders the configuration page"
    (should (string? render-config-page)))
    
  (it "makes a new game link"
    (should (-contains? new-game-link #"a href"))
    (should (-contains? new-game-link #"New Game")))
    
  (it "renders the configuration page"
    (should= 1 (count (distinct (map #(-contains? render-config-page %) fields))))))
    
(run-specs)