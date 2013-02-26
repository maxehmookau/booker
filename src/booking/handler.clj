(ns booking.handler
  (:import [com.mchange.v2.c3p0.ComboPooledDataSource])
  (:use [compojure.core]
        [cheshire.core :as json]
        [ring.util.json-response])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))
            
;; CRUD for rooms

(defroutes room-routes
  (GET  "/" [] 
    (json-response {:foo "bar baz"})))
         
(defroutes app-routes
  (context "/rooms" [] 
    room-routes)
  (route/not-found {:status 404 :body "Not found"}))

(def app
  (-> (handler/api app-routes)))