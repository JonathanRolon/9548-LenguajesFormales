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

;################# palabra-reservada? #################
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


;################# identificador? #################

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

;################# cadena? #################

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

;################# ya-declarado-localmente? #################

(deftest test20
	(testing "ya-declarado-localmente?Ej1"
			(assertTrue (ya-declarado-localmente? 'Y '[[0] [[X VAR 0] [Y VAR 1]]]))
	)
)

(deftest test21
	(testing "ya-declarado-localmente?Ej2"
			(assertFalse (ya-declarado-localmente? 'Z '[[0] [[X VAR 0] [Y VAR 1]]]))
	)
)

(deftest test22
	(testing "ya-declarado-localmente?Ej3"
		 (assertFalse (ya-declarado-localmente? 'Y '[[0 3 5] [[X VAR 0] [Y VAR 1] [INICIAR PROCEDURE 1] [Y CONST 2] [ASIGNAR PROCEDURE 2]]]))
	)
)

(deftest test23
	(testing "ya-declarado-localmente?Ej4"
		(assertTrue (ya-declarado-localmente? 'Y '[[0 3 5] [[X VAR 0] [Y VAR 1] [INICIAR PROCEDURE 1] [Y CONST 2] [ASIGNAR PROCEDURE 2] [Y CONST 6]]]))
	)
)

