(ns app.main
  (:gen-class) ; for -main method in uberjar
  (:require [io.pedestal.http :as server]
            [io.pedestal.http.route :as route]
            [app.service.abcihost :as abcihost] ;; abci host server
            [app.service.query :as query] ;; ledger query server
            [taoensso.timbre :as log]
            [taoensso.timbre.appenders.core :as appenders]
            [clojure.core.async :as async]
            [clojure.tools.cli :refer [parse-opts]]
            [cmd.abc :as abc]))

(log/set-config! {:level :debug
                  :ns-whitelist  ["app.*"]
                  :appenders {:println (appenders/println-appender {:stream :auto})}})

;; This is an adapted service map, that can be started and stopped
;; From the REPL you can call server/start and server/stop on this service
(defonce runnable-service-abcihost (server/create-server abcihost/service))
;; (defonce runnable-service-query (async/thread (server/create-server query/service)))
(defonce runnable-service-query (server/create-server query/service))

(defn run-dev
  "The entry-point for 'lein run-dev'"
  [& args]
  (println "\nCreating your [DEV] server...")
  (-> abcihost/service ;; start with production configuration
      (merge {:env :dev
              ;; do not block thread that starts web server
              ::server/join? false
              ;; Routes can be a function that resolve routes,
              ;;  we can use this to set the routes to be reloadable
              ::server/routes #(route/expand-routes (deref #'abcihost/grpc-routes)) ;; -- PROTOC-GEN-CLOJURE -- update route
              ;; all origins are allowed in dev mode
              ::server/allowed-origins {:creds true :allowed-origins (constantly true)}
              ;; Content Security Policy (CSP) is mostly turned off in dev mode
              ::server/secure-headers {:content-security-policy-settings {:object-src "none"}}})
      ;; Wire up interceptor chains
      server/default-interceptors
      server/dev-interceptors
      server/create-server
      server/start))

(defn -main
  "The entry-point for 'lein run'"
  [& args]
  (println "\nCreating your server...")
  (parse-opts args abc/abc-options)
  (async/go (server/start runnable-service-query))
  (server/start runnable-service-abcihost))

;; If you package the service up as a WAR,
;; some form of the following function sections is required (for io.pedestal.servlet.ClojureVarServlet).

;;(defonce servlet  (atom nil))
;;
;;(defn servlet-init
;;  [_ config]
;;  ;; Initialize your app here.
;;  (reset! servlet  (server/servlet-init service/service nil)))
;;
;;(defn servlet-service
;;  [_ request response]
;;  (server/servlet-service @servlet request response))
;;
;;(defn servlet-destroy
;;  [_]
;;  (server/servlet-destroy @servlet)
;;  (reset! servlet nil))

