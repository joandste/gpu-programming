(ns gpu.core
  (:require [uncomplicate.clojurecl.core :refer :all] 
            [uncomplicate.clojurecl.info :refer :all]
            [clojure.math :refer [acos sin cos]]
            [clojure.string :refer [split]]
            [clojure.java.io :refer [reader]]))


(defn parse-file [file re]
  (map (fn [line] (map Double/parseDouble (split line re)))
     (next (line-seq 
      (reader file)))))

(defn arc-to-rad [data]
  (map (fn [coordinates] (map (fn [arc] (* arc 0.000291)) coordinates)) data))

(defn calculate [coord1 coord2]
  (let [[asc1 dec1] coord1
        [asc2 dec2] coord2]
   (acos (+ (* (sin dec1) (sin dec2)) (* (cos dec1) (cos dec2) (cos (- asc1 asc2)))))))


(defn -main []
  (println "hello world"))


(comment
  (def data (parse-file "data_100k_arcmin.dat" #"\t"))
  
  (def rand (parse-file "rand_100k_arcmin.dat" #" "))
  
  (first (arc-to-rad data))
  (first (arc-to-rad rand))
  
  (def dd (pmap calculate (arc-to-rad data) (arc-to-rad data)))
  (def dr (pmap calculate (arc-to-rad data) (arc-to-rad rand)))
  (def rr (pmap calculate (arc-to-rad rand) (arc-to-rad rand)))
  
  (take 100 dd)

  (first dd))