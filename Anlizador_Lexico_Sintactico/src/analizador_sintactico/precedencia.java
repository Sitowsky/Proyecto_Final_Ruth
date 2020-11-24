package analizador_sintactico;

public class precedencia {

    private String simbol;
    private int value;

    precedencia(int i, String string) {
        setValue(i);
        setSimbol(string);
    }

    public int getKm() {
        return value;
    }

    public final void setValue(int value) {                                                                                   
        this.value = value;
    }
 
    public String getSimbol() {
        return simbol;
    }

    public final void setSimbol(String simbol) {
        this.simbol = simbol;
    }
}
