package analizador_sintactico;

import analisis_lexico.Table;
import java.util.ArrayList;

/**
 *
 * @author chuy_
 *
 * Metodología: Paso 1. Para cada expresión realizar la validación de tipos
 * hasta que sea el fin de archivo. Paso 2. Validar si los tipos de cada
 * operando son iguales o compatibles. Paso 3. Si son iguales asignar el tipo a
 * la sentencia que generó y continuar con la validación de tipos. Paso 4. En
 * caso contrario deberá de marcar un error de tipo semántico y terminar el
 * análisis.
 *
 */
//el paso 1 lo realiza el analizador lexico
//para el paso 2 necesitamos saber cuantas subexpresiones son  R. SEPARADO POR PARENTESIS , SIGNOS 
//Es necesario la jerarquia de las operaciones 
    /*  1.parentesis
 2.potencias y raices
 3.multiplciaciones y diviciones 
 4.sumas y restas 
 */
    //metodos necesarios 
//1.analizar la jerarquia , y separar en expresiones 
//
public class Arbol_Sintactico {

    ArrayList<String> simbols_table_new = new ArrayList<>();

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    
//tokens es la matriz de la tabla de tokens , simbols la matriz de la segunda tabla de simboloes , 
    //simbols table es la misma de la segunda tabla pero en arraylist 
    
    public Arbol_Sintactico(String[][] tokens, String[][] simbols, ArrayList<String> simbols_table) {
        System.out.println("\n*******************************************************");
        System.out.println("\nArbol sintactico abstracto\n");
        Expresion(tokens, simbols_table);

    }

    public void Expresion(String[][] tokens, ArrayList<String> simbols_table) {// construiremos toda nuestra linea de expresion 

        String expresion = "";
        int desde = 0;
        for (int fila = 0; fila < tokens.length; fila++) {

            if (tokens[fila][1].equals("Identificador")) { //Desde que inicie con un identifcador
                expresion = tokens[fila][0]; //columna 0
                desde = fila;
                fila++;

                if (tokens[fila][0].equals(":=")) { //luego debe haber un simbolo de asignacion
                    expresion = expresion + tokens[fila][0];
                    fila++;

                    while (!tokens[fila][0].equals(";")) { // guardaremos toda la linea hasta que termine con ";"
                        expresion = expresion + tokens[fila][0];
                        fila++;
                    }
                    System.out.println("Su expresion aritmetica es: " + expresion);
                    Validacion(tokens, simbols_table, expresion, desde, fila);

                }
            }
        }

    }
//
    public void Validacion(String[][] tokens, ArrayList<String> simbols_table, String expresion, int desde, int hasta) {

        int n = 0;
        String[] variables = new String[NumSubExpre(desde, hasta, tokens)];

        for (int i = desde + 1; i < hasta; i++) {
            if (tokens[i][2].equals("Int") || tokens[i][2].equals("Flotante")) {
                variables[n] = tokens[i][2];
                System.out.print(ANSI_RED + "   " + variables[n] +" "+ tokens[i][0]+"  |"+ ANSI_RESET);
                n++;
            }
        }

        String[] e = expresion.split("\\:");
        String tipeFinal = "";
        for (int i = 0; i < variables.length; i++) {
            if (i == 0) {
                tipeFinal = variables[i];//el primer termino sera nuestro valor final , dado que es el primero

            } else {
                System.out.print("\n("+tipeFinal +") ("+variables [i]+") " );
                tipeFinal = comprobacionTipos(tipeFinal, variables[i]);
                System.out.print("---> (" + tipeFinal+")" );
            }
            
        }

//        System.out.println(ANSI_RED + "Texto de color rojo" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "\n" + e[0] + " es tipo " + tipeFinal + ANSI_RESET);
        System.out.println("---------------------------------------");

        //Ahora need agregar a la tabla esos valores AQUI FABRICIOOOOOOOOOO, la tabla se llama  simbols_table , ahi viene la 
        //tabla de la practica dos , aqui la puedes usar , en esta clase la pase como parametro 
        
    }


    private String comprobacionTipos(String tipeFinal, String tipo) { //comprobar el tipo final de la expresion

        if (tipeFinal.equals("Int") && tipo.equals("Int")) { //si ambos son int
            return "Int";
        } else if (tipeFinal.equals("Int") && tipo.equals("Flotante")) {
            return "Flotante"; 
        } else if (tipeFinal.equals("Flotante") && tipo.equals("Int")) {
            return "Flotante"; 
        } else if (tipeFinal.equals("Flotante") && tipo.equals("Flotante")) {
            return "Flotante";
        }
        return null;

    }

    private int NumSubExpre(int desde, int hasta, String[][] tokens) { //saber el tamño del arreglo donde pondremos los tipos
        int subExpre = 0;
//        System.out.println("la expresion es desde " + desde + ", hasta " + hasta);
        for (int i = desde + 1; i < hasta; i++) {
            if (tokens[i][2].equals("Int") || tokens[i][2].equals("Flotante")) {
                subExpre++;
            }

        }
        return subExpre;
    }
}
