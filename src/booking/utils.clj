(ns booker.utils
  (:import java.text.SimpleDateFormat))

(defn fake-request 
  "Makes fake requests in development mode"
  [routes uri method & params]
  (let [localhost "127.0.0.1"]
    (routes {:server-port 80
             :server-name localhost
             :remote-addr localhost
             :uri uri
             :scheme :http
             :headers (or params {})
             :request-method method})))

(defn current-date []
  (.format (java.text.SimpleDateFormat. "MM/dd/yyyy") 
    (java.util.Date.)))
