(ns booking.config
)

;; Heroku config testing

(def db
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :user (System/getenv "DB_USER")
   :password (System/getenv "DB_PASSWORD")
   :subname (System/getenv "DATABASE_URL")})


