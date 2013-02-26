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

(defn create-rooms-table []
  (sql/with-connection (db-connection)
    (sql/create-table :rooms 
      [:id "varchar(256)" "primary key"]
      [:title "varchar(256)"])))

(defmacro with-conn [& body]
  `(sql/with-connection (db-connection)
     (do ~@body)))

(defn get-all [table]
  (with-conn
    (sql/with-query-results results
    ["select * from rooms"]
      (into [] results))))

