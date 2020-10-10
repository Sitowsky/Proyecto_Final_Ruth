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
    private void SubExpresion(String token) {
        switch (token) {
        
        
        
        }

    }
}
