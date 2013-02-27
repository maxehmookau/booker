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
  
(defroutes app-routes
  (context "/rooms" [] 
    room-routes)
  (route/not-found (json-response {:status 404 :body "Not found"})))

(defn fake-request [routes uri method & params]
  (let [localhost "127.0.0.1"]
    (routes {:server-port 80
             :server-name localhost
             :remote-addr localhost
             :uri uri
             :scheme :http
             :headers (or params {})
             :request-method method})))
 
;; YOU CAN TEST ROUTES IN THE REPL
 
(def index-page (fake-request app "/" :get))

(def app
  (-> (handler/api app-routes)))
