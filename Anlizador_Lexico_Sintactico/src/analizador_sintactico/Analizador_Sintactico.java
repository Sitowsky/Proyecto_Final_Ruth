package analizador_sintactico;

import analisis_lexico.Analizador_Lexico;
import generar_estructuras.AnalizadorDeGramatica;
import java.io.IOException;
import pila.Pila;

public class Analizador_Sintactico {

    private final int tabla[][] = {
        //Matriz predictiva
        //p  b end id := ;  (  ) il  rl +  -  *
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 6, 0, 0, 6, 0, 6, 6, 0, 0, 0},
        {0, 0, 0, 0, 0, 8, 0, 8, 0, 0, 7, 7, 7},
        {0, 0, 0, 10, 0, 0, 9, 0, 11, 12, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 14, 15},};
    private final int i;
    private final String cadena;
    private final AnalizadorDeGramatica ag;
    Analizador_Lexico n;

    public Analizador_Sintactico(String cadena) {
        this.cadena = cadena;
        i = 0;
        ag = new AnalizadorDeGramatica();
        n = new Analizador_Lexico();
    }

    public void comenzar() throws IOException {
        ag.analizar();
        LLDriver();
        n.impresionTokens();
        n.tablaSimbolos();
    }

    private void LLDriver() {

        Pila pila = new Pila();      //instanciamos nuestra pila         
        String a = n.tokenizer(cadena, n.getI()); //a sera nuestros tokens de la cadena a probar
        pila.push("inicio"); //colocamos a la pila la produccion inicial 
        String x = pila.peek(); //x sera nuestro ultimo dato ingresado a la pila

        while (!pila.empty()) { //mientras la pila no este vacia

            System.out.println("x= " + x + ",  a= " + a);
            pila.showStack();//mostrar lo que tiene la pila
            
            if (isInArray(x, ag.getNoTerminales())) {//si "x" esta en los terminales
                System.out.println(getRow(x) + ", " + getColumn(a));//pocisiones de la matriz 
                if (tabla[getRow(x)][getColumn(a)] != 0) {
                    pila.pop(); //quitamos el ultimo elemento añadido a la pila,
                                //ej: en la primer iteracion es "inicio" , para añadir la derivacion de este. 
                    String[] aux = ag.getDerivaciones()[tabla[getRow(x)][getColumn(a)] - 1].split(" ");
                    for (int m = aux.length - 1; m >= 0; m--) {
                        if (!"".equals(aux[m])) {
                            pila.push(aux[m]);//colocaremos las derivaciones de la produccion
                                                //ej: en la primer iteracion seria "programa program"
                        }
                    }
                } else {
                    System.out.println("Error sintactico en x = " + x + " y a = " + a);
                    System.exit(0);
                }
            } else {

                if (isInArray(x, ag.getTerminales())) {//verificaremos si el token es un terminal 
                    pila.pop();//si es terminal lo retiramos de la pila
                    a = n.tokenizer(cadena, n.getI()); //volvemos a llamar ahora el siguiente token
                } else {
                    System.out.println("Error sintactico en x = " + x + " y a = " + a);
                    System.exit(0);
                }

            }
            if (pila.empty()) {
                System.out.println("Tu programa esta escrito correctamente :)");
            } else {
                x = pila.peek();
            }
            System.out.println("------------------------");
        }

    }

    private int getRow(String x) {
        String[] noTerminales = ag.getNoTerminales();
        for (int j = 0; j < noTerminales.length; j++) {
            if (noTerminales[j].equals(x)) {
                return j;
            }
        }
        return 0;
    }

    private int getColumn(String a) {
        String[] terminales = ag.getTerminales();
        for (int j = 0; j < terminales.length; j++) {
            if (terminales[j].equals(a)) {
                return j;
            }
        }
        return 0;
    }

    private boolean isInArray(String word, String[] array) {
        for (String array1 : array) {
            for (String split : word.split(" ")) {
                if (split.equals(array1)) {
                    return true;
                }
            }
        }
        return false;
    }
}
