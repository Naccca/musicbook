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
    st/string]])

(defn validate-band [params]
  (first (st/validate params band-schema)))

(defn index-page [{:keys [flash] :as request}]
  (layout/render
   request
   "bands/index.html"
   {:bands (db/get-bands) :info (:info flash)}))

(defn show-page [{:keys [path-params] :as request}]
  (layout/render
   request
   "bands/show.html"
   {:band (db/get-band path-params)}))

(defn new-page [{:keys [flash] :as request}]
  (layout/render
   request
   "bands/new.html"
   (select-keys flash [:name :bio :location :errors])))

(defn edit-page [{:keys [flash path-params] :as request}]
  (layout/render
   request
   "bands/edit.html"
   (merge {:band (db/get-band path-params)} (select-keys flash [:errors]))))


(defn create-band! [{:keys [params]}]
  (if-let [errors (validate-band params)]
    (-> (response/found "/bands/new")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/create-band!
       (assoc params :created_at (java.util.Date.) :updated_at (java.util.Date.)))
      (response/found "/bands"))))

(defn update-band! [{:keys [path-params params]}]
  (if-let [errors (validate-band params)]
    (-> (response/found (str "/bands/edit/" (:id path-params)))
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/update-band!
       (assoc (merge params path-params) :updated_at (java.util.Date.)))
      (response/found (str "/bands/show/" (:id path-params))))))

(defn delete-band! [{:keys [path-params]}]
  (do
    (db/delete-band! path-params)
    (assoc(response/found "/bands") :flash {:info "band deleted!"})))

(defn bands-routes []
  [["/bands" {:get index-page :post create-band!}]
   ["/bands/show/:id" {:get show-page}]
   ["/bands/new" {:get new-page}]
   ["/bands/edit/:id" {:get edit-page}]
   ["/bands/update/:id" {:post update-band!}]
   ["/bands/delete/:id" {:post delete-band!}]])