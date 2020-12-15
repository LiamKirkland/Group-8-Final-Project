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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.CheckBox;
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
    private CheckBox quietCheck;

    @FXML
    private TextField yearField;

    @FXML
    private TextField ageField;

    @FXML
    private CheckBox videoGameCheck;

    @FXML
    private CheckBox drawingCheck;

    @FXML
    private CheckBox sportsCheck;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField nameField;

    @FXML
    private CheckBox tvCheck;

    @FXML
    private CheckBox computerCheck;

    @FXML
    private CheckBox animeCheck;

    @FXML
    private CheckBox readingCheck;

    @FXML
    private CheckBox exerciseCheck;

    @FXML
    private CheckBox introvertedCheck;

    @FXML
    private CheckBox outdoorsCheck;

    @FXML
    private TextField majorField;

    @FXML
    private Button changePicButton;

    @FXML
    private CheckBox talkativeCheck;

    @FXML
    private CheckBox studyingCheck;

    @FXML
    private CheckBox outgoingCheck;

    @FXML
    private Button saveButton;

    @FXML
    private CheckBox movieCheck;

    @FXML
    private CheckBox hangOutCheck;

    Mainuser loggedInUser;

    public EditProfileController(Mainuser userLogin) {
        loggedInUser = userLogin;
    }

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
                pfp = new Image(getClass().getResourceAsStream("/resources/images/default.png"));
                imageView.setImage(pfp);
                imageView.setUserData(null);
                System.out.println("worked");
            } else {
                try {
                    pfp = new Image(getClass().getResourceAsStream(imageName));
                    imageView.setImage(pfp);
                    imageView.setUserData(name);
                } catch (NullPointerException e) {
                    pfp = new Image(getClass().getResourceAsStream("/resources/images/default.png"));
                    imageView.setImage(pfp);
                    imageView.setUserData("default.png");
                    System.out.println("no work");
                    System.out.println(name);
                }
            }
        });
    }

    private String boxToString() {
        CheckBox[] bioTraits = {introvertedCheck, outgoingCheck, quietCheck, talkativeCheck,
            outdoorsCheck, exerciseCheck, sportsCheck, drawingCheck, tvCheck, animeCheck,
            videoGameCheck, computerCheck, studyingCheck, readingCheck, hangOutCheck, movieCheck};

        List<String> newBio = new ArrayList<>();
        for (int i = 0; i < bioTraits.length; i++) {
            if (bioTraits[i].isSelected()) {
                newBio.add(bioTraits[i].getText());
            }
        }

        System.out.println(String.join(", ", newBio));
        if(String.join(", ", newBio).isEmpty()){
            return null;
        }else{
            return String.join(", ", newBio);
        }
    }

    @FXML
    void saveChanges(ActionEvent event) throws IOException {
        //FXMLLoader loader = new FXMLLoader();
        //loader.load(getClass().getResource("/View/Profile.fxml").openStream());

        //ProfileController profCtrl = loader.getController();
        try {
            if (nameField.getText() == null || ageField.getText() == null || yearField.getText() == null) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info Save Err");
                alert.setHeaderText("Please review input info");
                alert.setContentText("1 or more inputs are empty or invalid. Please reenter and try again.");
                alert.showAndWait();
            } else {
                Mainuser currentUser = manager.find(Mainuser.class, loggedInUser.getId());
                Bio currentBio = manager.find(Bio.class, loggedInUser.getId());

                if (currentUser != null && currentBio != null) {
                    manager.getTransaction().begin();
                    currentUser.setName(nameField.getText());
                    currentUser.setAge(Integer.parseInt(ageField.getText()));
                    currentUser.setStudentyear(yearField.getText());
                    currentUser.setProfpic((String) imageView.getUserData());
                    currentUser.setMajor(majorField.getText());
                    currentBio.setContents(boxToString());
                    manager.getTransaction().commit();
                    System.out.println("Changes made successfully.");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Profile.fxml"));
                    ProfileController profCtrl = new ProfileController(currentUser);
                    loader.setController(profCtrl);
                    Parent profileView = loader.load();
                    Scene profileScene = new Scene(profileView);

                    
                    Scene currentScene = ((Node) event.getSource()).getScene();

                    Stage stage = (Stage) currentScene.getWindow();
                    stage.setScene(profileScene);
                    stage.show();
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
        query.setParameter("id", loggedInUser.getId());
        List<Mainuser> userList = query.getResultList();
        Mainuser mainUser = userList.get(0);

        nameField.setText(mainUser.getName());
        ageField.setText(String.valueOf(mainUser.getAge()));
        yearField.setText(mainUser.getStudentyear());
        majorField.setText(mainUser.getMajor());
        checkBioContents();

        String imageName;
        if (mainUser.getProfpic() != null) {
            imageName = "/resources/images/" + mainUser.getProfpic();
            imageView.setUserData(mainUser.getProfpic());
        } else {
            imageName = "/resources/images/default.png";
            imageView.setUserData(null);
        }
        Image pfp = new Image(getClass().getResourceAsStream(imageName));
        imageView.setImage(pfp);
    }

    private void checkBioContents() {
        manager = (EntityManager) Persistence.createEntityManagerFactory("Group_8_Final_ProjectPU").createEntityManager();
        CheckBox[] bioTraits = {introvertedCheck, outgoingCheck, quietCheck, talkativeCheck,
            outdoorsCheck, exerciseCheck, sportsCheck, drawingCheck, tvCheck, animeCheck,
            videoGameCheck, computerCheck, studyingCheck, readingCheck, hangOutCheck, movieCheck};

        Query bioQuery = manager.createNamedQuery("Bio.findById");
        bioQuery.setParameter("id", loggedInUser.getId());
        List<Bio> bioList = bioQuery.getResultList();
        Bio mainBio = bioList.get(0);

        String bio = mainBio.getContents();
        try {
            String[] bioArray = bio.split(", ");
            for (String checkedTrait : bioArray) {
                for (int i = 0; i < bioTraits.length; i++) {
                    if (checkedTrait.equals(bioTraits[i].getText())) {
                        bioTraits[i].setSelected(true);

                    }
                }

            }
        } catch (NullPointerException e) {

        }

    }
}
