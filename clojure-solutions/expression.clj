(defn operation [op]
  (fn [& args]
    (fn [vars] (apply op (map #(% vars) args)))))

(defn unchecked-divide [& args]
  (cond
    (empty? args) 1.0
    (== (count args) 1) (/ 1.0 (first args))
    :else
    (/ (double (first args)) (reduce * (rest args)))))
(defn subtract-op [& args]
  (if (empty? args) 0 (apply - args)))
(defn exp-sum [& args]
  (apply + (map #(Math/exp %) args)))
(defn log-exp-sum [& args]
  (Math/log (apply exp-sum args)))

(defn constant [val]
  (constantly val))
(defn variable [name]
  (fn [vars] (get vars name)))
(defn negate [arg]
  (fn [vars] (- (arg vars))))

(def sumexp (operation exp-sum))
(def lse (operation log-exp-sum))
(def add (operation +))
(def multiply (operation *))
(def subtract
  (operation subtract-op))
(def divide
  (operation unchecked-divide))

(defn variable? [token]
  (#{'x 'y 'z} token))
(def operators {'+      add
                '-      subtract
                '*      multiply
                '/      divide
                'negate negate
                'sumexp sumexp
                'lse    lse})
(defn parseToken [variable constant operators token]
  (cond
    (variable? token) (variable (str token))
    (number? token) (constant token)
    :else
    (apply (get operators (first token))
           (mapv #(parseToken variable constant operators %) (rest token)))))

(defn parseFunction [expr]
  (parseToken variable constant operators (read-string expr)))




(declare Add, Subtract, Multiply, Divide, Constant, Negate, ONE, ZERO, TWO)
(definterface Operation
  (evaluate [vars])
  (diff [variable])
  (toString [_]))

(defn evaluate [value vars]
  (.evaluate value vars))
(defn diff [expr var]
  (.diff expr var))
(defn toString [value]
  (.toString value))
(deftype AbstractOperation [op string-op expr diffImpl]
  Operation
  (evaluate [_ vars] (apply op (map #(evaluate % vars) expr)))
  (diff [_ var] (diffImpl expr var))
  (toString [_] (str "(" string-op " " (clojure.string/join " " (map #(toString %) expr)) ")")))
(defn create-operation [op string-op diffImpl]
  (fn [& args]
    (->AbstractOperation op string-op args diffImpl)))


(defn differentiate [exp var]
  (map #(diff % var) exp))

(deftype VariableExpr [name]
  Operation
  (evaluate [_ vars] (get vars name))
  (diff [_ var] (if (= var name) ONE ZERO))
  (toString [_] name))
(deftype ConstantExpr [value]
  Operation
  (evaluate [_ _] value)
  (diff [_ _] ZERO)
  (toString [_] (str value)))
(deftype NegateExpr [operand]
  Operation
  (evaluate [_ vars] (- (evaluate operand vars)))
  (diff [_ var] (Negate (diff operand var)))
  (toString [_] (str "(negate " (toString operand) ")")))


(defn Constant [value]
  (->ConstantExpr value))
(defn Variable [value]
  (->VariableExpr value))
(defn Negate [value]
  (->NegateExpr value))

(def ZERO (Constant 0.0))
(def ONE (Constant 1.0))
(def TWO (Constant 2.0))

(defn meansq-op [& args]
  (/ (apply + (map #(* % %) args)) (count args)))
(defn rms-op [& args]
  (Math/sqrt (apply meansq-op args)))

(defn meansqDiff [expr var]
  (Divide (apply Add (map #(diff (Multiply % %) var) expr))
          (Constant (count expr))))
(defn multiplyDiff [expr var]
  (if (= 1 (count expr))
    (diff (first expr) var)
    (Add
      (Multiply
        (diff (first expr) var)
        (apply Multiply (rest expr)))
      (Multiply
        (diff (apply Multiply (rest expr)) var)
        (first expr)))))
(defn divideDiff [expr var]
  (let [x (first expr) y (rest expr)]
    (if (= 1 (count expr))
      (Divide
        (Subtract
          (Multiply
            x (diff ONE var))
          (Multiply
            ONE (diff x var)))
        (Multiply x x))
      (Divide
        (Subtract
          (Multiply
            (apply Multiply y)
            (diff x var))
          (Multiply
            (diff (apply Multiply y) var) x))
        (Multiply
          (apply Multiply y)
          (apply Multiply y))))))

(def Add
  (create-operation + "+"
                    (fn [expr var]
                      (apply Add (differentiate expr var)))))
(def Subtract
  (create-operation subtract-op "-"
                    (fn [expr var]
                      (apply Subtract (differentiate expr var)))))

(def Multiply
  (create-operation * "*" multiplyDiff))
(def Divide
  (create-operation unchecked-divide "/" divideDiff))

(def Meansq
  (create-operation meansq-op "meansq" meansqDiff))
(def RMS
  (create-operation rms-op "rms"
                    (fn [expr var]
                      (Divide (diff (apply Meansq expr) var)
                              (Multiply TWO (apply RMS expr))))))
(def object-ops {'+      Add
                 '-      Subtract
                 '*      Multiply
                 '/      Divide
                 'negate Negate
                 'meansq Meansq
                 'rms    RMS
                 })
(defn parseObject [expr]
  (parseToken Variable Constant object-ops (read-string expr)))