(ns booking.models.booking)

(defn all-bookings []
  (db/get-all :bookings))

