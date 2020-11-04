/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import analizador_sintactico.*;
import java.util.ArrayList;
import java.util.Arrays;
import pila.Pila;

/**
 *
 * @author fabri
 */
public class Code_Intermedio {

    public static void main(String[] args) {
        Code_Intermedio n = new Code_Intermedio();
        n.crear("");
    }

    public void crear(String g) {
        Pila listaInt = new Pila();
        Pila variables = new Pila();
        int varia = 1;
        g = "32 3.25 * = Precio2";
        String[] simbols = g.split(" ");
        String equal = simbols[simbols.length - 1];
        System.out.println(Arrays.toString(simbols));
        for (String simbol : simbols) {
            if (simbol.equals("+") || simbol.equals("-") || simbol.equals("*") || simbol.equals("/") || simbol.equals("=")) {
                if ((listaInt.size()) >= 2) {
                    System.out.print(simbol + " " + listaInt.peek());
                    listaInt.pop();
                    System.out.print(" " + listaInt.peek());
                    listaInt.pop();
                    System.out.print(" v" + varia);
                    variables.push("v" + varia);
                    varia++;
                    System.out.println("");
                } else if ((listaInt.size()) == 1 && (variables.size()) >= 1) {
                    System.out.print(simbol + " " + listaInt.peek());
                    listaInt.pop();
                    System.out.print(" " + variables.peek());
                    System.out.println(" " + variables.peek());
                } else if ((variables.size()) >= 2 && listaInt.empty()) {

                    System.out.print(simbol + " " + variables.peek());
                    variables.pop();
                    System.out.print(" " + variables.peek());
                    System.out.print(" " + variables.peek());
                    System.out.println("");
                } else {
                    System.out.print(simbol + " " + variables.peek());
                    variables.pop();
                    System.out.print(" " + equal);
                    System.out.print(" " + equal);
                    System.out.println("");

                }

            } else {
                listaInt.push(simbol);
            }
        }

    }

}
