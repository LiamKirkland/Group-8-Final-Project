/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ProfileController implements Initializable {

    @FXML
    private Text nameField;
    @FXML
    private Text ageField;
    @FXML
    private Text yearField;
    @FXML
    private Text majorField;
    @FXML
    private TextArea bioField;
    @FXML
    private Button editProf;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    
    @FXML
    void openEditor(ActionEvent event) throws IOException {
            
            FXMLLoader loader = new FXMLLoader (getClass().getResource("/View/EditProfile.fxml"));
            Parent editProfileView = loader.load();
            Scene profileScene = new Scene(editProfileView);
            EditProfileController editProfileController = loader.getController();
            
            
            Stage stage = new Stage();
            stage.setScene(profileScene);
            stage.show();
    }
    
}
