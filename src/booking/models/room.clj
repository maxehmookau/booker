(ns booking.models.room
  (refer-clojure :exclude [create get])
  (:use [booking.db :as db]))

(defn all
  "Returns all meeting rooms"
  []
  (db/get-all :rooms))

(defn create 
  "Create a new meeting room record"
  [room]
  (db/create-one :rooms room))

(defn get 
  "Find a room by id"
  [id]
  (db/get-one :rooms id))
  
  
