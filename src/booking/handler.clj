(ns booking.handler
  (:use [compojure.core]
        [booking.db :as db]
        [booking.models.room :as room]
        [cheshire.core :as json]
        [ring.util.json-response])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))
            
;; CRUD for rooms
(defroutes room-routes
  (GET  "/" []   (json-response (room/all-rooms)))
  (POST "/" {body :body} {:body body}
    (context "/:id" [id] 
      (defroutes room-routes
        (GET    "/" [] (json-response (room/get-room id))
        (PUT    "/" {body :body} "")
        (DELETE "/" [] ""))))))

(def prod-db (db/heroku-db))

(def test-data 
  {:count 3 
   :rooms [{:id 1 :title "Huddle"} {:id 2 :title "Lab"} {:id 3 :title "Board"}]})
  
;; Main application routes 
(defroutes app-routes
  (GET "/api/rooms" [] (json-response test-data))
  (GET "/config" [] (json-response (db/heroku-db)))
  (context "/rooms" [] room-routes)
  (route/not-found (json-response {:status 404 :body "Not found"})))

(def app
  (-> (handler/api app-routes)))
