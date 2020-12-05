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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

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

    @FXML
    private void changePicture(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog(".png");
        dialog.setTitle("Change Profile Picture");
        dialog.setHeaderText("Enter the new picture's file name:\n(Must be a png, include extension in name.)");
        dialog.setContentText("File:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            String imageName = "/resources/images/" + name;
            Image pfp;
            if ("".equals(name)) {
                pfp = new Image(getClass().getResourceAsStream("/resources/images/default.jpg"));
                imageView.setImage(pfp);
                imageView.setUserData(null);
                System.out.println("worked");
            } else {
                try {
                    pfp = new Image(getClass().getResourceAsStream(imageName));
                    imageView.setImage(pfp);
                    imageView.setUserData(name);
                } catch (NullPointerException e) {
                    pfp = new Image(getClass().getResourceAsStream("/resources/images/default.jpg"));
                    imageView.setImage(pfp);
                    imageView.setUserData("default.jpg");
                    System.out.println("no work");
                    System.out.println(name);
                }
            }
        });
    }
    Scene prevScene;

    public void setPreviousScene(Scene scene) {
        prevScene = scene;
    }

    @FXML
    void saveChanges(ActionEvent event) {

        try {
            if (nameField.getText() == null || ageField.getText() == null || yearField.getText() == null) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info Save Err");
                alert.setHeaderText("Please review input info");
                alert.setContentText("1 or more inputs are empty or invalid. Please reenter and try again.");
                alert.showAndWait();
            } else {
                Mainuser currentUser = manager.find(Mainuser.class, 1);
                Bio currentBio = manager.find(Bio.class, 1);

                if (currentUser != null && currentBio != null) {
                    manager.getTransaction().begin();
                    currentUser.setName(nameField.getText());
                    currentUser.setAge(Integer.parseInt(ageField.getText()));
                    currentUser.setStudentyear(yearField.getText());
                    currentUser.setProfpic((String) imageView.getUserData());
                    currentUser.setMajor(majorField.getText());
                    currentBio.setContents(bioField.getText());
                    manager.getTransaction().commit();
                    System.out.println("Changes made successfully.");
                    Stage stage = (Stage) saveButton.getScene().getWindow();

                    if (prevScene != null) {
                        stage.setScene(prevScene);
                    }
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info Save Err");
            alert.setHeaderText("Please review input info");
            alert.setContentText("1 or more inputs are empty or invalid. Please reenter and try again.");
            alert.showAndWait();
        }

    }

    EntityManager manager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = (EntityManager) Persistence.createEntityManagerFactory("Group_8_Final_ProjectPU").createEntityManager();

        Query query = manager.createNamedQuery("Mainuser.findById");
        query.setParameter("id", 1);
        List<Mainuser> userList = query.getResultList();
        Mainuser mainUser = userList.get(0);
        Query bioQuery = manager.createNamedQuery("Bio.findById");
        bioQuery.setParameter("id", 1);
        List<Bio> bioList = bioQuery.getResultList();
        Bio mainBio = bioList.get(0);

        nameField.setText(mainUser.getName());
        ageField.setText(String.valueOf(mainUser.getAge()));
        yearField.setText(mainUser.getStudentyear());
        majorField.setText(mainUser.getMajor());
        bioField.setText(mainBio.getContents());
        String imageName;
        if (mainUser.getProfpic() != null) {
            imageName = "/resources/images/" + mainUser.getProfpic();
            imageView.setUserData(mainUser.getProfpic());
        } else {
            imageName = "/resources/images/default.jpg";
            imageView.setUserData(null);
        }
        Image pfp = new Image(getClass().getResourceAsStream(imageName));
        imageView.setImage(pfp);
    }

}
