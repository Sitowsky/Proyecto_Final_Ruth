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
        ArrayList<precedencia> precede = new ArrayList();
        precede.add(new precedencia(4, "="));
        precede.add(new precedencia(1, "("));
        precede.add(new precedencia(3, "*"));
        precede.add(new precedencia(3, "/"));
        precede.add(new precedencia(2, "+"));
        precede.add(new precedencia(2, "-"));
        int igual = 0;
        for (int i = 0; i < g.length(); i++) {
            if (g.substring(0, i).contains("=")) {
                igual = i;
                break;
            }
        }
        String equal = g.substring(0, igual-2);
        System.out.println(equal);
        g = g.substring(igual);
        Pila pilaOperadores = new Pila();
        String sufija = "";
        g=g.replace(":", "");
        String[] simbols = g.split("");
        for (String simbol : simbols) {
            
            if (("ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwqyz0123456789.").contains(simbol)) {

                sufija = sufija + simbol;

            } else if ("(".equals(simbol)) {
                pilaOperadores.push(simbol);

            } else if (")".equals(simbol)) {
                
                String simboloTope = pilaOperadores.peek();
                
                pilaOperadores.pop();
                while (!"(".equals(simboloTope)) {
                    sufija = sufija + " ";
                    sufija = sufija + simboloTope;
                    simboloTope = pilaOperadores.peek();
                    pilaOperadores.pop();
                }

            } else {
                while (!pilaOperadores.empty() && precdencia2(pilaOperadores, precede) >= precdencia(pilaOperadores,simbol, precede)) {
                    sufija = sufija + " ";
                    sufija = sufija + pilaOperadores.peek();

                    pilaOperadores.pop();
                }
                sufija = sufija + " ";

                pilaOperadores.push(simbol);

            }

        }

        while (!pilaOperadores.empty()) {
            sufija = sufija + " ";
            sufija = sufija + pilaOperadores.peek();
            pilaOperadores.pop();

        }
        return sufija + " = "+equal;

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

