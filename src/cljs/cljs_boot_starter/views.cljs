(ns cljs-boot-starter.views
  (:require [re-frame.core :as re-frame]
            [cljs-boot-starter.routes :as routes]))


;; home

(defn home-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div (str "Hello from " @name ". This is the Home Page.")
       [:div [:a {:href (routes/url-for :about)} "go to About Page"]]])))


;; about

(defn about-panel []
  (fn []
    [:div "This is the About Page."
     [:div [:a {:href (routes/url-for :home)} "go to Home Page"]]]))


;; main

(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :about-panel [] [about-panel])
(defmethod panels :default [] [:div])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [panels @active-panel])))
