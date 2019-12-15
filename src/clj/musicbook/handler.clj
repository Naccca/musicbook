(ns musicbook.handler
  (:require
    [musicbook.middleware :as middleware]
    [musicbook.layout :refer [error-page]]
    [musicbook.routes.home :refer [home-routes]]
    [musicbook.routes.artists :refer [artists-routes]]
    [reitit.ring :as ring]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.middleware.webjars :refer [wrap-webjars]]
    [musicbook.env :refer [defaults]]
    [mount.core :as mount]))

(mount/defstate init-app
  :start ((or (:init defaults) (fn [])))
  :stop  ((or (:stop defaults) (fn []))))

(defn all-routes []
  (into [] (concat "" {:middleware [middleware/wrap-csrf
                                    middleware/wrap-formats]}
                   (home-routes)
                   (artists-routes))))

(mount/defstate app-routes
  :start
  (ring/ring-handler
    (ring/router
      [(all-routes)])
    (ring/routes
      (ring/create-resource-handler
        {:path "/"})
      (wrap-content-type
        (wrap-webjars (constantly nil)))
      (ring/create-default-handler
        {:not-found
         (constantly (error-page {:status 404, :title "404 - Page not found"}))
         :method-not-allowed
         (constantly (error-page {:status 405, :title "405 - Not allowed"}))
         :not-acceptable
         (constantly (error-page {:status 406, :title "406 - Not acceptable"}))}))))

(defn app []
  (middleware/wrap-base #'app-routes))
