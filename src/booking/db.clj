(ns booking.db
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (:require [clojure.java.jdbc :as sql]))

;; Database access

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
  `(sql/with-connection (db-connection)
     (do ~@body)))

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

