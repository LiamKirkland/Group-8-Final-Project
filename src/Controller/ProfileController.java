/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Bio;
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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
    private ImageView imageField;
    @FXML
    private Button editProf;
    @FXML
    private Button matchesButton;

    Mainuser loggedInUser;

    public ProfileController(Mainuser userLogin) {

        loggedInUser = userLogin;
    }

    @FXML
    void openEditor(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EditProfile.fxml"));
        EditProfileController editProfCtrl = new EditProfileController(loggedInUser);
        loader.setController(editProfCtrl);
        Parent editProfileView = loader.load();
        Scene profileScene = new Scene(editProfileView);

        Scene currentScene = ((Node) event.getSource()).getScene();

        Stage stage = (Stage) currentScene.getWindow();
        stage.setScene(profileScene);
        stage.show();
    }

    @FXML
    void viewMatches(ActionEvent event) throws IOException {

        try {
            Query query = manager.createNamedQuery("Mainuser.findById");
            query.setParameter("id", loggedInUser.getId());
            List<Mainuser> userList = query.getResultList();
            Mainuser mainUser = userList.get(0);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Matches.fxml"));

            MatchesController matchesCtrl = new MatchesController(mainUser);
            loader.setController(matchesCtrl);
            Parent matchesView = loader.load();
            Scene matchesScene = new Scene(matchesView);

            Scene currentScene = ((Node) event.getSource()).getScene();

            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(matchesScene);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Matching Err");
            alert.setHeaderText("Please edit your profile and add interests");
            alert.setContentText("Socian cannot match you with anyone if you do not choose interests. Please edit your profile and add some!");
            alert.showAndWait();
        }
    }

    EntityManager manager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = (EntityManager) Persistence.createEntityManagerFactory("Group_8_Final_ProjectPU").createEntityManager();

        Query query = manager.createNamedQuery("Mainuser.findById");
        query.setParameter("id", loggedInUser.getId());
        List<Mainuser> userList = query.getResultList();
        Mainuser mainUser = userList.get(0);
        Query bioQuery = manager.createNamedQuery("Bio.findById");
        bioQuery.setParameter("id", loggedInUser.getId());
        List<Bio> bioList = bioQuery.getResultList();
        Bio mainBio = bioList.get(0);
        
        nameField.setText(mainUser.getName());
        ageField.setText(String.valueOf(mainUser.getAge()));
        yearField.setText(mainUser.getStudentyear());
        majorField.setText(mainUser.getMajor());
        bioField.setText(mainBio.getContents());
        String imageName = "/resources/images/" + mainUser.getProfpic();
        
        Image pfp;
        try {
            pfp = new Image(getClass().getResourceAsStream(imageName));
        } catch (Exception e) {
            pfp = new Image(getClass().getResourceAsStream("/resources/images/default.png"));
        }
        imageField.setImage(pfp);

    }

    public void reloadData() {
        manager = (EntityManager) Persistence.createEntityManagerFactory("Group_8_Final_ProjectPU").createEntityManager();

        Query query = manager.createNamedQuery("Mainuser.findById");
        query.setParameter("id", loggedInUser.getId());
        List<Mainuser> userList = query.getResultList();
        Mainuser mainUser = userList.get(0);
        Query bioQuery = manager.createNamedQuery("Bio.findById");
        bioQuery.setParameter("id", loggedInUser.getId());
        List<Bio> bioList = bioQuery.getResultList();
        Bio mainBio = bioList.get(0);

        nameField.setText(mainUser.getName());
        ageField.setText(String.valueOf(mainUser.getAge()));
        yearField.setText(mainUser.getStudentyear());
        majorField.setText(mainUser.getMajor());
        bioField.setText(mainBio.getContents());
        String imageName = "/resources/images/" + mainUser.getProfpic();
        Image pfp;
        try {
            pfp = new Image(getClass().getResourceAsStream(imageName));
        } catch (NullPointerException e) {
            pfp = new Image(getClass().getResourceAsStream("/resources/images/default.jpg"));
        }
        imageField.setImage(pfp);
        System.out.println("NAME: " + nameField.getText());
    }

}
