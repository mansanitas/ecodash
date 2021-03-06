(task-options!
 pom  {:project     'Servir-Mekong/ecodash
       :version     "1.0.0"
       :description "EVI web explorer for Southeast Asia"
       :url         "http://ecodash-beta.appspot.com"}
 repl {:eval        '(set! *warn-on-reflection* true)})

(set-env!
 :source-paths   #{"src"}
 ;; :resource-paths #{"../static"}
 :dependencies   '[[adzerk/boot-cljs           "1.7.228-2" :scope "test"]
                   [adzerk/boot-cljs-repl      "0.3.3"     :scope "test"]
                   [adzerk/boot-reload         "0.4.13"    :scope "test"]
                   [com.cemerick/piggieback    "0.2.1"     :scope "test"]
                   [org.clojure/tools.nrepl    "0.2.12"    :scope "test"]
                   [weasel                     "0.7.0"     :scope "test"]
                   [org.clojure/clojure        "1.8.0"]
                   [org.clojure/clojurescript  "1.9.293"]
                   [reagent                    "0.6.0"]
                   [org.clojure/core.async     "0.2.391"]
                   [cljs-http                  "0.1.41"]
                   [com.cognitect/transit-cljs "0.8.239"]
                   [cljsjs/google-maps         "3.18-1"]])

(require
  '[adzerk.boot-cljs      :refer [cljs]]
  '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
  '[adzerk.boot-reload    :refer [reload]])

(deftask development []
  (comp (watch)
        (cljs-repl)
        (reload)
        (cljs :optimizations    :none
              :source-map       true
              :compiler-options {:asset-path "static/compiled_js/main.out"})
        (target :dir #{"../static/compiled_js"})))

(deftask production []
  (comp (cljs :optimizations    :advanced
              :source-map       true
              :compiler-options {:externs ["google_charts_externs.js"]})
        (target :dir #{"../static/compiled_js"})))
