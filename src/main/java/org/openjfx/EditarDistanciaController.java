package org.openjfx;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.Node;

public class EditarDistanciaController {
    @FXML
    private TextField idField;
    @FXML
    private TextField distanciaField;

    private PrimaryController recorridosController;

    public EditarDistanciaController() {
        
    }

    public void setRecorridosController(PrimaryController recorridosController) {
        this.recorridosController = recorridosController;
    }

    @FXML
    private void guardarCambios(ActionEvent event) {
        String id = idField.getText();
        int nuevaDistancia = Integer.parseInt(distanciaField.getText());
        boolean idEncontrado = false;

        // Buscar el recorrido con el id dado y actualizar su distancia
        for (Recorrido recorrido : recorridosController.recorridos) {
            if (recorrido.getId().equals(id)) {
                recorrido.setDistancia(nuevaDistancia);
                recorridosController.actualizarTabla();
                idEncontrado = true;
                break;
            }
        }

        if (!idEncontrado) {
            // Mostrar un mensaje de advertencia
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("El ID ingresado no existe.");
            alert.showAndWait();
        } else {
            // Cerrar la ventana                
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }

    
}
