package analisis_lexico;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import analizador_sintactico.Arbol_Sintactico;
import analizador_sintactico.Arbol_Sintactico2;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Fabricio
 */
public class Analizador_Lexico {

    String lexema;
    int token;
    int i = 0;
    ArrayList<String> tokens = new ArrayList<>();
    ArrayList<String> simbols = new ArrayList<>();
    int identificador = 500;
    int reservada = 300;
    int caracter = 50;

    public String tokenizer(String input, int i) {
        int estado = 0;
        lexema = "";

        while (i < input.length()) {
            switch (estado) {
                case 0:
                    if ((esMayus(input.charAt(i)))) { //primer caracter mayu                       
                        lexema = lexema + input.charAt(i);
                        i++;
                        estado = 1;
                        break;
                    } else if ((esDigito(input.charAt(i)))) {//primer caracter  digito

                        estado = 2;
                        break;
                    } else if (esCaracter(input.charAt(i))) { //primer caracter

                        lexema = lexema + input.charAt(i);
                        estado = 6;
                        break;
                    } else if (esCero(input.charAt(i))) { //primer caracter

                        estado = 3;
                        break;
                    } else {
                        estado = 4;

                        break;
                    }

                case 1:
                    while ((esGuion(input.charAt(i)))) { //si hay guiones que los guarde en el lexema                      
                        lexema = lexema + input.charAt(i);
                        i++;
                    }
                    if (esMinus(input.charAt(i)) || esDigito(input.charAt(i)) || esCero(input.charAt(i))) { //solo avanzara si tiene un minus o dig
                        lexema = lexema + input.charAt(i);
                        estado = 5;
                        i++;
                        break;
                    } else {
                        System.out.println("Error léxico en:" + lexema);
                        System.exit(0);
                    }

                case 2:
                    if (esDigito(input.charAt(i)) || esFlotante(input.charAt(i), input.charAt(i + 1))) {
                        lexema = lexema + input.charAt(i);
                        i++;
                        estado = 7;
                    } else {
                        lexema = lexema + input.charAt(i);
                        i++;
                        estado = 4;
                    }
                    break;
                case 3:

                    lexema = lexema + input.charAt(i);
                    i++;
                    if (esCero(input.charAt(i)) || esMinus(input.charAt(i)) || esMayus(input.charAt(i))) {
                        lexema = lexema + input.charAt(i);
                        i++;
                        System.exit(0);
                    } else {
                        estado = 9;
                        i++;
                        break;
                    }

                case 4:
                    if (esMinus(input.charAt(i))) {
                        estado = 8;
                        break;
                    } else {
                        estado = 0;
                        i++;
                        break;
                    }

                case 5:
                    while (esMinus(input.charAt(i)) || esDigito(input.charAt(i)) || esCero(input.charAt(i))) { //si hay guiones que los guarde en el lexema                      
                        lexema = lexema + input.charAt(i);
                        i++;
                    }
                    if (esGuion(input.charAt(i))) { //solo avanzara si tiene un minus o dig
                        lexema = lexema + input.charAt(i);
                        i++;
                        estado = 1;
                        break;
                    } else {
                        if (esEspacio(input.charAt(i)) == true || esCaracter(input.charAt(i)) == true) {
                            setI(i);
                            //a ver pa que aparescan ordenados en la tabla 
//     FAB                       agregaTokens(lexema+ "|   Identificador   |    Int | " +identificador++ );
                            agregaTokens(ProporcionalColum(lexema, 0) + "| Identificador     | Int                   | " + identificador++);
                            return ("id");
                        } else {

                            lexema = lexema + input.charAt(i);
                            System.out.println("Error léxico en:" + lexema);
                            System.exit(0);
                        }
                    }

                case 6:

                    if ((input.charAt(i - 1)) == ':' && (input.charAt(i)) == '=') {
                        setI(i + 1);
                        agregaTokens(":=      " + "| Símbolo especial  | Asignación            | " + caracter++ + " ");
                        return (":=");
                        //ASIGNACION PARA PRACTICA 3
                        
                    } else {
                        i++;
                        setI(i);
                        String tipo = queEs(lexema);
//  FAB                      agregaTokens(lexema+ "  |   Símbolo especial  |    "+tipo+"  | " +caracter++ );
                        agregaTokens(ProporcionalColum(lexema, 0) + "| Símbolo especial  | " + tipo + " | " + caracter++ + " ");
                        return (lexema);
                    }
                case 7:
                    if (esDigito(input.charAt(i)) || esCero(input.charAt(i))) {
                        lexema = lexema + input.charAt(i);
                        i++;
                    } else {
                        if (esFlotante(input.charAt(i), input.charAt(i + 1))) {
                            lexema = lexema + input.charAt(i);
                            i++;
                            estado = 10;
                        } else {
                            estado = 9;
                        }

                    }
                    break;

                case 8:
                    if (esReser(input.charAt(i))) {
                        lexema = lexema + input.charAt(i);
                        i++;
                        if ((input.charAt(i)) == 'r' && (input.charAt(i + 1)) == 'o' && (input.charAt(i + 2)) == 'g'
                                && (input.charAt(i + 3)) == 'r' && (input.charAt(i + 4)) == 'a' && (input.charAt(i + 5)) == 'm'
                                && ((input.charAt(i + 6)) == '\n' || (input.charAt(i + 6)) == ' ' || (input.charAt(i + 6)) == '\t')) {
                            i = i + 7;
                            setI(i);
                            agregaTokens("program" + " | Palabra reservada | Program start         | " + reservada++);
                            return ("program");

                        } else if ((input.charAt(i)) == 'e' && (input.charAt(i + 1)) == 'g' && (input.charAt(i + 2)) == 'i'
                                && (input.charAt(i + 3)) == 'n' && ((input.charAt(i + 4)) == '\n' || (input.charAt(i + 4)) == ' ' || (input.charAt(i + 4)) == '\t')) {
                            i = i + 5;
                            setI(i);
                            agregaTokens("begin" + "   | Palabra reservada | Inicio programa       | " + reservada++);
                            return ("begin");
                        } else if ((input.charAt(i)) == 'n' && (input.charAt(i + 1)) == 'd') {
                            i = i + 3;
                            setI(i);
                            agregaTokens("end" + "     | Palabra reservada | Fin de programa       | " + reservada++);
                            return ("end");
                        }
                    } else {
                        lexema = lexema + input.charAt(i);
                        i++;
                        while (esMinus(input.charAt(i)) || esDigito(input.charAt(i)) || esCero(input.charAt(i))) {
                            lexema = lexema + input.charAt(i);
                            i++;
                        }
                        System.out.println("Error léxico en:" + lexema);
                        System.exit(0);
                    }
                case 9:
                    setI(i);
// FAB                   agregaTokens(lexema+ "|    Número entero  |    Int  |  10 " );
                    agregaTokens(ProporcionalColum(lexema, 0) + "| Número entero     | Int                   | 10 ");
                    return ("intliteral");
                case 10:
                    if (esDigito(input.charAt(i)) || esCero(input.charAt(i))) {
                        lexema = lexema + input.charAt(i);
                        estado = 10;
                        i++;
                        break;
                    } else {
                        estado = 11;
                    }

                    break;
                case 11:
                    setI(i);
//   FAB                 agregaTokens(lexema+ "|    Número flotante  |    Flotante  |  11 " );
                    agregaTokens(ProporcionalColum(lexema, 0) + "| Número flotante   | Flotante              | 11 ");
                    return ("realliteral");
                default:

            }

        }
        return null;

    }

