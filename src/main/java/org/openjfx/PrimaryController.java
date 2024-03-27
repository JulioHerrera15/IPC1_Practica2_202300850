package org.openjfx;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import com.opencsv.CSVReader;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TableColumn;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.Initializable;
import java.net.URL;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.animation.KeyValue;




public class PrimaryController implements Initializable{
    private SecondaryController secondaryController;
    

    public void setSecondaryController(SecondaryController secondaryController) {
        this.secondaryController = secondaryController;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        File file = fileChooser.showOpenDialog(null);  
        if (file != null) {
            rutaArchivo = file.getPath();
            loadTableData(file);
        }
    }

    

    @FXML
    public TableView<Recorrido> tablaRecorridos;

    @FXML
    private TableColumn<Recorrido, String> idColumna;

    @FXML
    public ObservableList<Recorrido> recorridos;    

    @FXML
    private TableColumn<Recorrido, String> inicioColumna;

    @FXML
    private TableColumn<Recorrido, String> finColumna;

    @FXML
    private TableColumn<Recorrido, String> distanciaColumna;
    
    private String rutaArchivo;

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

    private TertiaryController tertiaryController;

    public void setTertiaryController(TertiaryController tertiaryController) {
        this.tertiaryController = tertiaryController;
    }


    @FXML
    private void loadTableData(File file) {
        recorridos = FXCollections.observableArrayList();
        Map<String, String> startPointToEndPoint = new HashMap<>();
        Map<String, String> endPointToStartPoint = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            reader.readNext(); // Ignora la primera línea

            String[] line;
            int contador = 1;
            while ((line = reader.readNext()) != null) {
                String id = String.format("%03d", contador++);
                recorridos.add(new Recorrido(id, line[0], line[1], Integer.parseInt(line[2])));
                startPointToEndPoint.put(line[0], line[1]); // Asume que el punto inicial es el primer valor y el punto final es el segundo valor
                endPointToStartPoint.put(line[1], line[0]); // Asume que el punto final es el primer valor y el punto de inicio es el segundo valor
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar los datos");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        idColumna.setCellValueFactory(new PropertyValueFactory<>("id"));
        inicioColumna.setCellValueFactory(new PropertyValueFactory<>("inicio"));
        finColumna.setCellValueFactory(new PropertyValueFactory<>("fin"));
        distanciaColumna.setCellValueFactory(new PropertyValueFactory<>("distancia"));

        // Paso 3: Establece la lista de recorridos en DataHolder
        DataHolder.getInstance().setRecorridos(recorridos);
        
        // Paso 4: Asigna la ObservableList a la tabla
        tablaRecorridos.setItems(recorridos);        

        if (secondaryController != null) {
            secondaryController.setStartPointToEndPoint(startPointToEndPoint);
            secondaryController.setEndPointToStartPoint(endPointToStartPoint); // Asegúrate de implementar este método en SecondaryController
        }
        
        System.out.println("Puntos de inicio a puntos finales: " + startPointToEndPoint);
        System.out.println("Puntos finales a puntos de inicio: " + endPointToStartPoint);
    }

    public void actualizarTabla(){
        tablaRecorridos.refresh();
    }

    

    @FXML
    private void handleEditarDistancias(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/editarDistancias.fxml"));
            Parent root = loader.load();
            EditarDistanciaController controller = loader.getController();
            controller.setRecorridosController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button homeButton;

    @FXML
    private Button plusButton;

    @FXML
    private Button carButton;

    @FXML
    private Button clockButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                Parent root = FXMLLoader.load(getClass().getResource("/org/openjfx/primary.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        });
    }

    public void initData(ObservableList<Recorrido> recorridos) {      
        SecondaryController secondaryController = new SecondaryController();
        
        this.recorridos = recorridos;
        initializeTable();

        secondaryController.setRecorridos(recorridos); // Pasa la lista recorridos al SecondaryController
    }
    
    public void initializeTable() {        
        idColumna.setCellValueFactory(new PropertyValueFactory<>("id"));
        inicioColumna.setCellValueFactory(new PropertyValueFactory<>("inicio"));
        finColumna.setCellValueFactory(new PropertyValueFactory<>("fin"));
        distanciaColumna.setCellValueFactory(new PropertyValueFactory<>("distancia"));
    
        tablaRecorridos.setItems(recorridos);
    }

    

    public void initializePlusButton() {
        plusButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/secondary.fxml"));
                Parent root = loader.load();
    
                this.secondaryController = loader.getController();           
                
               
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
                

                this.tertiaryController = loader.getController();

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
