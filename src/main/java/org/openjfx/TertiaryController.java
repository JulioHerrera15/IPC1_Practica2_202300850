package org.openjfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.shape.Circle;
import javafx.animation.Timeline;






public class TertiaryController implements Initializable{
    @FXML
    private HBox viaje1;

    @FXML
    private HBox viaje2;

    @FXML
    private HBox viaje3;

    @FXML
    private Circle circle;
    @FXML
    private AnchorPane sidebar;
    @FXML
    private Button toggleButton;
    @FXML
    private Label homeLabel; 
    @FXML
    private Label plusLabel;
    @FXML
    private Label carLabel;
    @FXML
    private Label clockLabel;
    @FXML
    private Label userLabel;

    private Map<Label, Integer> labelPositions; // Mapa para guardar la posición original de cada Label
    @FXML
    private VBox vbox;

    private boolean isExpanded = true;

    @FXML
    private Button iniciarViaje1Button;

    @FXML
    private Button iniciarViaje2Button;

    @FXML
    private Button iniciarViaje3Button;

    @FXML
    private Button iniciarTodosLosViajes;

    @FXML
    private Button regresarViaje1;

    @FXML
    private Button regresarViaje2;

    @FXML
    private Button regresarViaje3;    

    @FXML
    private Label puntoFinalLabel;

    @FXML
    private Label distanciaLabel1;

    @FXML
    private Label distanciaLabel2;

    @FXML
    private Label distanciaLabel3;

    @FXML
    private Label kmRecorridos1;

    @FXML
    private Label kmRecorridos2;

    @FXML
    private Label kmRecorridos3;
    
    @FXML
    private Label puntoInicial1;

    @FXML
    private Label puntoInicial2;

    @FXML
    private Label puntoInicial3;

    @FXML
    private Label puntoFinal1;

    @FXML
    private Label puntoFinal2;

    @FXML
    private Label puntoFinal3;

    @FXML
    private Label gasolinaLabel1;

    @FXML
    private Label gasolinaLabel2;

    @FXML
    private Label gasolinaLabel3;

    @FXML
    private Button recargarGasolina1;

    @FXML
    private Button recargarGasolina2;

    @FXML
    private Button recargarGasolina3;

    private List<Viaje> viajes;
    private boolean[] fechaInicioAgregada; // Declaración a nivel de clase

    private double[] gasolinaRestante;
    
    double[] totalGasolinaUtilizada;
    private List<String> fechasInicio = new ArrayList<>();    


    @FXML
    private ObservableList<Recorrido> recorridos = FXCollections.observableArrayList();

    public ObservableList<Recorrido> getRecorridos() {      
        return recorridos;
    }

    public void setRecorridos(ObservableList<Recorrido> recorridos) {  
        this.recorridos = recorridos;
    }

    private ImageView[] imageViews = new ImageView[3];


    HashMap<Integer, String> fechasFinPorViaje = new HashMap<>();

    public void actualizarInterfazUsuario() {     
        
        Label[] gasolinLabels = {gasolinaLabel1, gasolinaLabel2, gasolinaLabel3};
        
        HBox[] viajesHBox = {viaje1, viaje2, viaje3}; // Array de HBox
    
        int i = 0; // Contador para el array de HBox
    
        for (Viaje viaje : viajes) {
            if (i >= viajesHBox.length) {
                break; // Si hay más de 3 viajes, sal del bucle
            }

            // Inicializa la gasolina restante para este viaje
            gasolinaRestante[i] = viaje.getCapacidadTanque();
    
            String tipoVehiculo = viaje.getTransporte();
            ImageView imageview = new ImageView();
            imageViews[i] = imageview;
    
            if ("Motocicleta 1".equals(tipoVehiculo) || "Motocicleta 2".equals(tipoVehiculo) || "Motocicleta 3".equals(tipoVehiculo)) {
                try (InputStream stream = new FileInputStream("src/main/resources/org/openjfx/motocicleta.png")) {
                    System.out.println("Imagen de motocicleta encontrada");
                    javafx.scene.image.Image image = new javafx.scene.image.Image(stream, 100,100, true, true);
                    imageview.setImage(image);
                } catch (IOException e) {
                    System.out.println("Imagen de motocicleta no encontrada");
                }
            } else if ("Vehículo estándar 1".equals(tipoVehiculo) || "Vehículo estándar 2".equals(tipoVehiculo) || "Vehículo estándar 3".equals(tipoVehiculo)) {
                try (InputStream stream = new FileInputStream("src/main/resources/org/openjfx/carroEstándar.png")) {
                    System.out.println("Imagen de carro estándar encontrada");
                    javafx.scene.image.Image image = new javafx.scene.image.Image(stream, 100,100, true, true);
                    imageview.setImage(image);
                } catch (IOException e) {
                    System.out.println("Imagen de carro estándar no encontrada");
                }
            } else if ("Vehículo premium 1".equals(tipoVehiculo) || "Vehículo premium 2".equals(tipoVehiculo) || "Vehículo premium 3".equals(tipoVehiculo)) {
                try (InputStream stream = new FileInputStream("src/main/resources/org/openjfx/carroPremium.png")) {
                    System.out.println("Imagen de carro premium encontrada");
                    javafx.scene.image.Image image = new javafx.scene.image.Image(stream, 100,100, true, true);
                    imageview.setImage(image);
                } catch (IOException e) {
                    System.out.println("Imagen de carro premium no encontrada");
                }
            }
            gasolinLabels[i].setText("Gasolina actual: " + gasolinaRestante[i] + " gal");                
            viajesHBox[i].getChildren().add(imageview); // Agrega la imagen al HBox correspondiente
            i++; // Incrementa el contador
            

            actualizarDistancias();
            actualizarPuntosIniciales();
            actualizarPuntosFinales();            
        }
        fechaInicioAgregada = new boolean[viajes.size()]; // Inicializa fechaInicioAgregada después de que viajes ha sido inicializado
    }

