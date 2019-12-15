(ns musicbook.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[musicbook started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[musicbook has shut down successfully]=-"))
   :middleware identity})
