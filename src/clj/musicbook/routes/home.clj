(ns musicbook.routes.home
  (:require
   [musicbook.layout :as layout]
   [musicbook.db.core :as db]
   [clojure.java.io :as io]
   [ring.util.response]
   [ring.util.http-response :as response]
   [struct.core :as st]))


(defn home-page [request]
  (layout/render request "home.html"))


(defn home-routes []
  [["/" {:get home-page}]])

