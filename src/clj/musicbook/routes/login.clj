(ns musicbook.routes.login
  (:require
   [musicbook.layout :as layout]
   [musicbook.db.core :as db]
   [clojure.java.io :as io]
   [ring.util.response]
   [ring.util.http-response :as response]
   [struct.core :as st]
   [buddy.hashers :as hashers]))

(def login-schema
  [[:username
    st/required
    st/string]

   [:password
    st/required
    st/string]])

(defn login-page [{:keys [flash] :as request}]
  (layout/render
   request
   "login/login.html"
   (select-keys flash [:username])))

(defn lookup-artist [username password]
  (if-let [artist (db/get-artist-by-username {:username username})]
    (if (hashers/check password (:password_hash artist))
      (dissoc artist :password_hash))))

(defn login [{:keys [params session]}]
  (if-let [artist (lookup-artist (:username params) (:password params))]
    (-> (response/found (str "/artists/show/" (:id artist)))
        (assoc :session (assoc session :identity artist)))
    (response/found "/login")))

(defn logout [{:keys [session]}]
  (-> (response/found "/")
      (assoc :session (dissoc session :identity))))

(defn login-routes []
  [["/login" {:get login-page :post login}]
   ["/logout" {:post logout}]])