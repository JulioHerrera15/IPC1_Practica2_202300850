package org.openjfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataHolder {
    private static DataHolder instance;

    private ObservableList<Recorrido> recorridos;
    private ObservableList<Viaje> viajes = FXCollections.observableArrayList();

    private DataHolder() {
        recorridos = FXCollections.observableArrayList();
    }

    public static DataHolder getInstance() {
        if (instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    public ObservableList<Recorrido> getRecorridos() {
        return recorridos;
    }

    public void setRecorridos(ObservableList<Recorrido> recorridos) {
        this.recorridos = recorridos;
    }

    public ObservableList<Viaje> getViajes() {
        return viajes;
    }

    public void setViajes(ObservableList<Viaje> viajes) {
        this.viajes = viajes;
    }
}
