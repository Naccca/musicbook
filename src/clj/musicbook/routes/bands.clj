(ns musicbook.routes.bands
  (:require
   [musicbook.layout :as layout]
   [musicbook.db.core :as db]
   [clojure.java.io :as io]
   [ring.util.response]
   [ring.util.http-response :as response]
   [struct.core :as st]))

(def band-schema
  [[:name
    st/required
    st/string]

   [:bio
    st/required
    st/string]

   [:location
    st/required
    st/string]

   [:genres
    st/required
    st/string]])

(def create-membership-schema
  [[:artist_name
    st/required]])

(def delete-membership-schema
  [[:artist_id
    st/required]])

(defn validate-band [params]
  (first (st/validate params band-schema)))

(defn owner? [band session]
  (= (get-in session [:identity :id]) (:owner_id band)))

(defn index-page [{:keys [flash] :as request}]
  (layout/render
   request
   "bands/index.html"
   {:bands (db/get-bands) :info (:info flash)}))

(defn search-page [{:keys [params flash] :as request}]
  (layout/render
    request
    "bands/index.html"
    {:bands (db/search-bands {:name-like (str "%" (:q params) "%")}) :info (:info flash)}))

(defn show-page [{:keys [path-params flash] :as request}]
  (let [band (db/get-band path-params)]
    (layout/render
     request
     "bands/show.html"
     {:band band
      :owner (db/get-artist {:id (:owner_id band)})
      :artists (db/get-artists-by-band-id (assoc path-params :state_id 2))
      :invites (db/get-artists-by-band-id (assoc path-params :state_id 1))
      :info (:info flash)})))

(defn new-page [{:keys [flash] :as request}]
  (layout/render
   request
   "bands/new.html"
   (select-keys flash [:name :bio :location :genres :errors])))

(defn edit-page [{:keys [flash path-params session] :as request}]
  (let [band (db/get-band path-params)]
    (if (owner? band session)
      (layout/render
       request
       "bands/edit.html"
       (merge {:band band} (select-keys flash [:errors])))
      (response/found "/"))))

(defn create-band! [{:keys [params session]}]
  (if-let [errors (validate-band params)]
    (-> (response/found "/bands/new")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/create-band!
       (assoc params 
              :owner_id (get-in session [:identity :id])
              :created_at (java.util.Date.)
              :updated_at (java.util.Date.)))
      (response/found "/bands"))))

(defn update-band! [{:keys [path-params params session]}]
  (let [band (db/get-band path-params)]
    (if (owner? band session)
      (if-let [errors (validate-band params)]
        (-> (response/found (str "/bands/edit/" (:id band)))
            (assoc :flash (assoc params :errors errors)))
        (do
          (db/update-band!
           (assoc (merge params path-params) :updated_at (java.util.Date.)))
          (response/found (str "/bands/show/" (:id band)))))
      (response/found "/"))))

(defn delete-band! [{:keys [path-params session]}]
  (let [band (db/get-band path-params)]
    (if (owner? band session)
      (do
        (db/delete-band! path-params)
        (assoc(response/found "/bands") :flash {:info "band deleted!"}))
      (response/found "/"))))

(defn create-membership! [{:keys [path-params params session]}]
  (let [band (db/get-band path-params)]
    (if (owner? band session)
      (if-let [artist (db/get-artist-by-name {:name (:artist_name params)})]
        (if (and 
              (st/valid? params create-membership-schema)
              (not (db/membership? {:artist_id (:id artist) :band_id (:id band)})))
          (do
            (db/create-membership! 
              {:artist_id (:id artist) :band_id (:id band) :created_at (java.util.Date.) :updated_at (java.util.Date.)})
            (response/found (str "/bands/show/" (:id band))))
          (assoc(response/found (str "/bands/show/" (:id band))) :flash {:info "Something went wrong!"}))
        (assoc(response/found (str "/bands/show/" (:id band))) :flash {:info "Artist not found!"}))
      (response/found "/"))))

(defn accept-membership! [{:keys [path-params params session]}]
  (let [band (db/get-band path-params)]))

(defn delete-membership! [{:keys [path-params params session]}]
  (let [band (db/get-band path-params)]
    (if (owner? band session)
      (if (st/valid? params delete-membership-schema)
        (do 
          (db/delete-membership!
            (assoc params :band_id (:id band)))
          (assoc(response/found (str "/bands/show/" (:id band))) :flash {:info "Artist removed."}))
        (assoc(response/found (str "/bands/show/" (:id band))) :flash {:info "Something went wrong!"}))
      (response/found "/"))))

  

(defn bands-routes []
  [["/bands" {:get index-page :post create-band!}]
   ["/bands/search" {:get search-page}]
   ["/bands/show/:id" {:get show-page}]
   ["/bands/new" {:get new-page}]
   ["/bands/edit/:id" {:get edit-page}]
   ["/bands/update/:id" {:post update-band!}]
   ["/bands/delete/:id" {:post delete-band!}]
   ["/bands/create-membership/:id" {:post create-membership!}]
   ["/bands/delete-membership/:id" {:post delete-membership!}]])