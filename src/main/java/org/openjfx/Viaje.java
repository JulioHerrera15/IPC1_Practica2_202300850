package org.openjfx;

public class Viaje extends Recorrido {
    private String id;
    private String transporte;
    private double capacidadTanque;
    private double gastoGasolina;
    private String fechaInicio;
    private String fechaFin;

    public Viaje(String id, String inicio, String fin, String transporte, double distancia, String fechaInicio, String fechaFin) {
        super(null, inicio, fin, distancia);
        this.id = id;
        this.transporte = transporte;
        this.capacidadTanque = 0;
        this.gastoGasolina = 0;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        setTransporte(transporte);
    }

    public String getTransporte() {
        return transporte;
    }

    public void setTransporte(String transporte) {
        this.transporte = transporte;
        switch (transporte) {
            case "Motocicleta 1":
            case "Motocicleta 2":
            case "Motocicleta 3":
                this.capacidadTanque = 6;
                this.gastoGasolina = 0.1;
                break;
            case "Vehículo estándar 1":
            case "Vehículo estándar 2":
            case "Vehículo estándar 3":
                this.capacidadTanque = 10;
                this.gastoGasolina = 0.3;
                break;
            case "Vehículo premium 1":
            case "Vehículo premium 2":
            case "Vehículo premium 3":
                this.capacidadTanque = 12;
                this.gastoGasolina = 0.45;
                break;
            default:
                throw new IllegalArgumentException("Transporte no válido: " + transporte);
        }
    }

    public double getCapacidadTanque() {
        return capacidadTanque;
    }

    public void setCapacidadTanque(double capacidadTanque) {
        this.capacidadTanque = capacidadTanque;
    }

    public double getGastoGasolina() {
        return gastoGasolina;
    }

    public void setGastoGasolina(double gastoGasolina) {
        this.gastoGasolina = gastoGasolina;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String toString (){
        return "Viaje{" +
                "inicio=" + getInicio() +
                ", fin=" + getFin() +
                ", transporte=" + getTransporte() +
                ", distancia=" + getDistancia() +
                ", capacidadTanque=" + getCapacidadTanque() +
                ", gastoGasolina=" + getGastoGasolina() +
                '}';
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    

    
    
    
}
