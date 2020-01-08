(ns musicbook.routes.artists
  (:require
   [musicbook.layout :as layout]
   [musicbook.db.core :as db]
   [clojure.java.io :as io]
   [ring.util.response]
   [ring.util.http-response :as response]
   [struct.core :as st]
   [buddy.hashers :as hashers]))

(def create-artist-schema
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

(def update-artist-schema
  [[:name
    st/required
    st/string]

   [:bio
    st/required
    st/string]

   [:location
    st/required
    st/string]])

(def accept-membership-schema
  [[:band_id
    st/required
    st/string]])

(defn validate-artist [params schema]
  (first (st/validate params schema)))

(defn authorize? [session path-params]
  (= (get-in session [:identity :id]) (Integer/parseInt (:id path-params))))

(defn index-page [{:keys [flash] :as request}]
  (layout/render
   request
   "artists/index.html"
   {:artists (db/get-artists) :info (:info flash)}))

(defn search-page [{:keys [params flash] :as request}]
  (layout/render
    request
    "artists/index.html"
    {:artists (db/search-artists {:name-like (str "%" (:q params) "%")}) :info (:info flash)}))

(defn show-page [{:keys [path-params] :as request}]
  (layout/render
   request
   "artists/show.html"
   {:artist (db/get-artist path-params)
    :bands (db/get-bands-by-artist-id (assoc path-params :state_id 2))
    :invites (db/get-bands-by-artist-id (assoc path-params :state_id 1))}))

(defn new-page [{:keys [flash] :as request}]
  (layout/render
   request
   "artists/new.html"
   (select-keys flash [:username :name :bio :location :errors])))

(defn edit-page [{:keys [flash path-params session] :as request}]
  (if (authorize? session path-params)
    (layout/render
     request
     "artists/edit.html"
     (merge {:artist (db/get-artist path-params)} (select-keys flash [:errors])))
    (response/found "/")))


(defn create-artist! [{:keys [params]}]
  (if-let [errors (validate-artist params create-artist-schema)]
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

(defn update-artist! [{:keys [path-params params session]}]
  (if (authorize? session path-params)
    (if-let [errors (validate-artist params update-artist-schema)]
      (-> (response/found (str "/artists/edit/" (:id path-params)))
          (assoc :flash (assoc params :errors errors)))
      (do
        (db/update-artist!
         (assoc (merge params path-params) :updated_at (java.util.Date.)))
        (response/found (str "/artists/show/" (:id path-params)))))
    (response/found "/")))

(defn delete-artist! [{:keys [path-params session]}]
  (if (authorize? session path-params)
    (do
      (db/delete-artist! path-params)
      (assoc(response/found "/artists") :flash {:info "Artist deleted!"}))
    (response/found "/")))

(defn accept-membership! [{:keys [path-params params session]}]
  (let [artist (db/get-artist path-params)]
    (if (authorize? session path-params)
      (do
        (db/accept-membership! (assoc params :artist_id (:id artist)))
        (response/found (str "/bands/show/" (:band_id params))))
      (response/found "/"))))

(defn artists-routes []
  [["/artists" {:get index-page :post create-artist!}]
   ["/artists/search" {:get search-page}]
   ["/artists/show/:id" {:get show-page}]
   ["/artists/new" {:get new-page}]
   ["/artists/edit/:id" {:get edit-page}]
   ["/artists/update/:id" {:post update-artist!}]
   ["/artists/delete/:id" {:post delete-artist!}]
   ["/artists/accept-membership/:id" {:post accept-membership!}]])