    public void initViajes(List<Viaje> viajes) {
        this.viajes = viajes;
        this.totalGasolinaUtilizada = new double[viajes.size()];
        for (int i = 0; i < viajes.size(); i++) {
            totalGasolinaUtilizada[i] = viajes.get(i).getCapacidadTanque();
        }
        gasolinaRestante = new double[viajes.size()];
        actualizarInterfazUsuario();
    }
      
    public void iniciarViaje1() {
        new Thread(() -> {
            LocalDateTime fechaInicio = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String fechaInicioFormateada = fechaInicio.format(formatter);
            iniciarViaje(imageViews[0], regresarViaje1, kmRecorridos1, viajes.get(0).getDistancia(), 0, gasolinaLabel1, viajes.get(0).getGastoGasolina(), recargarGasolina1);
            System.out.println("Iniciando viaje 1");
            System.out.println("Gasolina utilizada en viaje 1: " + totalGasolinaUtilizada[0] + " gal");
            System.out.println("Fecha de inicio: " + fechaInicioFormateada);
            fechasInicio.add(fechaInicioFormateada);
            fechaInicioAgregada[0] = true;            
        }).start();
    }
    
    @FXML
    public void iniciarViaje2() {
        new Thread(() -> {
            LocalDateTime fechaInicio = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String fechaInicioFormateada = fechaInicio.format(formatter);
            iniciarViaje(imageViews[1], regresarViaje2, kmRecorridos2, viajes.get(1).getDistancia(), 1, gasolinaLabel2, viajes.get(1).getGastoGasolina(), recargarGasolina2);
            System.out.println("Iniciando viaje 2");
            System.out.println("Gasolina utilizada en viaje 2: " + totalGasolinaUtilizada[1] + " gal");
            System.out.println("Fecha de inicio: " + fechaInicioFormateada);
            fechasInicio.add(fechaInicioFormateada);
            fechaInicioAgregada[1] = true;
        }).start();
    }
    
    @FXML
    public void iniciarViaje3() {
        new Thread(() -> {
            LocalDateTime fechaInicio = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String fechaInicioFormateada = fechaInicio.format(formatter);
            iniciarViaje(imageViews[2], regresarViaje3, kmRecorridos3, viajes.get(2).getDistancia(), 2, gasolinaLabel3, viajes.get(2).getGastoGasolina(), recargarGasolina3);
            System.out.println("Iniciando viaje 3");
            System.out.println("Gasolina utilizada en viaje 3: " + totalGasolinaUtilizada[2] + " gal");
            System.out.println("Fecha de inicio: " + fechaInicioFormateada);
            fechasInicio.add(fechaInicioFormateada);
            fechaInicioAgregada[2] = true;
        }).start();
    }

