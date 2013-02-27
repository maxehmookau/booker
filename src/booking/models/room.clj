(ns booking.models.room
  (:use [booking.db :as db]))

(defn all-rooms
  "Returns all meeting rooms"
  []
  (db/get-all :rooms))

(defn create-room
  "Create a new meeting room record"
  [room]
  (db/create-one :rooms room))

(defn get-room
  "Find a room by id"
  [id]
  (db/get-one :rooms id))
  
  
