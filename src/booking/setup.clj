(ns booking.setup
  (:use [booking.db :as database]))
  
(defn create-db []
  (database/create-rooms-table)) 

(defn task-runner [args]
  (let [f (resolve (symbol (first args)))]
    (apply f (rest args))))

(defn -main [& args]
  (print "Migrating the database\n")
  (try
    (create-db)
  (catch Exception e
    (println (.getNextException e)))))
  
