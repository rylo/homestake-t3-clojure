(ns homestake-t3-clojure.homestake-wrapper
  (:import (org.homestake.response.ServerResponse))
  (:require [homestake-t3-clojure.t3-wrapper :refer :all])
  (:gen-class
    :name homestake-t3-clojure.homestake-wrapper
    :extends org.homestake.response.ServerResponse
    :exposes-methods {response responseSuper
                      setResponseBody setResponseBodySuper}))

(defn -setResponseBody [this body]
  (.setResponseBodySuper this body))

(defn -headerValues [this]
  (java.util.HashMap. {"status" (int 200), "content-type" "text/html"}))

(defn nil-converter [value]
  (if (= "nil" value)
    nil
    value))

(defn get-player-list [querystring-hashtable]
  (let [player-fields ["player1" "player1type" "player2" "player2type"]]
    (map #(.get querystring-hashtable %) player-fields)))
    
(defn get-requested-move [querystring-hashtable]
  (let [requested-move-fields ["move" "marker"]]
    (map #(.get querystring-hashtable %) requested-move-fields)))
    
(defn get-board [querystring-hashtable]
  (let [space-numbers (range 0 9)]
    (map #(nil-converter (.get querystring-hashtable (str %))) space-numbers)))
    
(defn processed-request [request]
  (get-requested-move request))

(defn processed-game [request]
  [(get-board request) (get-player-list request)])

(defn generate-response-body [this request]
  (try 
    (str (homestake-t3-clojure.t3-wrapper/process-request 
      (processed-request (.queryStrings request)) 
      (processed-game (.queryStrings request))))
      (catch Exception exception
        (.printStackTrace exception)
        "Internal server error")))

(defn -response [this request]
  (let [response-body (generate-response-body this request)]
    (do
      (-setResponseBody this response-body)
      (.responseSuper this request))))
