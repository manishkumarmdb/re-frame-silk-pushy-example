(ns cljs-boot-starter.routes
  (:require [clojure.set :refer [rename-keys]]
            [domkm.silk :as silk]
            [pushy.core :as pushy]
            [re-frame.core :as re-frame]))

;; first step is to define the routes we want.
(def routes (silk/routes [[:home [[]]]
                          [:about [["about"]]]]))


;;parse-url function uses bidi/match-route to turn a URL into a ds
(defn- parse-url [url]
  (silk/arrive routes url))

(defn- sanitize-silk-keywords [matched-route]
  (rename-keys matched-route {:domkm.silk/name    :name
                              :domkm.silk/pattern :pattern
                              :domkm.silk/routes  :routes
                              :domkm.silk/url     :url}))

;;dispatch-route is called with that structure:
(defn- dispatch-route [matched-route]
  (let [matched-route (sanitize-silk-keywords matched-route)
        panel-name (keyword (str (name (:name matched-route)) "-panel"))]
    (re-frame/dispatch [:set-active-panel panel-name])))


;; The app-routes function that used to define functions is replaced by one that sets up pushy:
(defn app-routes []
  (pushy/start! (pushy/pushy dispatch-route parse-url)))

(def url-for (partial silk/depart routes))
