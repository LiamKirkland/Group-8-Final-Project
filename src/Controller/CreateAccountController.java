/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Bio;
import Model.Mainuser;
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
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Jack
 */
public class CreateAccountController implements Initializable {

    @FXML
    private TextField createAccountUsername;
    @FXML
    private TextField createAccountPassword;
    @FXML
    private Button createAccountButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField createAccountName;
    @FXML
    private TextField createAccountMajor;
    @FXML
    private TextField createAccountYear;
    @FXML
    private TextField createAccountAge;

    /**
     * Initializes the controller class.
     */
    EntityManager manager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        manager = (EntityManager) Persistence.createEntityManagerFactory("Group_8_Final_ProjectPU").createEntityManager();
    }

    @FXML
    void createAccount(ActionEvent event) {
        try {
            String username = createAccountUsername.getText();
            String password = createAccountPassword.getText();
            String name = createAccountName.getText();
            int age = Integer.parseInt(createAccountAge.getText());
            String major = createAccountMajor.getText();
            String studentYear = createAccountYear.getText();

            Mainuser account = new Mainuser();

            manager = (EntityManager) Persistence.createEntityManagerFactory("Group_8_Final_ProjectPU").createEntityManager();
            Query query = manager.createNamedQuery("Mainuser.findAll");
            List<Mainuser> result = query.getResultList();
            account.setId(result.size() + 1);
            account.setUsername(username);
            account.setPassword(password);
            account.setName(name);
            account.setAge(age);
            account.setMajor(major);
            account.setStudentyear(studentYear);

            // save this student to databse by calling Create operation        
            create(account);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
            Parent profileView = loader.load();
            LoginController loginCtrl = loader.getController();
            Scene profileScene = new Scene(profileView);

            Scene currentScene = ((Node) event.getSource()).getScene();

            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(profileScene);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Create Account Err");
            alert.setHeaderText("Please fill out you account information");
            alert.setContentText("User name and password need to be set.");
            alert.showAndWait();
            System.out.println(e);
        }
    }

    public void create(Mainuser account) {
        try {
            // begin transaction
            manager.getTransaction().begin();

            // sanity check
            if (account.getId() != null) {

                // create student
                manager.persist(account);

                // end transaction
                manager.getTransaction().commit();
                
                Bio blankBio = new Bio(account.getId());
                
                manager.getTransaction().begin();
                manager.persist(blankBio);
                manager.getTransaction().commit();

                System.out.println(account.toString() + " is created");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    Mainuser selectedModel;
    Scene previousScene;

    public void setPreviousScene(Scene scene) {
        previousScene = scene;
        backButton.setDisable(false);
    }

    @FXML
    void backButtonAction(ActionEvent event) {
        // option 1: get current stage -- from event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //  option 2: get current stage -- from backbutton        
        if (previousScene != null) {
            stage.setScene(previousScene);
        }

    }
}
