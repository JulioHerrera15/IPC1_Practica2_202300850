package org.openjfx;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

import com.opencsv.CSVReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;


public class PrimaryController {

    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        File file = fileChooser.showOpenDialog(null);  
        if (file != null) {
            loadTableData(file);
        }

        
    }

    @FXML
    private TableView<Recorrido> tablaRecorridos;
    private ObservableList<Recorrido> recorridos;

    @FXML
    private void loadTableData(File file) {
        recorridos = FXCollections.observableArrayList();
        try {
            try (CSVReader reader = new CSVReader(new FileReader(file))) {
                String[] line;
                while ((line = reader.readNext()) != null) {
                    if (line.length < 3) {
                        System.out.println("Línea con menos de 3 columnas encontrada: " + Arrays.toString(line));
                        continue;
                    }
                    recorridos.add(new Recorrido(line[0], line[1], line[2]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Paso 4: Asigna la ObservableList a la tabla
        tablaRecorridos.setItems(recorridos);
        
    }

    
}