    boolean esMayus(char c) {
        return c >= 'A' && c <= 'Z';
    }

    boolean esMinus(char c) {
        return c >= 'a' && c <= 'z';
    }

    boolean esDigito(char c) {
        return c >= '1' && c <= '9';
    }

    boolean esCero(char c) {
        return c == '0';
    }

    boolean esEspacio(char c) {
        return c == ' ' || c == '\t' || c == '\n';
    }

    boolean esFlotante(char c, char m) {
        return c == '.' && m >= '0' && m <= '9';
    }

    boolean esGuion(char c) {
        return c == '_';
    }

    boolean esCaracter(char c) {
        return c == '-' || c == '(' || c == ')' || c == '*' || c == '+' || c == '=' || c == ';';
    }

    boolean esReser(char c) {
        return c == 'b' || c == 'p' || c == 'e';
    }

    private static boolean isInArray(String word, ArrayList<String> array) {
        for (int i = 0; i < array.size(); i++) {
            if (word.equals(array.get(i))) {
                return true;
            }
        }
        return false;
    }

    void setI(int ir) {
        i = ir;

    }

    public int getI() {
        return this.i;
    }

    public void agregaTokens(String lexema) {

        if (contiene(lexema).equals("")) {
            int m = veces(lexema);
            tokens.add(lexema + "   |    " + m);
        } else {
            String yaEsta = contiene(lexema);
            int m = veces(yaEsta);
            String[] parts = yaEsta.split("\\|");
            String auxm = "    " + Integer.toString(m);
            tokens.add(reemplazar(yaEsta, parts[4], auxm));
        }

    }

    public String queEs(String lexema) {
        String tipo = "";
        switch (lexema) {
            case "-":
                tipo = "Resta                ";
                break;
            case "+":
                tipo = "Suma                 ";
                break;
            case "*":
                tipo = "Multiplicación       ";
                break;
            case ";":
                tipo = "Punto y coma         ";
                break;
            case "(":
                tipo = "Paréntesis que abre  ";
                break;
            case ")":
                tipo = "Paréntesis que cierra";
                break;
        }

        return tipo;

    }

