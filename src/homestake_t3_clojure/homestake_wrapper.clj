(ns homestake-t3-clojure.homestake-wrapper
  (:import (org.homestake.response.ServerResponse))
  (:gen-class
    :name homestake-t3-clojure.homestake-wrapper
    :extends org.homestake.response.ServerResponse
    :methods [[headerValues [] java.util.HashMap]]
    :exposes-methods {response responseSuper
                      setResponseBody setResponseBodySuper
                      HTMLWrap HTMLWrapSuper}))

(defn setResponseBody [this body]
  (.setResponseBodySuper this body))
    
(defn HTMLWrap [this text]
  (.HTMLWrapSuper this text))
  
(defn response [this request]
  (.responseSuper this request))
    
(defn -headerValues [this]
  (java.util.HashMap. {"status" (int 200), "content-length" 500, "content-type" "text/html"}))