    @FXML
    public void regresarViaje1() {
        new Thread(() -> {
            if(!fechaInicioAgregada[0]) {
                LocalDateTime fechaInicio = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String fechaInicioFormateada = fechaInicio.format(formatter);                
                System.out.println("Regresando viaje 1");
                System.out.println("Gasolina utilizada viaje 1: " + totalGasolinaUtilizada[0] + " gal");
                System.out.println("Fecha de inicio: " + fechaInicioFormateada);
                fechasInicio.add(fechaInicioFormateada);
            }
            regresarImagen(imageViews[0], regresarViaje1, kmRecorridos1, viajes.get(0).getDistancia(), 0, gasolinaLabel1, viajes.get(0).getGastoGasolina(), recargarGasolina1);
            
        }).start();
    }

    @FXML
    public void regresarViaje2() {
        new Thread(() -> {
            if(!fechaInicioAgregada[1]) {
                LocalDateTime fechaInicio = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String fechaInicioFormateada = fechaInicio.format(formatter);                
                System.out.println("Regresando viaje 2");
                System.out.println("Gasolina utilizada en viaje 2: " + totalGasolinaUtilizada[1] + " gal");
                System.out.println("Fecha de inicio: " + fechaInicioFormateada);
                fechasInicio.add(fechaInicioFormateada);
            }
            regresarImagen(imageViews[1], regresarViaje2, kmRecorridos2, viajes.get(1).getDistancia(), 1, gasolinaLabel2, viajes.get(1).getGastoGasolina(), recargarGasolina2);            
        }).start();
    }

    @FXML
    public void regresarViaje3() {
        new Thread(() -> {   
            if(!fechaInicioAgregada[2]) {
                LocalDateTime fechaInicio = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String fechaInicioFormateada = fechaInicio.format(formatter);                
                System.out.println("Regresando viaje 3");
                System.out.println("Gasolina utilizada en viaje 3: " + totalGasolinaUtilizada[2] + " gal");
                System.out.println("Fecha de inicio: " + fechaInicioFormateada);
                fechasInicio.add(fechaInicioFormateada);
            }
            regresarImagen(imageViews[2], regresarViaje3, kmRecorridos3, viajes.get(2).getDistancia(), 2, gasolinaLabel3, viajes.get(2).getGastoGasolina(), recargarGasolina3);            
        }).start();
    }

    private double[] distanciaTotalRecorrida = new double[3];

