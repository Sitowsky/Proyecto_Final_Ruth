package analizador_sintactico;

/**
 *
 * @author chuy_
 *
 * Metodología: Paso 1. Para cada expresión realizar la validación de tipos
 * hasta que sea el fin de archivo. Paso 2. Validar si los tipos de cada
 * operando son iguales o compatibles. Paso 3. Si son iguales asignar el tipo a
 * la sentencia que generó y continuar con la validación de tipos. Paso 4. En
 * caso contrario deberá de marcar un error de tipo semántico y terminar el
 * análisis.
 *
 */
public class Arbol_Sintactico {

    public Arbol_Sintactico(String[][]tokens ,String[][]simbols) {
        SubExpresion(tokens);
//        System.out.println("alo mada faker"+arrayGet[0][0]);
    }

    //el paso 1 lo realiza el analizador lexico
    //para el paso 2 necesitamos saber cuantas subexpresiones son  R. SEPARADO POR PARENTESIS , SIGNOS 
    //Es necesario la jerarquia de las operaciones 
    /*  1.parentesis
     2.potencias y raices
     3.multiplciaciones y diviciones 
     4.sumas y restas 
     */
    //metodos necesarios 
    //1.analizar la jerarquia , y separar en expresiones 
    //
    public void SubExpresion(String[][]tokens) {
        String expresion="";
        int desde=0 ;
        for (int fila = 0; fila < tokens.length; fila++) {
           
            if (tokens[fila][1].equals("Identificador")) {
                expresion = tokens[fila][0]; //columna 0
                desde=fila;
                fila++;
                
                if (tokens[fila][0].equals(":=")) {
                    expresion = expresion + tokens[fila][0];
                    fila++;
                    
                    while (!tokens[fila][0].equals(";") ) {
                        expresion = expresion + tokens[fila][0];
                        fila++;
                    }
                    System.out.println("su expresion aritmetica es: "+ expresion);
                    Validacion(expresion,desde, fila);

                }
            }
        }

    }

    private void Validacion(String expresion, int desde , int hasta) {
        
        numSubExpr(expresion);
    
    }

    private void numSubExpr(String expresion) {
        String[] e =expresion.split("\\=");
        System.out.println("la expresion del split -----"+e[1]);
        
        
        
    }
}
