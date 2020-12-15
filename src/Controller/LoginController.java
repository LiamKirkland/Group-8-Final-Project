/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Mainuser;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;


/**
 * FXML Controller class
 *
 * @author Jack
 */
public class LoginController implements Initializable {

    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private ImageView imageView;


    /**
     * Initializes the controller class.
     */
    EntityManager manager;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void loginUser(ActionEvent event) throws IOException {
        
        try{
            String username = userNameField.getText();
            String password = passwordField.getText();

            manager = (EntityManager) Persistence.createEntityManagerFactory("Group_8_Final_ProjectPU").createEntityManager();
            Query query = manager.createNamedQuery("Mainuser.findByUsername");
            query.setParameter("username", userNameField.getText());
            List<Mainuser> usernameList = query.getResultList();
            Mainuser mainUsername = usernameList.get(0);
        
            if(password.equals(mainUsername.getPassword())){
                System.out.println("Correct username and password.");
                userNameField.setText(null);
                passwordField.setText(null);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Profile.fxml"));
                System.out.println(mainUsername.getName());
                ProfileController profCtrl = new ProfileController(mainUsername);
                loader.setController(profCtrl);
                Parent editProfileView = loader.load();
                Scene profileScene = new Scene(editProfileView);

                 
                Scene currentScene = ((Node) event.getSource()).getScene();
                

                Stage stage = (Stage) currentScene.getWindow();
                stage.setScene(profileScene);
                stage.show();
            }
            else{
                System.out.println("Incorrect username or password.");
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Login Err");
            alert.setHeaderText("Please review user credentials");
            alert.setContentText("User name or parrword does not match an account.");
            alert.showAndWait();
            System.out.println(e);
        }

    }

    @FXML
    private void registerUser(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CreateAccount.fxml"));
        Parent createAccountView = loader.load();
        Scene loginScene = new Scene(createAccountView);

        CreateAccountController createAccCtrl = loader.getController();
        Scene currentScene = ((Node) event.getSource()).getScene();
        createAccCtrl.setPreviousScene(currentScene);

        Stage stage = (Stage) currentScene.getWindow();
        stage.setScene(loginScene);
        stage.show();
        
    }
    
    void readByUsername(ActionEvent event) {


        // read input from command line
        String username = userNameField.getText();

        List<Mainuser> u = readByUsername(username);
        System.out.println(u.toString());

    }
    
     public List<Mainuser> readByUsername(String usernameInput) {
        Query query = manager.createNamedQuery("Login.findByUsername");

        // setting query parameter
        query.setParameter("username", usernameInput);

        // execute query
        List<Mainuser> usernames = query.getResultList();

        return usernames;
    }
    
}
