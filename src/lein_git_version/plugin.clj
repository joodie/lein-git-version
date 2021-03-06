(ns lein-git-version.plugin
  (:use
   clojure.pprint
   [leiningen.git-version :only [get-git-version]]))

(defn middleware
  [project]
  (let [code (str
              ";; Do not edit.  Generated by lein-git-version plugin.\n"
              "(ns " (:name project) ".version)\n"
              "(def version \"" (get-git-version) "\")\n")
        filename (str (first (:source-paths project)) "/"
                      (:name project) "/version.clj")]
    (-> project
        (update-in [:injections] concat `[(spit ~filename ~code)])
        (assoc :version (get-git-version)))))
