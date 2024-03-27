package org.openjfx;
import java.io.Serializable;


public class Recorrido implements Serializable{
    private String id;
    private String inicio;
    private String fin;
    private double distancia;
    

    public Recorrido(String id, String inicio, String fin, double distancia) {
        this.id = id;
        this.inicio = inicio;
        this.fin = fin;
        this.distancia = distancia;
    }

    public String getId() { return id; }
    public String getInicio() { return inicio; }
    public String getFin() { return fin; }
    public double getDistancia() { return distancia; }
    
    public void setId(String id) { this.id = id; }
    public void setInicio(String inicio) { this.inicio = inicio; }
    public void setFin(String fin) { this.fin = fin; }
    public void setDistancia(double distancia) { this.distancia = distancia; }
}
