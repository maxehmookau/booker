(ns booking.middleware)

(defn- log [msg & vals]
  (let [line (apply format msg vals)]
    (locking System/out (println line))))

(defn wrap-request-logging [handler]
  (fn [{:keys [request-method uri] :as req}]
    (when (not (= "uri" "/favicon.ico"))
      (let [resp (handler req)]
        (log "PROCESSING %s %s" request-method uri)
        resp))))
