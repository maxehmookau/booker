(ns booking.handler
  (:use [compojure.core]
        [booking.db :as db]
        [booking.middleware :only [wrap-request-logging]]
        [booking.models.room :as room]
        [cheshire.core :as json]
        [ring.util.json-response])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes room-routes
  (GET    "/rooms"     []           (json-response (room/all-rooms)))
  (POST   "/rooms"     [params]     (json-response params))
  (GET    "/rooms/:id" [id]         (json-response {:body id}))
  (PUT    "/rooms/:id" [id params]  (json-response params))
  (DELETE "/rooms/:id" [id]         (json-response {:id id})))

(def test-data 
  {:count 3 
   :rooms [{:id 1 :title "Huddle"} {:id 2 :title "Lab"} {:id 3 :title "Board"}]})
  
;; Main application routes 
(defroutes app-routes
  (GET "/"          [] (json-response {:status 200 :body "Roombooker API"}))
  (GET "/api/rooms" [] (json-response test-data))
  (GET "/config"    [] (json-response (db/heroku-db)))
  room-routes
  (route/not-found (json-response {:status 404 :body "Not found"})))

(def app
  (-> (handler/api app-routes)
      wrap-request-logging))


