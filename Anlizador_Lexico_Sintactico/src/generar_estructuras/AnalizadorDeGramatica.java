package generar_estructuras;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class AnalizadorDeGramatica {

    private String[] producciones;
    private String[] derivaciones;
    private String[] terminales;
    private String[] noTerminales;

    public void analizar() {//
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/recursos/Gramatica.txt"));//Leer el archivo
            String line;
            int length = 0;
            while ((line = bf.readLine()) != null) {//Empezamos a leer hasta que se termine el archivo para saber el numero de lineas
                length++;
            }
            bf = new BufferedReader(new FileReader("src/recursos/Gramatica.txt")); //Volvemos a instanciar para que regrese el puntero al inicio
            producciones = new String[length];
            derivaciones = new String[length];//parte derecha
            String[] aux1 = new String[length];
            String[] derivantes = new String[length]; //parte izquierda
            for (int i = 0; (line = bf.readLine()) != null; i++) { //For porque ya sabemos el numero delineas que tiene el archivo
                producciones[i] = line;
                aux1[i] = line.split("->")[0];//split para poder guaradar las producciones 
                String line2 = aux1[i];
                derivantes[i] = line2.split(" ")[1];
                if (line.split("->").length > 1) {
                    derivaciones[i] = line.split("->")[1];//split para poder guaradar lo que viene despues de la derivación
                } else {
                    derivaciones[i] = "";//Si hay una derivacion en vacio(CREO)
                }
            }
            Set<String> noTerminalesSet = new LinkedHashSet<>(); //Array dinamico, nos sirve como auxiliar para no redimencionar
            noTerminalesSet.addAll(Arrays.asList(derivantes));
            int z = 0;
            noTerminales = new String[noTerminalesSet.size()];//Creamos el array estatico sabiend el tamño del arrayList
            for (String s : noTerminalesSet) {
                noTerminales[z] = s;//Pasamos la información contenida del arrayList al array estatico
                z++;
            }
            Set<String> terminalesSet = new LinkedHashSet<>(); //Array dinamico, nos sirve como auxiliar para no redimencionar
            String[] aux;
            for (String derivacione : derivaciones) {
                aux = derivacione.split(" "); //REALIZAMOS UN SPLIT PARA ELIMAR EL NUMERO DE LA PRODCCION
                for (String aux2 : aux) {
                    //asi podemos guardar y verificar que no haya repetidos
                    if (!isInArray(aux2, noTerminales)) {
                        if (aux2.isEmpty()) {
                        } else {
                            terminalesSet.add(aux2);
                        }
                    }
                }
            }
            z = 0;
            terminales = new String[terminalesSet.size()];
            for (String s : terminalesSet) {
                terminales[z] = s;//Pasamos la información contenida del arrayList al array estatico
                z++;
            }

            System.out.println("");
            System.out.println("Derivaciones(Lado derecho)");
            for (String derivacione : derivaciones) {
                System.out.println(derivacione);
            }
            System.out.println("");
            System.out.println("Terminales");
            for (String terminale : terminales) {
                System.out.println(terminale);
            }
            System.out.println("");
            System.out.println("No Terminales");
            for (String noTerminale : noTerminales) {
                System.out.println(noTerminale);
            }
            System.out.println("-.--------.---------");
            System.out.println("");

        } catch (IOException e) {
        }

    }

    private static boolean isInArray(String word, String[] array) {
        for (String array1 : array) {
            if (word.equals(array1)) {
                return true;
            }
        }
        return false;
    }

    public String[] getProducciones() {
        return producciones;
    }

    public String[] getDerivaciones() {
        return derivaciones;
    }

    public String[] getTerminales() {
        return terminales;
    }

    public String[] getNoTerminales() {
        return noTerminales;
    }
}
