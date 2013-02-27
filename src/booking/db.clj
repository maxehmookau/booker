(ns booking.db
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (:import java.net.URI)
  (:require [clojure.java.jdbc :as sql]))

;; Database access

(defn heroku-db
  "Generate the db map according to Heroku environment when available."
  []
  (when (System/getenv "DATABASE_URL")
    (let [url (URI. (System/getenv "DATABASE_URL"))
          host (.getHost url)
          port (if (pos? (.getPort url)) (.getPort url) 5432)
          path (.getPath url)]
      (merge
       {:subname (str "//" host ":" port path)}
       (when-let [user-info (.getUserInfo url)]
         {:user (first (clojure.string/split user-info #":"))
          :password (second (clojure.string/split user-info #":"))})))))

(def db-config
    {:classname "org.postgresql.Driver"
     :subprotocol "postgresql"
     :subname "//localhost/booking"
     :username "postgres"
     :password "postgres"})
   
(defn pool
  [config]
    (let [cpds (doto (ComboPooledDataSource.)
    (.setDriverClass (:classname config))
    (.setJdbcUrl (str "jdbc:" (:subprotocol config) ":" (:subname config)))
    (.setUser (:user config))
    (.setPassword (:password config))
    (.setMaxPoolSize 6)
    (.setMinPoolSize 1)
    (.setInitialPoolSize 1))]
  {:datasource cpds}))

(def pooled-db 
  (delay (pool db-config)))

(defn db-connection [] @pooled-db)

(defmacro with-conn [& body]
  `(sql/with-connection (heroku-db)
     (sql/transaction
       (do ~@body))))

(defmacro with-sql-results 
  "Run an abitrary sql query and return the results.
   Query is a vector of the form [query args]"
  [query]
  `(with-conn
    (sql/with-query-results results#
      ~query
      (into [] results#))))

(comment
  (with-sql-results ["select title from rooms as r where r.id = ?" 1]))

(defn drop-table [table]
  (with-conn
    (sql/drop-table table)))

(defn create-rooms-table []
  (with-conn
    (sql/create-table :rooms 
      [:id "SERIAL"] ;; postgres specific auto increment
      [:title "varchar(256)"])))

(defn create-bookings-table []
  (with-conn
    (sql/create-table :bookings
      [:id "SERIAL"]
      [:room_id "integer"]
      [:start_date ""]
      [:end_date ""])))

(defn get-all [table]
  (with-conn
    (sql/with-query-results results
    ["select * from rooms"]
      (into [] results))))

;; Create a single entry 

(defn create-one [table item]
  (with-conn
    (sql/insert-record table item)))

(comment
  (create-one :rooms {:title "Huddle"}))

(defn get-one [table id]
  (with-conn
    (sql/with-query-results results
      ["select * from rooms where id = ?" id]
      (if (empty? results) {:status 404}
          (first results)))))

(comment
  (get-one :rooms 1))

(defn delete-one [table id]
  (with-conn
    (sql/delete-rows table ["id = ?" id])))

(defn update-one [table id record]
  (with-conn
    (sql/update-values table ["id = ?" id] record)))

