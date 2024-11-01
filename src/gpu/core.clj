(ns gpu.core)

(defn parse-file [file re]
  (map (fn [line] (map Double/parseDouble (clojure.string/split line re)))
     (next (line-seq 
      (clojure.java.io/reader file)))))

(defn arc-to-rad [data]
  (map (fn [coordinates] (map (fn [arc] (* arc 0.000291)) coordinates)) data))

(def data (parse-file "data_100k_arcmin.dat" #"\t"))

(def rand (parse-file "rand_100k_arcmin.dat" #" "))

(arc-to-rad data)
(arc-to-rad rand)

(defn -main []
  (print "hello world"))