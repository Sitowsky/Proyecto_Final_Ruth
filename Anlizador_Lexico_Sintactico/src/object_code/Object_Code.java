
package object_code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Object_Code {

    public static void main(String[] args) {
        ArrayList finales = new ArrayList();
        ArrayList variables = new ArrayList();
        ArrayList datos = new ArrayList();
        try {
            File archivo = new File("src\\recursos\\optimizado.txt");
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                datos.add(linea);
                
            }
        } catch (IOException e) {
        }

        for (Object dato : datos) {
            String[] valores = dato.toString().split(" ");
            for (int i = 0; i < valores.length; i++) {
                if ("abcdefghijklmnñopqrstuvwxyz".contains(Character.toString(valores[i].charAt(0)))) {
                    if (variables.isEmpty()) {
                        variables.add(valores[i]);
                    } else {
                        if (!variables.contains(valores[i])) {
                            variables.add(valores[i]);
                        }

                    }
                }
            }
        }

        for (int i = 0; i < variables.size(); i++) {
            if ("v".contains(Character.toString(variables.get(i).toString().charAt(0)))) {
            } else {
                finales.add(variables.get(i));
            }
        }

        //crear codigo objeto
        System.out.println(".Const\n"
                + "\n"
                + ".Data?\n"
                + "\n"
                + ".Data\n"
                + "	message DB \"Incio de programa\", 0\n"
                + "	message2 DB \"Fin de programa\", 0\n"
                + "	salto DB 10, 13, 0	\n"
                + "	input DB 10 Dup(0)");
        for (int i = 0; i < variables.size(); i++) {
            System.out.println("        " + variables.get(i) + " DWord 0");
        }

        for (int i = 0; i < finales.size(); i++) {
            System.out.println("        string" + (i + 1) + " DWord 0");
        }

        System.out.println(".Code\n"
                + "\n"
                + "start:\n"
                + "	Invoke StdOut, Addr message ;mensaje al inciar\n"
                + "	Invoke StdOut, Addr salto");
        System.out.println("");
        for (Object dato : datos) {
            String[] valores = dato.toString().split(" ");
            System.out.println("        Mov Eax, 0");
            System.out.println("        Mov Eax, " + valores[1]);
            if ("+".equals(valores[0])) {
                System.out.println("        Add Eax, " + valores[2]);
            } else if("-".equals(valores[0])) {
                System.out.println("        Sub Eax, " + valores[2]);
            } else{
                System.out.println("        Mov Edx, 0");
                System.out.println("        Mov Edx, "+ valores[2]);
                System.out.println("        Mul Edx");
            }

            System.out.println("        Mov " + valores[3] + ", Eax");
            System.out.println("");
        }

        for (int i = 0; i < finales.size(); i++) {
            System.out.println("      Invoke dwtoa, " + finales.get(i) + ", Addr string" + (i + 1));
            System.out.println("      Invoke StdOut, Addr string" + (i + 1));
            System.out.println("      Invoke StdOut, Addr salto");

        }

        System.out.println("      Invoke StdOut, Addr message2\n"
                + "      Invoke StdIn, Addr input, 10\n"
                + "\n"
                + "\n"
                + "End start");

    }

}
