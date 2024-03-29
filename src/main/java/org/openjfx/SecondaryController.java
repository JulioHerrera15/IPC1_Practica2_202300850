package org.openjfx;

import java.io.IOException;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.shape.Circle;
import javafx.scene.layout.VBox;




public class SecondaryController implements Initializable{
    @FXML
    private ComboBox<String> startPointComboBox;

    @FXML
    private ComboBox<String> endPointComboBox;

    @FXML
    private ComboBox<String> transportComboBox;

    private Map<String, String> startPointToEndPoint;
    private Map<String, String> endPointToStartPoint;

    private TertiaryController tertiaryController;
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

    private ObservableList<Recorrido> recorridos = FXCollections.observableArrayList();

    public void setTertiaryController(TertiaryController tertiaryController) {
        this.tertiaryController = tertiaryController;
    }

    public void initData() {
        if (recorridos != null) {
            this.recorridos = DataHolder.getInstance().getRecorridos();
            Set<String> points = recorridos.stream()
                .flatMap(r -> Stream.of(r.getInicio(), r.getFin()))
                .collect(Collectors.toSet());
            
            // Limpia los ComboBox antes de agregar los datos
            startPointComboBox.getItems().clear();
            endPointComboBox.getItems().clear();

            startPointComboBox.getItems().addAll(points);
            endPointComboBox.getItems().addAll(points);
            initializeComponentesCombobox();
            

            // Configura startPointToEndPoint y endPointToStartPoint
            startPointToEndPoint = new HashMap<>();
            endPointToStartPoint = new HashMap<>();
            for (Recorrido recorrido : recorridos) {
                startPointToEndPoint.put(recorrido.getInicio(), recorrido.getFin());
                endPointToStartPoint.put(recorrido.getFin(), recorrido.getInicio());
            }
        }

        
    }    

    public void setStartPointToEndPoint(Map<String, String> startPointToEndPoint) {
        this.startPointToEndPoint = startPointToEndPoint;  
    }

    public ObservableList<Recorrido> getRecorridos() {      
        return recorridos;
    }

    public void setEndPointToStartPoint(Map<String, String> endPointToStartPoint) {
        this.endPointToStartPoint = endPointToStartPoint;
    }

    public void handleStartPointSelection(String selectedStartPoint) {
        if (startPointToEndPoint == null || endPointToStartPoint == null) {
            return;
        }
    
        String correspondingEndPoint = startPointToEndPoint.get(selectedStartPoint);
    
        // Si el punto de inicio seleccionado es en realidad un punto final, buscamos el punto de inicio original
        if (correspondingEndPoint == null) {
            correspondingEndPoint = endPointToStartPoint.get(selectedStartPoint);
        }
    
        // Actualizamos los elementos del ComboBox de punto final
        endPointComboBox.getItems().clear();
        if (correspondingEndPoint != null) {
            endPointComboBox.getItems().add(correspondingEndPoint);
        } else {
            endPointComboBox.getItems().addAll(startPointToEndPoint.values());
        }
    
        
    }

    private ObservableList<Viaje> viajes = FXCollections.observableArrayList();

    public void setRecorridos(ObservableList<Recorrido> recorridos) {
        this.recorridos = recorridos;
    }
    

    public void initializeComponentesCombobox() {
        if (transportComboBox.getItems().isEmpty()) {
            transportComboBox.getItems().addAll(
                "Motocicleta 1",
                "Motocicleta 2",
                "Motocicleta 3",
                "Vehículo estándar 1",
                "Vehículo estándar 2",
                "Vehículo estándar 3",
                "Vehículo premium 1",
                "Vehículo premium 2",
                "Vehículo premium 3"
            );
        }
    }


    @FXML
    private Button homeButton;

    @FXML
    private Button plusButton;

    @FXML
    private Button generarNuevoViaje;

    @FXML
    private Label pilotosOcupadosLabel;

    @FXML
    private Button carButton;

    @FXML
    private Button clockButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeHomeButton();
        initializePlusButton();
        initializeCarButton();
        initializeClockButton();
        // Carga la imagen
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("org/openjfx/perfil.png"));

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
               
        
        startPointComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            String puntoFinalAEstablecer = startPointComboBox.getValue();
            
            if(puntoFinalAEstablecer != null) {
                endPointComboBox.getItems().clear();
                endPointComboBox.getItems().addAll(recorridos.stream()
                    .filter(r -> r.getInicio().equals(puntoFinalAEstablecer))
                    .map(Recorrido::getFin)
                    .collect(Collectors.toSet())
                );
            }

            
        });

        if (startPointComboBox != null) {
            // Agrega un ChangeListener al ComboBox
            startPointComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                // Llama a handleStartPointSelection cuando se selecciona un nuevo punto de inicio
                handleStartPointSelection(newValue);
            });
        }

        if (recorridos != null){
            initData();
        }

        
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/primary.fxml"));
                Parent root = loader.load();
    
                PrimaryController primaryController = loader.getController();
                primaryController.initData(recorridos);        
    
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
                Parent root = FXMLLoader.load(getClass().getResource("/org/openjfx/secondary.fxml"));
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

                tertiaryController = loader.getController();
                tertiaryController.setViajes(viajes);                                   

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
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public double getDistancia(String puntoDeInicio, String puntoFinal) {
        for (Recorrido recorrido : recorridos) {
            if (recorrido.getInicio().equals(puntoDeInicio) && recorrido.getFin().equals(puntoFinal) || recorrido.getInicio().equals(puntoFinal) && recorrido.getFin().equals(puntoDeInicio)){
                return recorrido.getDistancia();
            }
        }
        return -1; // Devuelve -1 si no se encuentra el recorrido
    }

    @FXML
    public void generarViaje() {        
        String puntoDeInicio = startPointComboBox.getValue();
        String puntoFinal = endPointComboBox.getValue();
        String transporte = transportComboBox.getValue();
        double distancia = getDistancia(puntoDeInicio, puntoFinal);        
    
        Viaje viaje = new Viaje(null, puntoDeInicio, puntoFinal, transporte, distancia, null, null);
        viajes.add(viaje);  
        
        // Guarda los viajes en DataHolder
        DataHolder dataHolder = DataHolder.getInstance();
        dataHolder.setViajes(viajes);
    
        System.out.println("Viaje generado: " + puntoDeInicio + " -> " + puntoFinal + " (" + transporte + ")");

        if(tertiaryController != null) {              
            tertiaryController.initViajes(dataHolder.getViajes());
        }        
        

        if (viajes.size() >= 3) {
            pilotosOcupadosLabel.setText("¡Todos los pilotos están ocupados!");
            generarNuevoViaje.setDisable(true);            
        }

        startPointComboBox.setValue(null);
        endPointComboBox.setValue(null);
        transportComboBox.setValue(null);       

    }
    
}
