package analisis_lexico;
import java.util.ArrayList;

public class Table {
    String matriz[][];
    int colum_tam;
    ArrayList<String> simbols_table = new ArrayList<>();

    public Table(ArrayList<String> simbols, int columnas) {
        simbols_table = new ArrayList<>();
        int x = columnas, y = simbols.size(); //x es el numero de columnas
        matriz = new String[y][x]; //primero en filas (y) ,luego en columnas(x)
//        System.out.println("\nTamaño de la matriz , Columnas: " + matriz[0].length + ", Filas: " + matriz.length);
        separar(simbols, x); //agregara lo de simbols a la matriz
        guardarxColum(); //guarda cada elemento de forma ordenada en la tabla
        ImprimeTabla(); //imprime el nuevo arrylist de string justificado
    }

    private void separar(ArrayList<String> simbols, int x) {
//        System.out.println("guardando en la matriz..........\n");
        int colum = 0, fila = 0, filas = matriz.length;
        for (int j = 0; j < filas; j++) {
            for (int i = 0; i < x; i++) {/// el numero sera , el numero de columnas que tendra
                String[] parts = (simbols.get(fila)).split("\\|");
                matriz[fila][colum] = parts[colum].replace(" ", ""); //elimina espacios 
                colum++;
            }
            fila++;
            colum = 0;
        }
    }

    private void guardarxColum() { //guardar a simbols_table por columna
        int fila = 0, colum = 0;
        for (int i = 0; i < matriz[0].length; i++) { //columna 
            for (int j = 0; j < matriz.length; j++) { //fila
                if (colum == 0) { //si es colum =0 ya que sera el metodo add
//                    determinar_tam_colum(colum);
                    String parte = matriz[fila][colum];
                    simbols_table.add(fila, agregar_aSimbols(parte, determinar_tam_colum(colum)) + " | ");
//                    System.out.println(simbols_table.get(fila));
                } else if (colum > 0) {//si ya se ha agregado algo en esa fila, ahora con el metodo set la sustituiremos             
                    String parte = matriz[fila][colum]; //esta cadena gaurdara todo lo anterior para no perder los datos anteriores
                    String cadena = simbols_table.get(fila) + agregar_aSimbols(parte, determinar_tam_colum(colum)) + " | ";
                    simbols_table.set(fila, cadena);
//                System.out.println(simbols_table.get(fila));
                }
                fila++;
            }
            colum++;
            fila = 0;
        }
    }

    private int determinar_tam_colum(int colum) { //nos determina el numero(int) mayor de la columna
        int fila = 0, tamaño = 0;
        for (int j = 0; j < matriz.length; j++) { //checaremos terminos por columna para daterminar el tamaño maypr del elemento
//            System.out.println("a ver" +fila +","+colum);
            int tam = matriz[fila][colum].length();
            if (tam > tamaño) {
                tamaño = tam;
            }
//            System.out.println("el tamaño va "+ matriz[fila][colum]);
            fila++;
        }
        return tamaño;
    }

    private String agregar_aSimbols(String parte, int colum_tamaño) { //le agrega los espacios n
        //ecesarios para que esta justificado
        String aux = parte;
        if (parte.length() < colum_tamaño) {
            for (int i = parte.length(); i < colum_tamaño; i++) {
                aux = aux + " ";
            }
            return aux; //si el termino es menor que el termino mayor , osea standar de espacios

        } else //        System.out.println("parte"+parte);
        {
            return parte;//se refiere al termino mayor , no necesita agregarle espacios
        }
    }

    private void ImprimeTabla() { //imprime el nuevo arreglo justificado , simbols_table
        if (matriz[0].length == 6) { //nomas pa no imprimir la primera matriz, solo la segunda :v 
            for (int x = 0; x < simbols_table.size(); x++) {
                System.out.println(simbols_table.get(x));
            }
        }
    }

    String[][] arrayGet() {
        return matriz;
    }
    
    ArrayList<String> arrayListGet(){
    return simbols_table;
    }
}
