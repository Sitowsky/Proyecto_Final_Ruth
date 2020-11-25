package optimizados;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author fabri
 */
public class Optimizados {

    ArrayList optimizar, listos;
    File archivo;
    FileReader fr;
    BufferedReader br;

    Optimizados() {
        optimizar = new ArrayList();
        listos = new ArrayList();
        archivo = null;

    }

    public static void main(String[] args) {
        Optimizados o = new Optimizados();
        o.run();

    }

    public void run() {

        optimizar(lectura());
    }

    public ArrayList lectura() {
        try {
            archivo = new File("src\\recursos\\cuad.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                optimizar.add(linea);
            }
        } catch (IOException e) {
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (IOException e2) {
            }
        }
        return optimizar;

    }

    public void optimizar(ArrayList optimiza) {
        for (Object optimizando : optimiza) {
            String analiza = optimizando.toString();
            String[] partes = analiza.split(" ");
            if (partes[0].equals("=") || partes[0].equals("*") || partes[0].equals("/")
                    || partes[0].equals("+") || partes[0].equals("-")) {

                if ("0".equals(partes[1]) || "0".equals(partes[2])) {

                } else {
                    if (!listos.isEmpty()) {
                        String[] partes2 = listos.get(listos.size() - 1).toString().split(" ");
                        if (partes[0].equals("=") && partes[2].equals(partes[3]) && partes2[3].equals(partes[1])) {
                            listos.remove(listos.size() - 1);
                            analiza = partes2[0] + " " + partes2[1] + " " + partes2[2] + " " + partes[3];
                        }
                    }
                    listos.add(analiza);
                }

            }else{
                listos.add(analiza);
            }
        }
        listos.forEach((item) -> {
            System.out.println(item);
        });

    }

}
