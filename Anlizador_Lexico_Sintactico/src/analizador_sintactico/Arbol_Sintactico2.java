package analizador_sintactico;

import analisis_lexico.Table;
import java.util.ArrayList;
import java.util.Arrays;

public class Arbol_Sintactico2 {

    ArrayList<String> simbols_table_new = new ArrayList<>();
    ArrayList tipos = new ArrayList();

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public Arbol_Sintactico2(String[][] tokens, String[][] simbols, ArrayList<String> simbols_table) {

        System.out.println("\n*******************************************************");
        System.out.println("\nArbol sintactico abstracto\n");
        Expresion(tokens, simbols_table, tipos);

    }

    public void Expresion(String[][] tokens, ArrayList<String> simbols_table, ArrayList tipos) {// construiremos toda nuestra linea de expresion 
        System.out.println("NOMBRE        TIPO         VALOR  REP  LÍNEA ATRIBUTO");

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
                    Validacion(tokens, simbols_table, expresion, desde, fila, tipos);

                }
            }
        }

    }
//

    public void Validacion(String[][] tokens, ArrayList<String> simbols_table, String expresion, int desde, int hasta, ArrayList tipos) {

        int n = 0;
        String[] variables = new String[NumSubExpre(desde, hasta, tokens)];

        for (int i = desde + 1; i < hasta; i++) {
            if (tokens[i][2].equals("Int") || tokens[i][2].equals("Flotante")) {
                variables[n] = tokens[i][2];
                for (int j = 0; j < simbols_table.size(); j++) {
                    String variable = simbols_table.get(j).split("\\|")[0];
                    String busqueda = variable.replaceAll(" ", "");
                    if (busqueda.equals(tokens[i][0])) {
                        if (simbols_table.get(j).split("\\|")[1].contains("Númeroentero")) {
                            System.out.println(simbols_table.get(j).replace(" Númeroentero   ", "      Int     "));
                        }
                        if (simbols_table.get(j).split("\\|")[1].contains("Númeroflotante")) {
                            System.out.println(simbols_table.get(j).replace(" Númeroflotante ", "   Flotante   "));
                        }
                        if (simbols_table.get(j).split("\\|")[1].contains("Identificador")) {
                            System.out.println(simbols_table.get(j).replace(" Identificador  ", "      Int     "));
                        }

                    }

                }
                n++;
            }
        }

        String[] e = expresion.split("\\:");
        String tipeFinal = "";
        for (int i = 0; i < variables.length; i++) {
            if (i == 0) {
                tipeFinal = variables[i];//el primer termino sera nuestro valor final , dado que es el primero

            } else {
                tipeFinal = comprobacionTipos(tipeFinal, variables[i]);

            }

        }

        for (int j = 0; j < simbols_table.size(); j++) {
            String variable = simbols_table.get(j).split("\\|")[0];
            String busqueda = variable.replaceAll(" ", "");
            if (busqueda.equals(e[0])) {
                if (tipeFinal.equals("Int")) {
                    System.out.println(simbols_table.get(j).replace("Identificador", "     Int   "));

                } else {
                    System.out.println(simbols_table.get(j).replace("Identificador", "  Flotante "));

                }

            }

        }
        if (tipeFinal.equals("Int")) {
            System.out.println("expre   |      Int     " + "|        |   |        |        |");
        } else {
            System.out.println("expre   |   Flotante   " + "|        |   |        |        |");
        }

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
        for (int i = desde + 1; i < hasta; i++) {
            if (tokens[i][2].equals("Int") || tokens[i][2].equals("Flotante")) {
                subExpre++;
            }

        }
        return subExpre;
    }

}
