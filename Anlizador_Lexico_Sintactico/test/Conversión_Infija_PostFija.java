/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import pila.Pila;

/**
 *
 * @author fabri
 */
public class Conversión_Infija_PostFija {
    
    public static void main(String[] args) {
        Conversión_Infija_PostFija n = new Conversión_Infija_PostFija ();
        n.infija_postfija("Precio:=58.5+(3212.2+53.25-90+30)");
        
    }


    @SuppressWarnings("empty-statement")
    public String infija_postfija(String g) {
        ArrayList<precedencia> precede = new ArrayList();
        precede.add(new precedencia(4, "="));
        precede.add(new precedencia(1, "("));
        precede.add(new precedencia(3, "*"));
        precede.add(new precedencia(3, "/"));
        precede.add(new precedencia(2, "+"));
        precede.add(new precedencia(2, "-"));
        Pila pilaOperadores = new Pila();
        String sufija = "";
        g=g.replace(":", "");
        String[] simbols = g.split("");
        for (String simbol : simbols) {
            System.out.println(sufija);
            if (("ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwqyz0123456789.").contains(simbol)) {

                sufija = sufija + simbol;

            } else if ("(".equals(simbol)) {
                pilaOperadores.push(simbol);

            } else if (")".equals(simbol)) {

                String simboloTope = pilaOperadores.peek();
                pilaOperadores.pop();
                while (!"(".equals(simboloTope)) {
                    sufija = sufija + simboloTope;
                    simboloTope = pilaOperadores.peek();
                    pilaOperadores.pop();
                }

            } else {

                int precedencia2 = 0;
                int precedencia1 = 0;
                

                while (!pilaOperadores.empty() && precdencia2(pilaOperadores, precede) >= precdencia(pilaOperadores,simbol, precede)) {
                    pilaOperadores.showStack();
                    System.out.println(simbol);
                    System.out.println(sufija);
                    sufija = sufija + pilaOperadores.peek();

                    pilaOperadores.pop();
                }

                pilaOperadores.push(simbol);
                pilaOperadores.showStack();

            }

        }

        while (!pilaOperadores.empty()) {
            sufija = sufija + pilaOperadores.peek();
            pilaOperadores.pop();

        }
        System.out.println(sufija);
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
