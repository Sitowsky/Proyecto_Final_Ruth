package main;

import analizador_sintactico.Analizador_Sintactico;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LexicoSintactico {

    public static void main(String[] args)throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/recursos/Cadena.txt")); //leer el programa a probar
        String line;
        String cadena = "";
        while((line = br.readLine()) != null){
            cadena += line;
        }
        
        Analizador_Sintactico a = new Analizador_Sintactico(cadena);
        a.comenzar();
    }
    
}
