(ns musicbook.routes.artists
  (:require
   [musicbook.layout :as layout]
   [musicbook.db.core :as db]
   [clojure.java.io :as io]
   [ring.util.response]
   [ring.util.http-response :as response]
   [struct.core :as st]
   [buddy.hashers :as hashers]))

(def artist-schema
  [[:name
    st/required
    st/string]

   [:bio
    st/required
    st/string]

   [:location
    st/required
    st/string]

   [:username
    st/required
    st/string]

   [:password
    st/required
    st/string]])

(defn validate-artist [params]
  (first (st/validate params artist-schema)))

(defn index-page [{:keys [flash] :as request}]
  (layout/render
   request
   "artists/index.html"
   {:artists (db/get-artists) :info (:info flash)}))

(defn show-page [{:keys [path-params] :as request}]
  (layout/render
   request
   "artists/show.html"
   {:artist (db/get-artist path-params)}))

(defn new-page [{:keys [flash] :as request}]
  (layout/render
   request
   "artists/new.html"
   (select-keys flash [:username :name :bio :location :errors])))

(defn edit-page [{:keys [flash path-params] :as request}]
  (layout/render
   request
   "artists/edit.html"
   (merge {:artist (db/get-artist path-params)} (select-keys flash [:errors]))))


(defn create-artist! [{:keys [params]}]
  (if-let [errors (validate-artist params)]
    (-> (response/found "/artists/new")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/create-artist!
       (assoc
        (dissoc params :password)
        :password_hash (hashers/encrypt (:password params))
        :created_at (java.util.Date.)
        :updated_at (java.util.Date.)))
      (response/found "/artists"))))

(defn update-artist! [{:keys [path-params params]}]
  (if-let [errors (validate-artist params)]
    (-> (response/found (str "/artists/edit/" (:id path-params)))
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/update-artist!
       (assoc (merge params path-params) :updated_at (java.util.Date.)))
      (response/found (str "/artists/show/" (:id path-params))))))

(defn delete-artist! [{:keys [path-params]}]
  (do
    (db/delete-artist! path-params)
    (assoc(response/found "/artists") :flash {:info "Artist deleted!"})))

(defn artists-routes []
  [["/artists" {:get index-page :post create-artist!}]
   ["/artists/show/:id" {:get show-page}]
   ["/artists/new" {:get new-page}]
   ["/artists/edit/:id" {:get edit-page}]
   ["/artists/update/:id" {:post update-artist!}]
   ["/artists/delete/:id" {:post delete-artist!}]])