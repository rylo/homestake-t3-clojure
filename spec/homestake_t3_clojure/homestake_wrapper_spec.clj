(ns homestake-t3-clojure.homestake-wrapper-spec
  (:require [speclj.core :refer :all]
            [homestake-t3-clojure.homestake-wrapper :refer :all]))

(describe "homestake-wrapper"
  (it "returns header values"
    (should= {"status" 200, "content-length" 500, "content-type" "text/html"} 
      (.headerValues (homestake-t3-clojure.homestake-wrapper.))))
  (it "returns a response"
    (should
      (.containsKey (let [wrapper (homestake-t3-clojure.homestake-wrapper.)]
        (.setResponseBody wrapper "lol")
          (.response wrapper (org.homestake.utils.RequestParser. "GET /fun/?sentence=lol HTTP/1.1\n")))
          "1-default-header")))
  (it "sets the response body"
    (should= nil (.setResponseBody (homestake-t3-clojure.homestake-wrapper.) "LOL"))))

(run-specs)