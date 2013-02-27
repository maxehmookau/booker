(ns booking.handler
  (:use [compojure.core]
        [booking.db :as sql]
        [booking.models.room :as room]
        [cheshire.core :as json]
        [ring.util.json-response])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))
            
;; CRUD for rooms

(defroutes room-routes
  (GET  "/" []   (json-response (room/all-rooms)))
  (POST "/" {body :body} {:body body}
    (context "/:id" [id] (defroutes room-routes
      (GET    "/" [] ""
      (PUT    "/" {body :body} "")
      (DELETE "/" [] ""))))))
  
;; Main application routes 
(defroutes app-routes
  (context "/rooms" [] room-routes)
  (route/not-found (json-response {:status 404 :body "Not found"})))

(def app
  (-> (handler/api app-routes)))