;################# cargar-var-en-tabla #################
(deftest test24
	(testing "cargar-var-en-tablaEj1"
			(assertEquals 
						(cargar-var-en-tabla '[nil () [VAR X] :error [[0] []] 0 [[JMP ?]]])
						[nil () ['VAR 'X] :error [[0] []] 0 [['JMP '?]]]
			)
	)
)

(deftest test25
	(testing "cargar-var-en-tablaEj2"
			(assertEquals
						(cargar-var-en-tabla '[nil () [VAR X] :sin-errores [[0] []] 0 [[JMP ?]]])
						[nil () ['VAR 'X] :sin-errores [[0] [['X 'VAR 0]]] 1 [['JMP '?]]]
			)
	)
)

(deftest test26
	(testing "cargar-var-en-tablaEj3"
				(assertEquals
							(cargar-var-en-tabla '[nil () [VAR X , Y] :sin-errores [[0] [[X VAR 0]]] 1 [[JMP ?]]])
							[nil () ['VAR 'X 'Y] :sin-errores [[0] [['X 'VAR 0] ['Y 'VAR 1]]] 2 [['JMP '?]]]
				)
	)
)

;################# inicializar-contexto-local #################

(deftest test27
	(testing "inicializar-contexto-localEj1"
			(assertEquals
						(inicializar-contexto-local '[nil () [] :error [[0] [[X VAR 0] [Y VAR 1] [INI PROCEDURE 1]]] 2 [[JMP ?]]])
						[nil () [] :error [[0] [['X 'VAR 0] ['Y 'VAR 1] ['INI 'PROCEDURE 1]]] 2 [['JMP '?]]]
			)
	)
)

(deftest test28
	(testing "inicializar-contexto-localEj3"
			(assertEquals
					(inicializar-contexto-local '[nil () [] :sin-errores [[0] [[X VAR 0] [Y VAR 1] [INI PROCEDURE 1]]] 2 [[JMP ?]]])
					[nil () [] :sin-errores [[0 3] [['X 'VAR 0] ['Y 'VAR 1] ['INI 'PROCEDURE 1]]] 2 [['JMP '?]]]
			)
	)
)

;################# declaracion-var #################

(deftest test29
(testing "declaracion-varEj1"
			(assertEquals
					(declaracion-var ['VAR (list 'X (symbol ",") 'Y (symbol ";") 'BEGIN 'X (symbol ":=") 7 (symbol ";") 'Y (symbol ":=") 12 (symbol ";") 'END (symbol ".")) [] :error [[0] []] 0 '[[JMP ?]]])
						['VAR  (list 'X (symbol ",") 'Y (symbol ";") 'BEGIN 'X (symbol ":=") 7 (symbol ";") 'Y (symbol ":=") 12 (symbol ";") 'END '.) [] :error [[0] []] 0 [['JMP '?]]]
			)
	)
)

(deftest test30
	(testing "declaracion-varEj2"
			(assertEquals
					(declaracion-var ['VAR (list 'X (symbol ",") 'Y (symbol ";") 'BEGIN 'X (symbol ":=") 7 (symbol ";") 'Y (symbol ":=") 12 (symbol ";") 'END (symbol ".")) [] :sin-errores [[0] []] 0 '[[JMP ?]]])
					['BEGIN (list 'X (symbol ":=") 7 (symbol ";") 'Y (symbol ":=") 12 (symbol ";") 'END (symbol ".")) ['VAR 'X (symbol ",") 'Y (symbol ";")] :sin-errores [[0] [['X 'VAR 0] ['Y 'VAR 1]]] 2 [['JMP '?]]]
			)
	)
)

(deftest test31
	(testing "declaracion-varEj3"
			(assertEquals
					(declaracion-var ['VAR (list 'X (symbol ",") 'Y (symbol ",") 'Z (symbol ";") 'BEGIN 'X (symbol ":=") 7 (symbol ";") 'Y (symbol ":=") 12 (symbol ";") 'Z (symbol ":=") 20 (symbol ";") 'END (symbol ".")) [] :sin-errores [[0] []] 0 '[[JMP ?]]])
					['BEGIN (list 'X (symbol ":=") 7 (symbol ";") 'Y (symbol ":=") 12 (symbol ";") 'Z (symbol ":=") 20 (symbol ";") 'END (symbol ".")) ['VAR 'X (symbol ",") 'Y (symbol ",") 'Z (symbol ";")] :sin-errores [[0] [['X 'VAR 0] ['Y 'VAR 1] ['Z 'VAR 2]]] 3 [['JMP '?]]]
			)
	)
)

;################# procesar-signo-unario #################

(deftest test32
	(testing "procesar-signo-unarioEj1"
			(assertEquals
						(procesar-signo-unario ['+ (list 7 (symbol ";") 'Y ':= '- 12 (symbol ";") 'END (symbol ".")) ['VAR 'X (symbol ",") 'Y (symbol ";") 'BEGIN 'X (symbol ":=")] :error '[[0] [[X VAR 0] [Y VAR 1]]] 2 []])
					 ['+ (list 7 (symbol ";") 'Y ':= '- 12 (symbol ";") 'END (symbol ".")) ['VAR 'X (symbol ",") 'Y (symbol ";") 'BEGIN 'X (symbol ":=")] :error '[[0] [[X VAR 0] [Y VAR 1]]] 2 []]
			)
		)
)

(deftest test33
	(testing "procesar-signo-unarioEj2"
			(assertEquals
					(procesar-signo-unario [7 (list (symbol ";") 'Y ':= '- 12 (symbol ";") 'END (symbol ".")) ['VAR 'X (symbol ",") 'Y (symbol ";") 'BEGIN 'X (symbol ":=")] :sin-errores '[[0] [[X VAR 0] [Y VAR 1]]] 2 []])
					[7 (list (symbol ";") 'Y ':= '- 12 (symbol ";") 'END (symbol ".")) ['VAR 'X (symbol ",") 'Y (symbol ";") 'BEGIN 'X (symbol ":=")] :sin-errores '[[0] [[X VAR 0] [Y VAR 1]]] 2 []]
			)
	)
)
(deftest test34
	(testing "procesar-signo-unarioEj3"
			(assertEquals
				(procesar-signo-unario ['+ (list 7 (symbol ";") 'Y ':= '- 12 (symbol ";") 'END (symbol ".")) ['VAR 'X (symbol ",") 'Y (symbol ";") 'BEGIN 'X (symbol ":=")] :sin-errores '[[0] [[X VAR 0] [Y VAR 1]]] 2 []])
				[7 (list (symbol ";") 'Y ':= '- 12 (symbol ";") 'END (symbol ".")) ['VAR 'X (symbol ",") 'Y (symbol ";") 'BEGIN 'X (symbol ":=") '+] :sin-errores '[[0] [[X VAR 0] [Y VAR 1]]] 2 []]
			)
	)
)
(deftest test35
	(testing "procesar-signo-unarioEj4"
			(assertEquals
					(procesar-signo-unario ['- (list 7 (symbol ";") 'Y ':= '- 12 (symbol ";") 'END (symbol ".")) ['VAR 'X (symbol ",") 'Y (symbol ";") 'BEGIN 'X (symbol ":=")] :sin-errores '[[0] [[X VAR 0] [Y VAR 1]]] 2 []])
					[7 (list (symbol ";") 'Y ':= '- 12 (symbol ";") 'END (symbol ".")) ['VAR 'X (symbol ",") 'Y (symbol ";") 'BEGIN 'X (symbol ":=") '-] :sin-errores '[[0] [[X VAR 0] [Y VAR 1]]] 2 []]
			)
	)
)

;################# termino #################
(deftest test36
	(testing "terminoEj1"
			(assertEquals
				(termino ['X (list '* 2 'END (symbol ".")) ['VAR 'X (symbol ";") 'BEGIN 'X (symbol ":=")] :error '[[0] [[X VAR 0]]] 1 []])
					['X (list '* 2 'END (symbol ".")) ['VAR 'X (symbol ";") 'BEGIN 'X (symbol ":=")] :error '[[0] [[X VAR 0]]] 1 []]
			)
	)
)
(deftest test37
	(testing "terminoEj2"
		(assertEquals
			(str (termino ['X (list '* 2 'END (symbol ".")) ['VAR 'X (symbol ";") 'BEGIN 'X (symbol ":=")] :sin-errores '[[0] [[X VAR 0]]] 1 []]))
			"[END (.) [VAR X ; BEGIN X := X * 2] :sin-errores [[0] [[X VAR 0]]] 1 [[PFM 0] [PFI 2] MUL]]"
		)
	)
)
;################# expresion #################

(deftest test38
	(testing "expresionEj1"
			(assertEquals
					(str (expresion ['- (list (symbol "(") 'X '* 2 '+ 1 (symbol ")") 'END (symbol ".")) ['VAR 'X (symbol ";") 'BEGIN 'X (symbol ":=")] :error '[[0] [[X VAR 0]]] 1 []]))
					"[- (( X * 2 + 1 ) END .) [VAR X ; BEGIN X :=] :error [[0] [[X VAR 0]]] 1 []]"
		)
	)
)
(deftest test39
	(testing "expresionEj2"
			(assertEquals
					(str (expresion ['+ (list (symbol "(") 'X '* 2 '+ 1 (symbol ")") 'END (symbol ".")) ['VAR 'X (symbol ";") 'BEGIN 'X (symbol ":=")] :sin-errores '[[0] [[X VAR 0]]] 1 []]))
					"[END (.) [VAR X ; BEGIN X := + ( X * 2 + 1 )] :sin-errores [[0] [[X VAR 0]]] 1 [[PFM 0] [PFI 2] MUL [PFI 1] ADD]]"
			)
	)
)
(deftest test40
	(testing "expresionEj3"
			(assertEquals
					(str (expresion ['- (list (symbol "(") 'X '* 2 '+ 1 (symbol ")") 'END (symbol ".")) ['VAR 'X (symbol ";") 'BEGIN 'X (symbol ":=")] :sin-errores '[[0] [[X VAR 0]]] 1 []]))
					"[END (.) [VAR X ; BEGIN X := - ( X * 2 + 1 )] :sin-errores [[0] [[X VAR 0]]] 1 [[PFM 0] [PFI 2] MUL [PFI 1] ADD NEG]]"
			)
	)
)
;################# aplicar-aritmetico #################
(deftest test41
	(testing "aplicar-aritmeticoEj1"
			(assertEquals
					(str (aplicar-aritmetico + [1 2]))
					"[3]"
			)
	)
)
(deftest test42
	(testing "aplicar-aritmeticoEj2"
			(assertEquals
					(str (aplicar-aritmetico - [1 4 1]))
					"[1 3]"
			)
	)
)
(deftest test43
	(testing "aplicar-aritmeticoEj3"
			(assertEquals
					(str (aplicar-aritmetico * [1 2 4]))
					"[1 8]"
			)
	)
)
(deftest test44
	(testing "aplicar-aritmeticoEj4"
			(assertEquals
					(str (aplicar-aritmetico / [1 2 4]))
					"[1 0]"
			)
	)
)
(deftest test45
	(testing "aplicar-aritmeticoEj5"
			(assertEquals
					(aplicar-aritmetico + nil)
					nil
			)
	)
)
(deftest test46
	(testing "aplicar-aritmeticoEj6"
			(assertEquals
					(aplicar-aritmetico + [])
					[]
			)
	)
)
(deftest test47
	(testing "aplicar-aritmeticoEj7"
			(assertEquals
					(str (aplicar-aritmetico + [1]))
					"[1]"
			)
	)
)
(deftest test48
	(testing "aplicar-aritmeticoEj8"
			(assertEquals
					(str (aplicar-aritmetico 'hola [1 2 4]))
					"[1 2 4]"
			)
	)
)
(deftest test49
	(testing "aplicar-aritmeticoEj9"
			(assertEquals
					(str (aplicar-aritmetico count [1 2 4]))
					"[1 2 4]"
			)
	)
)
(deftest test50
	(testing "aplicar-aritmeticoEj10"
			(assertEquals
					(str (aplicar-aritmetico + '[a b c]))
					"[a b c]"
			)
	)
)
;################# aplicar-relacional #################
(deftest test51
	(testing "aplicar-relacionalEJ1"
			(assertEquals
					(str (aplicar-relacional > [7 5]))
					"[1]"
			)
	)
)
(deftest test52
	(testing "aplicar-relacionalEJ2"
			(assertEquals
					(str (aplicar-relacional > [4 7 5]))
					"[4 1]"
			)
	)
)
(deftest test53
	(testing "aplicar-relacionalEJ3"
			(assertEquals
					(str (aplicar-relacional = [4 7 5]))
					"[4 0]"
			)
	)
)
(deftest test54
	(testing "aplicar-relacionalEJ4"
			(assertEquals
					(str (aplicar-relacional not= [4 7 5]))
					"[4 1]"
			)
	)
)
(deftest test55
	(testing "aplicar-relacionalEJ5"
			(assertEquals
					(str (aplicar-relacional < [4 7 5]))
					"[4 0]"
			)
	)	
)
(deftest test56
	(testing "aplicar-relacionalEj6"
			(assertEquals
					(str (aplicar-relacional <= [4 6 6]))
					"[4 1]"
			)
	)
)
(deftest test57
	(testing "aplicar-relacionalEj7"
			(assertEquals
					(str (aplicar-relacional <= '[a b c]))
					"[a b c]"
			)
	)
)
;################# dump #################

(deftest test58
	(testing "dumpEj1"
			(assertEquals
					(dump '[[PFM 0] [PFI 2] MUL [PFI 1] ADD NEG])
					nil
			)
	)
)
(deftest test59
	(testing "dumpEj2"
			(assertEquals
					(dump '[HLT])
					nil
			)
	)
)
(deftest test61
	(testing "dumpEj3"
			(assertEquals
					(dump nil)
					nil
			)
	)
)
;################# generar #################

(deftest test61
	(testing "generarEj2"
			(assertEquals
					(str (generar '[nil () [VAR X] :sin-errores [[0] []] 0 [[JMP ?]]] 'HLT))
					"[nil () [VAR X] :sin-errores [[0] []] 0 [[JMP ?] HLT]]"
			)
	)
)
(deftest test62
	(testing "generarEj3"
			(assertEquals
					(str (generar '[nil () [VAR X] :sin-errores [[0] []] 0 [[JMP ?]]] 'PFM 0))
					"[nil () [VAR X] :sin-errores [[0] []] 0 [[JMP ?] [PFM 0]]]"
			)
	)
)
(deftest test63
	(testing "generarEj4"
			(assertEquals
					(str (generar '[nil () [VAR X] :error [[0] []] 0 [[JMP ?]]] 'HLT))
					"[nil () [VAR X] :error [[0] []] 0 [[JMP ?]]]"
			)
	)
)
(deftest test65
	(testing "generarEj5"
			(assertEquals
					(str (generar '[nil () [VAR X] :error [[0] []] 0 [[JMP ?]]] 'PFM 0))
					"[nil () [VAR X] :error [[0] []] 0 [[JMP ?]]]"
			)
	)
)
;################# buscar-coincidencias #################
(deftest test66
	(testing "buscar-coincidenciasEj1"
			(assertEquals
					(buscar-coincidencias '[nil () [CALL X] :sin-errores [[0 3] [[X VAR 0] [Y VAR 1] [A PROCEDURE 1] [X VAR 2] [Y VAR 3] [B PROCEDURE 2]]] 6 [[JMP ?] [JMP 4] [CAL 1] RET]])
					'([X VAR 0] [X VAR 2])
			)
	)	
)
;################# fixup #################
(deftest test67
	(testing "fixupEj1"
			(assertEquals
					(str (fixup ['WRITELN (list 'END (symbol ".")) [] :error [[0 3] []] 6 '[[JMP ?] [JMP ?] [CAL 1] RET]] 1))
					"[WRITELN (END .) [] :error [[0 3] []] 6 [[JMP ?] [JMP ?] [CAL 1] RET]]"
			)
	)
)
(deftest test68
	(testing "fixupEj2"
			(assertEquals
					(str (fixup ['WRITELN (list 'END (symbol ".")) [] :sin-errores [[0 3] []] 6 '[[JMP ?] [JMP ?] [CAL 1] RET]] 1))
					"[WRITELN (END .) [] :sin-errores [[0 3] []] 6 [[JMP ?] [JMP 4] [CAL 1] RET]]"
			)
	)
)
(deftest test69
	(testing "fixupEj3"
			(assertEquals
					(str (fixup ['WRITELN (list 'END (symbol ".")) [] :sin-errores [[0 3] []] 6 '[[JMP ?] [JMP 4] [CAL 1] RET [PFM 2] OUT NL RET]] 0))
					"[WRITELN (END .) [] :sin-errores [[0 3] []] 6 [[JMP 8] [JMP 4] [CAL 1] RET [PFM 2] OUT NL RET]]"
			)
	)
)
;################# generar-operador-relacional #################
(deftest test70
	(testing "generar-operador-relacionalEj1"
		(assertEquals
					(str (generar-operador-relacional ['WRITELN (list 'END (symbol ".")) [] :error [[0 3] []] 6 '[[JMP ?] [JMP ?] [CAL 1] RET]] '=))
					"[WRITELN (END .) [] :error [[0 3] []] 6 [[JMP ?] [JMP ?] [CAL 1] RET]]"
			)
	)
)
(deftest test71
	(testing "generar-operador-relacionalEj2"
			(assertEquals
					(str (generar-operador-relacional ['WRITELN (list 'END (symbol ".")) [] :sin-errores [[0 3] []] 6 '[[JMP ?] [JMP ?] [CAL 1] RET]] '+))
					"[WRITELN (END .) [] :sin-errores [[0 3] []] 6 [[JMP ?] [JMP ?] [CAL 1] RET]]"
			)
	)
)
(deftest test72
	(testing "generar-operador-relacionalEj3"
			(assertEquals
					(str (generar-operador-relacional ['WRITELN (list 'END (symbol ".")) [] :sin-errores [[0 3] []] 6 '[[JMP ?] [JMP ?] [CAL 1] RET]] '=))
					"[WRITELN (END .) [] :sin-errores [[0 3] []] 6 [[JMP ?] [JMP ?] [CAL 1] RET EQ]]"
			)
	)
)
(deftest test73
	(testing "generar-operador-relacionalEj4"
			(assertEquals
					(str (generar-operador-relacional ['WRITELN (list 'END (symbol ".")) [] :sin-errores [[0 3] []] 6 '[[JMP ?] [JMP ?] [CAL 1] RET]] '>=))
					"[WRITELN (END .) [] :sin-errores [[0 3] []] 6 [[JMP ?] [JMP ?] [CAL 1] RET GTE]]"
			)
	)
)
;################# generar-signo #################
(deftest test74
	(testing "generar-signoEj1"
			(assertEquals
					(str (generar-signo [nil () [] :error '[[0] [[X VAR 0]]] 1 '[MUL ADD]] '-))
					"[nil () [] :error [[0] [[X VAR 0]]] 1 [MUL ADD]]"
			)
	)
)
(deftest test75
	(testing "generar-signoEj2"
			(assertEquals
					(str (generar-signo [nil () [] :error '[[0] [[X VAR 0]]] 1 '[MUL ADD]] '+))
					"[nil () [] :error [[0] [[X VAR 0]]] 1 [MUL ADD]]"
			)
	)
)
(deftest test76
	(testing "generar-signoEj3"
			(assertEquals
					(str (generar-signo [nil () [] :sin-errores '[[0] [[X VAR 0]]] 1 '[MUL ADD]] '+))
					"[nil () [] :sin-errores [[0] [[X VAR 0]]] 1 [MUL ADD]]"
			)
	)
)
(deftest test77
	(testing "generar-signoEj4"
			(assertEquals
					(str (generar-signo [nil () [] :sin-errores '[[0] [[X VAR 0]]] 1 '[MUL ADD]] '*))
					"[nil () [] :sin-errores [[0] [[X VAR 0]]] 1 [MUL ADD]]"
			)
	)
)
(deftest test78
	(testing "generar-signoEj5"
			(assertEquals
					(str (generar-signo [nil () [] :sin-errores '[[0] [[X VAR 0]]] 1 '[MUL ADD]] '-))
					"[nil () [] :sin-errores [[0] [[X VAR 0]]] 1 [MUL ADD NEG]]"
			)
	)
)