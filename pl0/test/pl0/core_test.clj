(ns pl0.core-test
  (:require [clojure.test :refer :all]
            [pl0.core :refer :all]))
			
;; Utiles

(defn assertEquals [a b]
	(is (= a b))
)

(defn assertTrue [a]
		(is (true? a))
)

(defn assertFalse [a]
		(is (false? a))
)

;; Tests unitarios


;################# a-mayusculas-salvo-strings #################
(deftest test1
	(testing "a-mayusculas-salvo-stringsEj1"
		(assertEquals (a-mayusculas-salvo-strings "  const Y = 2;")
				"  CONST Y = 2;"
		)
	)
)

(deftest test2
	(testing "a-mayusculas-salvo-stringsEj2"
		(assertEquals (a-mayusculas-salvo-strings "  writeln ('Se ingresa un valor, se muestra su doble.');")
				"  WRITELN ('Se ingresa un valor, se muestra su doble.');"
		)
	)
)

;################# palabra-reservada #################
(deftest test3
	(testing "palabra-reservada-Ej1"
			(assertTrue (palabra-reservada? 'CALL))
	)
)

(deftest test4
	(testing "palabra-reservada-Ej2"
			(assertTrue (palabra-reservada? "CALL"))
	)
)

(deftest test5
	(testing "palabra-reservada-Ej3"
		(assertFalse (palabra-reservada? "ASIGNAR"))	
	)
)

(deftest test6
	(testing "palabra-reservada-Ej4"
		(assertFalse (palabra-reservada? 'ASIGNAR))
	)
)

(deftest test7
	(testing "palabra-reservada-Ej5"
		(assertTrue (palabra-reservada? "BEGIN"))
	)
)

(deftest test8
	(testing "palabra-reservada-Ej6"
		(assertTrue (palabra-reservada? 'END))
	)
)


;################# identificador #################

(deftest test9
	(testing "identificador-Ej1"
			(assertFalse (identificador? '2))
	)
)

(deftest test11
	(testing "identificador-Ej2"
			(assertFalse (identificador? 2))
	)
)

(deftest test12
	(testing "identificador-Ej3"
			(assertTrue (identificador? 'V2))
	)
)

(deftest test13
	(testing "identificador-Ej4"
		(assertTrue (identificador? "V2"))
	)
)

(deftest test14
	(testing "identificador-Ej5"
		 (assertFalse (identificador? 'CALL))
	)
)

;################# cadena #################

(deftest test15
	(testing "cadenaEj2"
			(assertTrue (cadena? "'Hola'"))
	)
)

(deftest test16
	(testing "cadenaEj3"
			(assertFalse (cadena? "Hola"))
	)
)

(deftest test17
	(testing "cadenaEj4"
			(assertFalse (cadena? "'Hola"))
	)
)

(deftest test18
	(testing "cadenaEj5"
			(assertFalse (cadena? 'Hola))
	)
)

(deftest test19
	(testing "cadenaEj6"
			(assertFalse (cadena? "'Hola''"))
	)
)

(deftest test20
	(testing ""

	)
)

(deftest test21
	(testing ""

	)
)

(deftest test22
	(testing ""

	)
)

(deftest test23
	(testing ""

	)
)

(deftest test24
	(testing ""

	)
)

(deftest test25
	(testing ""

	)
)

(deftest test26
	(testing ""

	)
)

(deftest test27
	(testing ""

	)
)
