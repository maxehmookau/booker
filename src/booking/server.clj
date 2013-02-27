(ns booking.server
  (:use [ring.adapter.jetty :only [run-jetty]])
  (:require [booking.handler :as handler]))

(defn -main [& args]
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty handler/app {:port port})))
