# homestake-t3-clojure

A Clojure library designed to combine the functionality of my Clojure tic-tac-toe application and my Java HTTP server.
Created as part of my residency at 8th Light.

## Usage

* Start the server
      
        lein run

* Watch and compile CoffeeScript files:

        coffee -w -c javascript/spec/*.coffee & coffee -w -c javascript/src/*.coffee & coffee -o public/javascript -w -c javascript/src/*.coffee

## License

Copyright © 2013 Rylan Dirksen

Distributed under the Eclipse Public License, the same as Clojure.
