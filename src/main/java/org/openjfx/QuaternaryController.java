package org.openjfx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.shape.Circle;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;


public class QuaternaryController implements Initializable{
    @FXML
    private TableView<Viaje> viajesTableView;
    @FXML
    private TableColumn<Viaje, String> idColumn;
    @FXML
    private TableColumn<Viaje, String> fechaInicioColumn;
    @FXML
    private TableColumn<Viaje, String> fechaFinColumn;
    @FXML
    private TableColumn<Viaje, Double> distanciaColumn;
    @FXML
    private TableColumn<Viaje, String> transporteColumn;
    @FXML
    private TableColumn<Viaje, Double> gasolinaColumn;
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

    public void setViajeData(List<String> fechasInicio, List<String> fechasFin, ArrayList<String> transportes,ArrayList<Double> distancias, double[] totalGasolinaUtilizada) {
        ObservableList<Viaje> viajesData = FXCollections.observableArrayList();
        for (int i = 0; i < fechasInicio.size(); i++) {
            String id = String.format("%03d", i + 1); // Genera un ID que empieza desde 001
            Viaje viaje = new Viaje(id, "Inicio", "Fin", transportes.get(i), distancias.get(i), fechasInicio.get(i), fechasFin.get(i));
            viaje.setGastoGasolina(totalGasolinaUtilizada[i]);
            viajesData.add(viaje);
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fechaInicioColumn.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        fechaFinColumn.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        distanciaColumn.setCellValueFactory(new PropertyValueFactory<>("distancia"));
        transporteColumn.setCellValueFactory(new PropertyValueFactory<>("transporte"));
        gasolinaColumn.setCellValueFactory(new PropertyValueFactory<>("gastoGasolina"));
        viajesTableView.setItems(viajesData);
        
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
    
                //PrimaryController primaryController = loader.getController();
                //primaryController.initData(recorridos); // Devuelve la lista recorridos al PrimaryController
    
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
}
