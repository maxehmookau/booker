(ns booker.utils
  (:import java.text.SimpleDateFormat))

(defn current-date []
  (.format (java.text.SimpleDateFormat. "MM/dd/yyyy") 
    (java.util.Date.)))