(ns booking.handler
  (:import [com.mchange.v2.c3p0.ComboPooledDataSource])
  (:use [compojure.core]
        [cheshire.core :as json]
        [ring.util.json-response])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))
            
;; CRUD for rooms

(defn all-rooms []
  (json-response {:rooms "All rooms here"}))

(defn get-room [id]
  (json-response {:status 200}))

(defn create-room [room]
  (json-response {:status 200}))

(defn update-room [id room])

(defn delete-room [id])

(defroutes room-routes
  (GET  "/" [] (all-rooms))
  (POST "/" {body :body} (create-room body))
    (context "/:id" [id] (defroutes room-routes
      (GET    "/" [] (get-room id))
      (PUT    "/" {body :body} (update-room id body))
      (DELETE "/" [] (delete-room id)))))
  
(defroutes app-routes
  (context "/rooms" [] 
    room-routes)
  (route/not-found (json-response {:status 404 :body "Not found"})))

(def app
  (-> (handler/api app-routes)))