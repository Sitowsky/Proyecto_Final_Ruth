package main;

import analizador_sintactico.Analizador_Sintactico;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LexicoSintactico {

    public static void main(String[] args)throws IOException {
        //medir tiempo
        long inicio = System.nanoTime();//variable de inicio time
        BufferedReader br = new BufferedReader(new FileReader("src/recursos/Cadena.txt")); //leer el programa a probar
        String line;
        String cadena = "";
        while((line = br.readLine()) != null){
            cadena += line;
        }
        Analizador_Sintactico a = new Analizador_Sintactico(cadena);
        a.comenzar();
        long fin = System.nanoTime();//variable de fin time
        double tiempoEjecucion = (double)(fin - inicio) * 1.0e-9; //nanosegundos a segundos , 1nanos = 1e-9s
        System.out.println("EL TIEMPO DE EJECUCION ES "+tiempoEjecucion);
    }
}
