package org.openjfx;

import javafx.beans.property.SimpleStringProperty;

public class Recorrido {
    
    private final SimpleStringProperty inicio;
    private final SimpleStringProperty fin;
    private final SimpleStringProperty distancia;
    

    public Recorrido(String inicio, String fin, String distancia) {
        
        this.inicio = new SimpleStringProperty(inicio);
        this.fin = new SimpleStringProperty(fin);
        this.distancia = new SimpleStringProperty(distancia);
    }

    public String getColumn1() { return inicio.get(); }
    public String getColumn2() { return fin.get(); }
    public String getColumn3() { return distancia.get(); }
    
}