    private void iniciarViaje(ImageView imageview, Button button, Label kmRecorridos, double distanciaTotal, int viajeIndex, Label gasolinaLabel, double gastoGasolina, Button botonRecargarGasolina) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(30));
        transition.setNode(imageview);
        transition.setToX(625);
        transition.setOnFinished(event -> {
            button.setDisable(false);
            imageview.setScaleX(-1);            
        });
        // Crear una Timeline para actualizar la etiqueta de km recorridos
        final double[] distanciaRecorrida = {0};        
        final Timeline[] timeline = new Timeline[1];       

        // Agregar un controlador de eventos a cada botón de Recargar Gasolina
        
        botonRecargarGasolina.setOnAction(event -> {            
            totalGasolinaUtilizada[viajeIndex] += viajes.get(viajeIndex).getCapacidadTanque() - gasolinaRestante[viajeIndex];
            totalGasolinaUtilizada[viajeIndex] = Double.parseDouble(String.format("%.2f", totalGasolinaUtilizada[viajeIndex]));
            gasolinaRestante[viajeIndex] = viajes.get(viajeIndex).getCapacidadTanque(); // Recargar gasolina
            gasolinaLabel.setText("Gasolina actual: " + String.format("%.2f", gasolinaRestante[viajeIndex]) + " gal");
            botonRecargarGasolina.setDisable(true); // Ocultar el botón de recargar gasolina
            transition.play(); // Reanudar la animación
            timeline[0].play(); // Reanudar la animación 
        });
        

        timeline[0] = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            distanciaRecorrida[0] += 0.1 * distanciaTotal / 30; // Asume que el viaje dura 10 segundos
            distanciaTotalRecorrida[viajeIndex] += 0.1 * distanciaTotal / 30;
            gasolinaRestante[viajeIndex] -= 0.1 * gastoGasolina;
            kmRecorridos.setText("Kilómetros Recorridos: " + String.format("%.2f", distanciaTotalRecorrida[viajeIndex]) + " km");
            gasolinaLabel.setText("Gasolina actual: " + String.format("%.2f", gasolinaRestante[viajeIndex]) + " gal");

            if (gasolinaRestante[viajeIndex] <= 0) {
                double gasolinaRestanteActual = gasolinaRestante[viajeIndex];
                if (gasolinaRestanteActual < 0) {
                    gasolinaRestanteActual = 0;
                }
                final double gasolinaRestanteFinal = gasolinaRestanteActual;
                Platform.runLater(() -> {
                    gasolinaLabel.setText("Gasolina actual: " + String.format("%.2f", gasolinaRestanteFinal) + " gal");
                    transition.pause();
                    timeline[0].pause(); // Pausar la animación
                    botonRecargarGasolina.setDisable(false); // Hacer visible el botón de recargar gasolina
                });
            }
            
        }));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        // Establecer un manejador de eventos para cuando la animación termine
        timeline[0].setOnFinished(event -> {
            LocalDateTime fechaFin = LocalDateTime.now();
            String fechaFinFormateada = fechaFin.format(formatter);
            System.out.println("Hora de finalización del viaje: " + fechaFinFormateada);
            fechasFinPorViaje.put(viajeIndex, fechaFinFormateada);      
            
        });
        
        timeline[0].setCycleCount(300); // Corresponde a 10 segundos
        transition.play();
        timeline[0].play();          
    }

    private void regresarImagen(ImageView imageView, Button button, Label kmRecorridos, double distanciaTotal, int viajeIndex, Label gasolinaLabel, double gastoGasolina, Button botonRecargarGasolina) {
        
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(30));
        transition.setNode(imageView);
        transition.setToX(0);
        transition.setOnFinished(event -> {
            button.setDisable(true);
            imageView.setScaleX(1);
            
        });
        // Crear una Timeline para actualizar la etiqueta de km recorridos
        final double[] distanciaRecorrida = {0};
        final Timeline[] timeline = new Timeline[1];
        

        // Agregar un controlador de eventos a cada botón de Recargar Gasolina
        
        botonRecargarGasolina.setOnAction(event -> {            
            totalGasolinaUtilizada[viajeIndex] += viajes.get(viajeIndex).getCapacidadTanque() - gasolinaRestante[viajeIndex];
            totalGasolinaUtilizada[viajeIndex] = Double.parseDouble(String.format("%.2f", totalGasolinaUtilizada[viajeIndex]));
            gasolinaRestante[viajeIndex] = viajes.get(viajeIndex).getCapacidadTanque(); // Recargar gasolina
            gasolinaLabel.setText("Gasolina actual: " + String.format("%.2f", gasolinaRestante[viajeIndex]) + " gal");
            botonRecargarGasolina.setDisable(true); // Ocultar el botón de recargar gasolina
            transition.play(); // Reanudar la animación
            timeline[0].play(); // Reanudar la animación 
        });
        
        
        timeline[0] = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            distanciaRecorrida[0] += 0.1 * distanciaTotal / 30; // Asume que el viaje dura 10 segundos
            distanciaTotalRecorrida[viajeIndex] += 0.1 * distanciaTotal / 30;
            gasolinaRestante[viajeIndex] -= 0.1 * gastoGasolina;
            kmRecorridos.setText("Kilómetros Recorridos: " + String.format("%.2f", distanciaTotalRecorrida[viajeIndex]) + " km");
            gasolinaLabel.setText("Gasolina actual: " + String.format("%.2f", gasolinaRestante[viajeIndex]) + " gal");

            if (gasolinaRestante[viajeIndex] <= 0) {
                double gasolinaRestanteActual = gasolinaRestante[viajeIndex];
                if (gasolinaRestanteActual < 0) {
                    gasolinaRestanteActual = 0;
                }
                final double gasolinaRestanteFinal = gasolinaRestanteActual;
                Platform.runLater(() -> {
                    gasolinaLabel.setText("Gasolina actual: " + String.format("%.2f", gasolinaRestanteFinal) + " gal");
                    transition.pause();
                    timeline[0].pause(); // Pausar la animación
                    botonRecargarGasolina.setDisable(false); // Hacer visible el botón de recargar gasolina
                });
            }
        }));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        // Establecer un manejador de eventos para cuando la animación termine
        timeline[0].setOnFinished(event -> {
            LocalDateTime fechaFin = LocalDateTime.now();
            String fechaFinFormateada = fechaFin.format(formatter);
            System.out.println("Hora de finalización del viaje: " + fechaFinFormateada);
            fechasFinPorViaje.put(viajeIndex, fechaFinFormateada);
        });  

        timeline[0].setCycleCount(300); // Corresponde a 10 segundos
        transition.play();
        timeline[0].play();
    }

    @FXML
    public void iniciarTodosLosViajes() {
        Button[] regresarButtons = {regresarViaje1, regresarViaje2, regresarViaje3}; 
        Label[] kmRecorridosLabels = {kmRecorridos1, kmRecorridos2, kmRecorridos3};
        Label[] gasolinaLabels = {gasolinaLabel1, gasolinaLabel2, gasolinaLabel3};
        Button[] recargarGasolinaButtons = {recargarGasolina1, recargarGasolina2, recargarGasolina3};
        if (imageViews != null && imageViews.length > 0 && 
            regresarButtons != null && regresarButtons.length > 0 && 
            kmRecorridosLabels != null && kmRecorridosLabels.length > 0 && 
            gasolinaLabels != null && gasolinaLabels.length > 0 && 
            recargarGasolinaButtons != null && recargarGasolinaButtons.length > 0 &&
            viajes != null && viajes.size() >= imageViews.length) {
                for (int i = 0; i < imageViews.length; i++) {
                    LocalDateTime fechaInicio = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String fechaInicioFormateada = fechaInicio.format(formatter);
                    fechasInicio.add(fechaInicioFormateada);
                    System.out.println("Fecha de inicio: " + fechaInicioFormateada);
                    fechaInicioAgregada[i] = true;
                    iniciarViaje(imageViews[i], regresarButtons[i], kmRecorridosLabels[i], viajes.get(i).getDistancia(), i, gasolinaLabels[i], viajes.get(i).getGastoGasolina(), recargarGasolinaButtons[i]);
                }
        }        
        
        System.out.println("Iniciando todos los viajes");
        System.out.println("Gasolina utilizada en viaje 1: " + totalGasolinaUtilizada[0] + " gal");
        System.out.println("Gasolina utilizada en viaje 2: " + totalGasolinaUtilizada[1] + " gal");
        System.out.println("Gasolina utilizada en viaje 3: " + totalGasolinaUtilizada[2] + " gal");
        
    } 
    

    public void setViajes(List<Viaje> nuevosViajes) {
        this.viajes = new ArrayList<>(nuevosViajes);
        initViajes(this.viajes);        
    }

    public void actualizarDistancias() {
        Label[] distanciaLabels = {distanciaLabel1, distanciaLabel2, distanciaLabel3};

        for (int i = 0; i < viajes.size() && i < distanciaLabels.length; i++) {
            double distancia = viajes.get(i).getDistancia();
            distanciaLabels[i].setText("Distancia: " + distancia + " km");
        }
    }
    

    public void actualizarPuntosIniciales() {
        Label[] puntosIniciales = {puntoInicial1, puntoInicial2, puntoInicial3};

        for (int i = 0; i < viajes.size() && i < puntosIniciales.length; i++) {
            String puntoInicial = viajes.get(i).getInicio();
            puntosIniciales[i].setText(puntoInicial);
        }
    }

    public void actualizarPuntosFinales() {
        Label[] puntosFinales = {puntoFinal1, puntoFinal2, puntoFinal3};

        for (int i = 0; i < viajes.size() && i < puntosFinales.length; i++) {
            String puntoFinal = viajes.get(i).getFin();
            puntosFinales[i].setText(puntoFinal);
        }
    }    

    public void guardarEstado() {
        System.out.println("Guardando estado");
        EstadoAplicacion estado = new EstadoAplicacion();
        estado.setControladorActual("TertiaryController");
    
        // Obtén los viajes de DataHolder y conviértelos a ArrayList
        DataHolder dataHolder = DataHolder.getInstance();
        List<Viaje> viajes = new ArrayList<>(dataHolder.getViajes());
    
        System.out.println("Viajes a guardar: " + viajes);
        estado.setViajes(viajes);
        System.out.println("Viajes guardados: " + estado.getViajes());
    
        try {
            FileOutputStream fileOut = new FileOutputStream("estado.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(estado);
            out.close();
            fileOut.close();
            System.out.println("Estado guardado correctamente");
        } catch (IOException i) {
            System.out.println("Error al guardar el estado");
            i.printStackTrace();
        }
    }

   
    
   

   

        @FXML
        private Button homeButton;

        @FXML
        private Button plusButton;

        @FXML
        private Button generarNuevoViaje;

        @FXML
        private Button carButton;

        @FXML
        private Button clockButton;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            System.out.println("TertiaryController inicializado");            
            initializeHomeButton();
            initializePlusButton();
            initializeCarButton();
            initializeClockButton();
            // Carga la imagen
            Image image = new Image("file:src/main/resources/org/openjfx/perfil.png");

            // Crea un ImageView con la imagen
            ImageView imageView = new ImageView(image);

            // Habilita el suavizado
            imageView.setSmooth(true);

            // Ajusta el tamaño del ImageView al tamaño deseado
            imageView.setFitWidth(circle.getRadius() * 2);
            imageView.setFitHeight(circle.getRadius() * 2);

            // Crea una nueva imagen a partir del ImageView
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            Image resizedImage = imageView.snapshot(parameters, null);

            // Usa la imagen redimensionada para rellenar el círculo
            circle.setFill(new ImagePattern(resizedImage));
            
            labelPositions = new HashMap<>(); // Inicializa el mapa
            List<Label> labels = Arrays.asList(homeLabel, plusLabel, carLabel, clockLabel, userLabel); // Lista con tus labels
            for (Label label : labels) {
                labelPositions.put(label, vbox.getChildren().indexOf(label)); // Guarda la posición original de cada Label
            }

            // Añade un EventHandler al toggleButton
            toggleButton.setOnAction(event -> {
                if (isExpanded) {
                    hide();
                } else {
                    show();
                }
                isExpanded = !isExpanded; // Cambia el estado de isExpanded
            });
            
        }      
        

        public void hide() {
            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(sidebar.prefWidthProperty(), 50); // Establece el ancho que quieras para el estado contraído
            KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
            timeline.getKeyFrames().add(kf);
            timeline.play();
            for (Node node : vbox.getChildren()) {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    if (button.getGraphic() instanceof HBox) {
                        HBox hbox = (HBox) button.getGraphic();
                        for (Node child : hbox.getChildren()) {
                            if (child instanceof Label) {
                                Label label = (Label) child;
                                FadeTransition ft = new FadeTransition(Duration.millis(500), label);
                                ft.setFromValue(1.0);
                                ft.setToValue(0.0);
                                ft.play();
                            }
                        }
                    }
                }
            }
        }

        public void show() {
            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(sidebar.prefWidthProperty(), 150); // Establece el ancho que quieras para el estado expandido
            KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
            timeline.getKeyFrames().add(kf);
            timeline.play();
            for (Node node : vbox.getChildren()) {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    if (button.getGraphic() instanceof HBox) {
                        HBox hbox = (HBox) button.getGraphic();
                        for (Node child : hbox.getChildren()) {
                            if (child instanceof Label) {
                                Label label = (Label) child;
                                FadeTransition ft = new FadeTransition(Duration.millis(500), label);
                                ft.setFromValue(0.0);
                                ft.setToValue(1.0);
                                ft.play();
                            }
                        }
                    }
                }
            }
        }
        

        public void initializeHomeButton() {
            homeButton.setOnAction(event -> {
                try {

                    recorridos = DataHolder.getInstance().getRecorridos();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/primary.fxml"));
                    Parent root = loader.load();
        
                    PrimaryController primaryController = loader.getController();
                    primaryController.initData(recorridos);

                    // Pasa la instancia de TertiaryController a PrimaryController
                    primaryController.setTertiaryController(this);
        
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }       

        public void initializePlusButton() {
            plusButton.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/secondary.fxml"));
                    Parent root = loader.load();

                    SecondaryController secondaryController = loader.getController();
                    secondaryController.setRecorridos(recorridos);

                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        public void initializeCarButton() {
            carButton.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/tertiary.fxml"));
                    Parent root = loader.load();   
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        public void initializeClockButton() {
            clockButton.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/quaternary.fxml"));
                    Parent root = loader.load();   
                    Scene scene = new Scene(root);
                    QuaternaryController quaternaryController = loader.getController();
                    ArrayList<Double> distancias = new ArrayList<>();
                    ArrayList<String> transportes = new ArrayList<>();
                    ArrayList<String> fechasInicioPrimeras = new ArrayList<>();
                    ArrayList<String> fechasFinUltimas = new ArrayList<>();
                    for (int i = 0; i < viajes.size(); i++) {
                        Viaje viaje = viajes.get(i);
                        distancias.add(viaje.getDistancia());
                        transportes.add(viaje.getTransporte());
                        fechasInicioPrimeras.add(fechasInicio.get(i)); // Asume que cada viaje tiene al menos una fecha de inicio
                        fechasFinUltimas.add(fechasFinPorViaje.get(i)); // Obtiene la fecha de finalización del viaje actual
                    }
                    quaternaryController.setViajeData(fechasInicioPrimeras, fechasFinUltimas, transportes, distancias, totalGasolinaUtilizada);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    
}