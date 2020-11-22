package pila;

public class Pila {

    Nodo inicio;
    Nodo fin;
    int tamaño;

    public Pila() {
        inicio = fin = null;
        tamaño = 0;
    }

    public void push(String s) { //colocar en la pila 
        Nodo nuevo = new Nodo();
        nuevo.dato = s;

        nuevo.siguiente = inicio;
        inicio = nuevo;
        tamaño++;

    }

    public void showStack() { //mostrar todo lo que hay en la pila
        Nodo actual;
        actual = inicio;
        if (inicio != null) {
            System.out.print("[");
            while (actual != null) {
                System.out.print(actual.dato);
                actual = actual.siguiente;
                if(actual != null) System.out.print(", ");
            }
            System.out.println("]");
        } else {
            System.out.print("La pila se encuentra vacía");
        }

    }
    
    public boolean empty(){ //comprobar si hay algun elemento en la pila
        if(inicio == null)
            return true;
        else return false;
    }
    
    public int size(){ //comprobar si hay algun elemento en la pila
        return tamaño;
    }

    public String peek() {//retornar el dato del ultimo elemento añadido a la pila
        Nodo actual;
        actual = inicio;
        if (inicio != null) {
            return actual.dato;
        } else {
            System.out.print("\nLa pila se encuentra vacía\n");   
        }
        return actual.dato;
    }
    
    public void pop(){//quitar el ultimo elemento añadido a la pila    
        Nodo actual;
        actual = inicio;     
        inicio = actual.siguiente;
        actual.siguiente = null;
        tamaño--;      
    }
}
