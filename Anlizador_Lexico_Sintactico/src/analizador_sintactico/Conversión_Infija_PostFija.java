/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador_sintactico;

import java.util.ArrayList;
import pila.Pila;

/**
 *
 * @author fabri
 */
public class Conversión_Infija_PostFija {
    
    @SuppressWarnings("empty-statement")
    public String infija_postfija(String g) {
        ArrayList<precedencia> precede = new ArrayList();//nuestro arraylist de los operando posibles
        precede.add(new precedencia(4, "="));
        precede.add(new precedencia(1, "("));
        precede.add(new precedencia(3, "*"));
        precede.add(new precedencia(3, "/"));
        precede.add(new precedencia(2, "+"));
        precede.add(new precedencia(2, "-"));
        Pila pilaOperadores = new Pila(); //instanciamos una pila , para nuestros operadores 
        String sufija = "";
        g=g.replace(":", ""); //eliminamos el ":" de cada expresion
        String[] simbols = g.split(""); //separamos nuestras expresion en caracteres para evaluarlos 
        for (String simbol : simbols) {
            if (("ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwqyz0123456789.").contains(simbol)) {
                //si contiene algun operando la expresion se guardara en subfija
                sufija = sufija + simbol;

            } else if ("(".equals(simbol)) { //si existe alguna subexpresion que se divida entre parentesis
                pilaOperadores.push(simbol);

            } else if (")".equals(simbol)) { //entraremos cuando se haya terminado alguna subexpresion expresa con parentesis 

                String simboloTope = pilaOperadores.peek(); //obtenemos el ultimo valor de la pila ")"
                pilaOperadores.pop(); //lo retiramos (el ultimo elemento ingresado de la pila)
                while (!"(".equals(simboloTope)) {
                    sufija = sufija + simboloTope; //guardamos en nuestra expresion los operadores
                    simboloTope = pilaOperadores.peek(); //obtenemos el siguiente operador de la pila
                    pilaOperadores.pop();//retiramos el ultimos elemento añadido a la pila de operandos
                }

            } else {
                
                while (!pilaOperadores.empty() && precdencia2(pilaOperadores, precede) >= precdencia(pilaOperadores,simbol, precede)) {
                    sufija = sufija + pilaOperadores.peek();
                    pilaOperadores.pop();
                }
                pilaOperadores.push(simbol);
            }

        }

        while (!pilaOperadores.empty()) {
            sufija = sufija + pilaOperadores.peek();
            pilaOperadores.pop();

        }
        return sufija;

    }
    

    private int precdencia(Pila pilaOperadores, String simbol, ArrayList<precedencia> precede) {
        if (!pilaOperadores.empty()) {

                    for (precedencia precede1 : precede) {
                        if (precede1.getSimbol().equals(simbol)) {
                            return precede1.getKm();
                        }
                    }
        }
        return 0;
    }

    private int precdencia2(Pila pilaOperadores, ArrayList<precedencia> precede) {
        if (!pilaOperadores.empty()) {
                    for (precedencia precede1 : precede) {
                        if (precede1.getSimbol().equals(pilaOperadores.peek())) {
                            return precede1.getKm();
                        }
                    }
        }
        return 0;
    }

}

