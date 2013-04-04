(ns homestake-t3-clojure.homestake-wrapper-spec
  (:require [speclj.core :refer :all]
            [homestake-t3-clojure.HomestakeWrapper :refer :all])
  (:import homestake_t3_clojure.HomestakeWrapper))

(def request-hashmap (java.util.Hashtable. {"player1" "x" "player1type" "human" 
                                    "player2" "o" "player2type" "easy"
                                    "move" 0 "marker" "x"
                                    "0" "o"
                                    "1" "nil"
                                    "2" "nil"
                                    "3" "nil"
                                    "4" "nil"
                                    "5" "nil"
                                    "6" "nil"
                                    "7" "nil"
                                    "8" "x"}))

(defn -contains? [string matcher]
  (not (nil? (re-find matcher string))))

(describe "homestake-wrapper"

  (it "returns header values"
    (should (-contains? (str (.headerValues (HomestakeWrapper.))) #"content-type"))
    (should (-contains? (str (.headerValues (HomestakeWrapper.))) #"status")))

  (it "returns a response with game information query strings"
    (should
      (.containsKey (let [t3-response (HomestakeWrapper.)]
        (.setResponseBody t3-response "lol")
          (.response t3-response (org.homestake.utils.RequestParser. "GET /?move=0&marker=x&player1=x&player2=o&0=x&1=x&2=x&3=nil&4=nil&5=nil&6=nil&7=nil&8=nil HTTP/1.1\n")))
          "1-default-header")))
          
  (it "returns a response for a new game"
    (should
      (.containsKey (let [t3-response (HomestakeWrapper.)]
        (.setResponseBody t3-response "lol")
          (.response t3-response (org.homestake.utils.RequestParser. "GET / HTTP/1.1\n")))
          "1-default-header")))
          
  (it "gzips the body"
    (should
      (.containsKey (let [t3-response (HomestakeWrapper.)]
        (.setResponseBody t3-response "lol")
          (.response t3-response (org.homestake.utils.RequestParser. "GET / HTTP/1.1\n")))
          "2-gzip-body")))
          
  (it "sets the response body"
    (should= nil (.setResponseBody (HomestakeWrapper.) "LOL")))
    
  (it "returns the player list"
    (should= ["x" "human" "o" "easy"] (get-player-list request-hashmap)))
    
  (it "returns the request [requested-move marker]"
    (should= [0 "x"] (get-requested-move request-hashmap)))
    
  (it "returns the board"
    (should= ["o" nil nil nil nil nil nil nil "x"] (get-board request-hashmap))))

(run-specs)