    public void impresionTokens() { //9,18,13
        System.out.println("LEXEMA  |      TOKEN        |         TIPO          | VALOR | REPETICIONES ");
        for (int x = 0; x < tokens.size(); x++) {
            System.out.println(tokens.get(x));
        }

        

    }

    private String ProporcionalColum(String lexema, int columna) {//metodo para agregar espacios y al imprimir salga por culomnas

        switch (columna) {

            case 0:
                if (lexema.length() < 8) {
                    for (int j = lexema.length(); j < 8; j++) {
                        lexema = lexema + " ";
                    }
                    return lexema;
                }
                return lexema;

            case 1:
                if (lexema.length() < 8) {
                    for (int j = lexema.length(); j < 8; j++) {
                        lexema = lexema + " ";
                    }
                    return lexema;
                }
                return lexema;
        }
        return "";
    }

    public String contiene(String lexema) {
        String palabra = "";
        String[] parts = lexema.split("\\|");
        String pala = parts[0];
        for (int j = 0; j < tokens.size(); j++) {
            if ((tokens.get(j)).contains(pala)) {
                return tokens.get(j);
            }
        }
        return palabra;
    }

    private int veces(String yaEsta) {
        int veces = 0;
        String[] parts = yaEsta.split("\\|");
        String pala = parts[0];
        for (int j = 0; j < tokens.size(); j++) {
            if ((tokens.get(j)).contains(pala)) {
                veces = veces + 1;
               
            }
        }

        return veces + 1;
    }

    public static String reemplazar(String cadena, String busqueda, String reemplazo) {
        return cadena.replaceAll(busqueda, reemplazo);
    }

    @SuppressWarnings("empty-statement")
    public void tablaSimbolos() throws IOException {

        for (int j = 0; j < tokens.size(); j++) {
            String[] parts = (tokens.get(j)).split("\\|");
            String comprobar = parts[1];
            if (comprobar.contains("Identificador") || comprobar.contains("Número flotante") || comprobar.contains("Número entero")) {
                simbols.add(tokens.get(j));
            }

        }

        for (int x = 0; x < simbols.size(); x++) {
            String[] parts = (simbols.get(x)).split("\\|");
            String comprobar = parts[0];
            for (int j = x - 1; j > -1; j--) {
                String[] parts2 = (simbols.get(j)).split("\\|");
                String comprobar2 = parts2[0];
                if (comprobar2.contains(comprobar)) {
                    simbols.remove(j);
                    x = x - 1;
                }
            }

        }

        for (int x = 0; x < simbols.size(); x++) {
            String[] parts = (simbols.get(x)).split("\\|");
            String valide;
            String nombre = parts[0].replaceAll(" ", "");
            String tipo = parts[1].replaceAll(" ", "");
            String valatr;

            String repe = parts[4].replaceAll(" ", "");
            if (tipo.contains("Identificador")) {
                valide = parts[3].replaceAll(" ", "");
                valatr = "0";
            } else {
                valide = parts[0].replaceAll(" ", "");
                valatr = parts[0].replaceAll(" ", "");
            }
            String lineas = busqueda(nombre);
            simbols.set(x, nombre + " | " + tipo + "  |          " + valide + "         |          " + repe + "    |      "+lineas+"           |  " + valatr);
        }


       
//        for (int x = 0; x < simbols.size(); x++) { //el metodo de fabricio para imprimir
//            System.out.println(simbols.get(x));
//        }
        System.out.println("NOMBRE        TIPO         VALOR  REP  LÍNEA ATRIBUTO");
        Table t2 = new Table(simbols,6); //metodo mio pa imprimir
        
//        System.out.println("LEXEMA  |  TOKEN           |   TIPO              |VALOR|REP");

        Table t1 = new Table(tokens,5); //el arraylis de token, y el numero de columnas
        Arbol_Sintactico2 as = new Arbol_Sintactico2(t1.arrayGet(),t2.arrayGet(),t2.arrayListGet());
        //Arbol_Sintactico as = new Arbol_Sintactico(t1.arrayGet(),t2.arrayGet(),t2.arrayListGet());
        
  

//       
    }

    private String busqueda(String nombre) {
        String lineas="";
        try {
            FileReader fr = new FileReader("src/recursos/Cadena.txt");
            BufferedReader bf = new BufferedReader(fr);
            long lNumeroLineas = 0;
            String sCadena;
            

            while ((sCadena = bf.readLine()) != null) {
                String[] parts = (sCadena.split(" "));
                for (String part : parts) {
                    if (part.equals(nombre)) {
                        lineas=lineas+(lNumeroLineas+1)+",";
                    }
                }
                lNumeroLineas++;
            }
        } catch (FileNotFoundException fnfe) {
        } catch (IOException ioe) {
        }
        return lineas;

    }

}
