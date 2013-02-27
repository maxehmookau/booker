(ns booking.setup
  (:use [booking.db :as database]))

(defn -main []
  (print "Migrating the database\n...")
  (database/create-rooms-table))
  