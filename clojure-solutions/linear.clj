(defn valid-vectors? [& vectors]
  (and
    (not (empty? vectors))
    (every? vector? vectors)
    (every? (fn [v] (every? number? v)) vectors)
    (let [len (count (first vectors))] (every? #(== len (count %)) vectors))))

(defn valid-matrices? [& matrices]
  (every? #(and (vector? %)
                (not (empty? %))
                (apply valid-vectors? %))
          matrices))

(defn same-size? [& matrices]
  (and (every? #(== (count (first matrices)) (count %))
               (rest matrices))
       (every? #(== (count (first (first matrices))) (count %))
               (map first matrices))))

(defn compatible-matrices? [& matrices]
  (reduce (fn [prev curr]
            (let [c (count (first prev))
                  r (count curr)]
              (and prev (== c r) curr)))
          matrices))

(defn vector-op [op]
  (fn [& vectors]
    {:pre  [(apply valid-vectors? vectors)]
     :post [(valid-vectors? %)
            (== (count (first vectors)) (count %))]}
    (apply mapv op vectors)))

(def v+ (vector-op +))
(def v- (vector-op -))
(def v* (vector-op *))
(def vd (vector-op /))

(defn scalar [& vectors]
  {:pre  [(apply valid-vectors? vectors)]
   :post [(number? %)]}
  (reduce + (apply v* vectors)))

(def vect (fn [& vectors]
            {:pre  [(apply valid-vectors? vectors)]
             :post [(valid-vectors? %)
                    (== (count (first vectors)) (count %))]}
            (reduce #(let [[x1 y1 z1] %1
                           [x2 y2 z2] %2]
                       [(- (* y1 z2) (* z1 y2))
                        (- (* z1 x2) (* x1 z2))
                        (- (* x1 y2) (* y1 x2))])
                    vectors)))

(defn v*s [vector & scalars]
  {:pre  [(every? number? scalars)]
   :post [(valid-vectors? %)
          (== (count vector) (count %))]}
  (let [scalar (reduce * scalars)]
    (mapv (partial * scalar) vector)
    )
  )

(defn matrix-op [op]
  (fn [& matrices]
    {:pre  [(apply valid-matrices? matrices)
            (apply same-size? matrices)]
     :post [(valid-matrices? %)
            (same-size? % (first matrices))]}
    (apply mapv (fn [& cols]
                  (apply mapv op cols)) matrices)))

(def m+ (matrix-op +))
(def m- (matrix-op -))
(def m* (matrix-op *))
(def md (matrix-op /))

(defn transpose [matrix]
  {:pre  [(valid-matrices? matrix)]
   :post [(if (every? (fn [v] (= (count v) 0)) matrix) (== (count %) 0)
          (and (compatible-matrices? matrix %) (compatible-matrices? % matrix)))]}
  (apply mapv vector matrix))

(defn m*m [& matrices]
  {:pre  [(apply valid-matrices? matrices)
          (apply compatible-matrices? matrices)]
   :post [(valid-matrices? %)
          (== (count (first matrices)) (count %))
          (== (count (first (last matrices))) (count (first %)))]}
  (letfn [(m** [m1 m2]
            (let [m2t (transpose m2)]
              (mapv #(mapv (fn [col2] (reduce + (map * % col2))) m2t) m1)))]
    (reduce m** matrices)))

(defn m*v [matrix vector]
  {:pre  [(valid-matrices? matrix)
          (== (count (first matrix)) (count vector))]
   :post [(valid-vectors? %)
          (== (count matrix) (count %))]}
  (mapv #(apply + (mapv * % vector)) matrix))

(defn m*s [matrix & scalars]
  {:pre  [(valid-matrices? matrix)
          (every? number? scalars)]
   :post [(valid-matrices? %)
          (same-size? matrix %)]}
  (let [scalar (reduce * scalars)]
    (mapv #(v*s % scalar) matrix)))

(defn same-dim [t1 t2]
  (if (or (and (number? t1) (number? t2))
          (and
            (= (count t1) (count t2))
            (every? boolean (mapv same-dim t1 t2))
            )
          ) t1 nil
      )
  )

(defn tensor-op [op]
  (fn [& tensors]
    {:pre [(reduce same-dim tensors)]}
    (letfn [(func [& tensors]
              (if (number? (first tensors))
                (apply op tensors)
                (apply mapv func tensors)))]
      (apply func tensors))))

(def t+ (tensor-op +))
(def t- (tensor-op -))
(def t* (tensor-op *))
(def td (tensor-op /))