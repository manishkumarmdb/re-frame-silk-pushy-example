(ns cljs-boot-starter.client
  (:require [reagent.core :as reagent :refer [atom render]]
            [re-frame.core :as re-frame]
            [devtools.core :as devtools]
            [cljs-boot-starter.handlers]
            [cljs-boot-starter.subs]
            [cljs-boot-starter.routes :as routes]
            [cljs-boot-starter.views :as views]
            [cljs-boot-starter.config :as config]))

(enable-console-print!)

#_(defn hello []
    [:div
     "Hello world!"])

#_(defn init []
    (render [hello] (.getElementById js/document "my-app-area")))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")
    (devtools/install!)))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "my-app-area")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))

(init)
