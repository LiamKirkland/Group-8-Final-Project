/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LeemKirk
 */
public class EditProfileController implements Initializable {

    @FXML
    private ImageView imageView;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField majorField;
    @FXML
    private TextField bioField;
    @FXML
    private Button changePicButton;
    @FXML
    private Button saveButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void changePicture(ActionEvent event) {
    }
    
    @FXML
    void saveChanges(ActionEvent event) {

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
    
}
