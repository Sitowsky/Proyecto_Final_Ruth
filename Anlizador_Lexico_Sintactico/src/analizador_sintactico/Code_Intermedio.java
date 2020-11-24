package analizador_sintactico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import pila.Pila;

public class Code_Intermedio {

    public void crear() throws IOException {
        Code_Intermedio n = new Code_Intermedio();
        String cadena;
        FileReader f = new FileReader("src/recursos/Expresiones.txt");
        try (BufferedReader b = new BufferedReader(f)) {
            while ((cadena = b.readLine()) != null) {
                n.crearC(cadena);
            }
        }
    }

    public void crearC(String g) throws IOException {
        String cadenita = "";
        Pila listaInt = new Pila();
        Pila variables = new Pila();
        int varia = 1;
        writeinFile("\n");
        writeinFile(g + "\n");
        String[] simbols = g.split(" ");
        String equal = simbols[simbols.length - 1];
        for (String simbol : simbols) {
            if (simbol.equals("+") || simbol.equals("-") || simbol.equals("*") || simbol.equals("/") || simbol.equals("=")) {
                if ((listaInt.size()) >= 2) {
                    cadenita = simbol + " " + listaInt.peek();
                    listaInt.pop();
                    cadenita = cadenita + " " + listaInt.peek();
                    listaInt.pop();
                    cadenita = cadenita + " v" + varia;
                    variables.push("v" + varia);
                    varia++;
                    writeinFile(cadenita + "\n");
                } else if ((listaInt.size()) == 1 && (variables.size()) >= 1) {
                    cadenita = simbol + " " + listaInt.peek();
                    listaInt.pop();
                    cadenita = cadenita + " " + variables.peek();
                    cadenita = cadenita + " " + variables.peek();
                    writeinFile(cadenita + "\n");
                } else if ((variables.size()) >= 2 && listaInt.empty()) {
                    cadenita = simbol + " " + variables.peek();
                    variables.pop();
                    cadenita = cadenita + " " + variables.peek();
                    cadenita = cadenita + " " + variables.peek();
                    writeinFile(cadenita + "\n");
                } else {
                    cadenita = simbol + " " + variables.peek();
                    variables.pop();
                    cadenita = cadenita + " " + equal;
                    cadenita = cadenita + " " + equal;
                    writeinFile(cadenita + "\n");
                }
            } else {
                listaInt.push(simbol);
            }
        }
    }

    public void writeinFile(String c) throws IOException {
        String ruta = "src/recursos/cuad.txt";
        File archivo = new File(ruta);
        try (FileWriter f2 = new FileWriter(archivo, true)) {
            f2.write(c);
        }
    }
}
