(ns booking.models.booking)

(defn all-bookings []
  (db/get-all :bookings))

(defn create-booking [room_id from to user_id])

