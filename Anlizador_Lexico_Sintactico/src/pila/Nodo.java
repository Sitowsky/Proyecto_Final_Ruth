package pila;

public class Nodo {
    
    String dato;
    Nodo siguiente;
    
    public Nodo(){
        this.siguiente = null;
    }
    @Override
    public String toString(){
        
        return dato;
    }
}
