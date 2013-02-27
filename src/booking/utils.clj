(ns booker.utils
  (:import java.text.SimpleDateFormat))

(defn uuid [] (str (java.util.UUID/randomUUID)))

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

;; YOU CAN TEST ROUTES IN THE REPL
;; (def index-page (fake-request app "/" :get))

(defn current-date []
  (.format (java.text.SimpleDateFormat. "MM/dd/yyyy") 
    (java.util.Date.)))
