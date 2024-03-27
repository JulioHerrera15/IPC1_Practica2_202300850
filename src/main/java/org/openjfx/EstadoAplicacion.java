package org.openjfx;

import java.io.Serializable;
import java.util.List;

public class EstadoAplicacion implements Serializable {
    private String controladorActual;
    private List<Viaje> viajes;

    // Getter y setter para controladorActual
    public String getControladorActual() {
        return controladorActual;
    }

    public void setControladorActual(String controladorActual) {
        this.controladorActual = controladorActual;
    }

    // Getter y setter para viajes
    public List<Viaje> getViajes() {
        return viajes;
    }

    public void setViajes(List<Viaje> viajes) {
        this.viajes = viajes;
    }
}