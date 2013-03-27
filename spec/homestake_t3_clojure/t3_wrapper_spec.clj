(ns homestake-t3-clojure.t3-wrapper-spec
  (:require [speclj.core :refer :all]
            [homestake-t3-clojure.t3-wrapper :refer :all]))

(def requested-move [5 "x"])
(def players [(tictactoe.player.EasyComputer. "o") (tictactoe.player.EasyComputer. "x")])
(def empty-board [nil nil nil nil nil nil nil nil nil])
(def almost-won-board [nil nil nil "x" "x" nil nil nil nil])

(describe "t3-wrapper"
  (it "returns a move from a request"
    (should= 5 (get-move requested-move)))
  (it "returns a marker from a request"
    (should= "x" (get-marker requested-move)))
  (it "processes a request and returns a string"
    (should (string? (process-request requested-move [empty-board players]))))
  (it "processes a request and returns the altered board"
    (should (doto 
      (java.lang.String. (process-request requested-move [almost-won-board players]))
      (.contains "Player x wins!"))))
  (it "formats a board into HTML"
    (should= (format "<div>%s</div>" (apply str (repeat 9 "<div></div>"))) (render-board empty-board)))
  (it "formats a space into HTML"
    (should= "<div>TESTING!</div>" (render-space "TESTING!"))
    (should= "<div></div>" (render-space nil)))
  (it "renders the configuration page"
    (should (string? (render-config-page)))))

(run-specs)
