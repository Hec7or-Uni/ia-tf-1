; -------------------------------------------------------------
; MODULO MAIN (COMPLETAR)
;-------------------------------------------------------------
(defmodule MAIN
    (export deftemplate nodo)
)

(deftemplate MAIN::nodo
    (slot estado);   Definición del estado.
    (multislot camino)
    (slot coste (default 0))
    (slot clase (default abierto))
    (slot numAndar (default 0))
    (slot numSaltar (default 0))
)

(deffacts MAIN::estado-inicial
;   DEFINE Nodo inicial
    (nodo (estado 1))
)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; MODULO MAIN::CONTROL            ;;;
;;; CU                              ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defrule MAIN::pasa-el-mejor-a-cerrado-CU
;   IMPLEMENTA CU
	?nodo <- (nodo (coste ?c1) (clase abierto))
	(not (nodo (clase abierto) (coste ?c2&:(< ?c2 ?c1)) ))
	=>
    (modify ?nodo (clase cerrado))
    (focus OPERADORES)
)

;-------------------------------------------------------------
; MODULO OPERADORES (COMPLETAR)
;-------------------------------------------------------------
; Acciones andar y saltar con sus restricciones
(defmodule OPERADORES
    (import MAIN deftemplate nodo)
)

(defrule OPERADORES::Andar
;   IMPLEMENTA
   (nodo (estado ?o)
            (camino $?movimientos)
            (clase cerrado)
            (coste ?coste)
            (numAndar ?num))
    =>
    (bind ?nuevo-estado (+ ?o 1))
    (assert (nodo 
		      (estado ?nuevo-estado)
              (camino $?movimientos A)
              (coste (+ ?coste 1))
              (numAndar (+ ?num 1))))
)

(defrule OPERADORES::Saltar
;   IMPLEMENTA
    (nodo (estado ?o&:(<= ?o 4))
            (camino $?movimientos)
            (clase cerrado)
            (coste ?coste)
            (numAndar ?numA)
            (numSaltar ?numS&:(> ?numA ?numS)))
    =>
    (bind ?nuevo-estado (* ?o 2))
    (assert (nodo 
		      (estado ?nuevo-estado)
              (camino $?movimientos S)
              (coste (+ ?coste 2))
              (numSaltar (+ ?numS 1))))
)

;-------------------------------------------------------------
; MODULO RESTRICCIONES (COMPLETAR)
;-------------------------------------------------------------
; Nos quedamos con el nodo de menor coste
; La longitud del camino no es el coste
(defmodule RESTRICCIONES
    (import MAIN deftemplate nodo)
)

; eliminamos nodos repetidos
; en base al camino
(defrule RESTRICCIONES::repeticiones-de-camino
;   IMPLEMENTA
	(declare (auto-focus TRUE))
    ?nodo1 <- (nodo (estado ?estado) (camino $?camino1))
    (nodo (estado ?estado) (camino $?camino2&:(> (length$ ?camino1) (length$ ?camino2))))
    =>
    (retract ?nodo1)
)

; eliminamos nodos repetidos
; en base al coste
(defrule RESTRICCIONES::repeticiones-de-coste
;   IMPLEMENTA
	(declare (auto-focus TRUE))
    ?nodo1 <- (nodo (estado ?estado) (coste ?coste1))
    (nodo (estado ?estado) (coste ?coste2&:(> ?coste1 ?coste2)))
    =>
    (retract ?nodo1)
)


;-------------------------------------------------------------
; MODULO SOLUCION
;-------------------------------------------------------------
;Definimos el modulo solución
(defmodule SOLUCION
    (import MAIN deftemplate nodo)
)

;Miramos si hemos encontrado la solucion
(defrule SOLUCION::encuentra-solucion
    (declare (auto-focus TRUE))
    ?nodo1 <- (nodo (estado 8) (camino $?camino) (clase cerrado))
    =>
    (retract ?nodo1)
    (assert (solucion ?camino))
)

;Escribimos la solución por pantalla
(defrule SOLUCION::escribe-solucion
    (solucion $?movimientos)
=>
    (printout t "La solucion tiene " (- (length ?movimientos) 1)
    " pasos" crlf)
    (loop-for-count (?i 1 (length ?movimientos))
    (printout t "(" (nth ?i $?movimientos) ")" " "))
    (printout t crlf)
    (halt)
)