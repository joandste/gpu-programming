(ns gpu.core
  (:require [uncomplicate.clojurecl.core :refer :all] 
            [uncomplicate.clojurecl.info :refer :all]
            [clojure.math :refer [acos sin cos PI]]
            [clojure.string :refer [split]]
            [clojure.java.io :refer [reader]]))


(defn parse-file-data [file re]
  (map (fn [line] (map Double/parseDouble (split line re)))
     (next (line-seq 
      (reader file)))))

(defn parse-file-count [file] 
       (first (line-seq
              (reader file))))

(defn arc-to-rad [data]
  (let [conversion (/ (* (/ 1 60) PI) 180)]
   (map (fn [coordinates] (map (fn [arc] (* arc conversion)) coordinates)) data)))

(defn calculate [coord1 coord2]
  (let [[asc1 dec1] coord1
        [asc2 dec2] coord2]
   (acos (+ (* (sin dec1) (sin dec2))
            (* (cos dec1) (cos dec2) (cos (- asc1 asc2)))))))

(defn calculate-pairs [list1 list2]
  (mapcat (fn [x]
            (map (fn [y] (calculate x y)) list2))
          list1))


(defn -main []
  (println "hello world"))


(comment
  (def data (arc-to-rad (parse-file-data "data_100k_arcmin.dat" #"\t")))
  
  (def rand (arc-to-rad (parse-file-data "rand_100k_arcmin.dat" #" ")))
  
  (take 10 data)
  (first data)

  (parse-file-count "data_100k_arcmin.dat")
  (parse-file-count "rand_100k_arcmin.dat")
  
  (def dd (calculate-pairs data data))
  (def dr (calculate-pairs data rand))
  (def rr (calculate-pairs rand rand))
  
  (take 100 dd)
  (take 100 rr) 
  (first dd))