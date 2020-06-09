package analisis_lexico;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;

/**
 *
 * @author Fabricio
 */
public class Analizador_Lexico {

    String lexema;
    int token;
    int i = 0;

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
                        return (":=");
                    } else {
                        i++;
                        setI(i);
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
                            return ("program");

                        } else if ((input.charAt(i)) == 'e' && (input.charAt(i + 1)) == 'g' && (input.charAt(i + 2)) == 'i'
                                && (input.charAt(i + 3)) == 'n' && ((input.charAt(i + 4)) == '\n' || (input.charAt(i + 4)) == ' ' || (input.charAt(i + 4)) == '\t')) {
                            i = i + 5;
                            setI(i);
                            return ("begin");
                        } else if ((input.charAt(i)) == 'n' && (input.charAt(i + 1)) == 'd') {
                            i = i + 3;
                            setI(i);
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

}