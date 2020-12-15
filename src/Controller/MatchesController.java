package Controller;

import Model.Bio;
import Model.Mainuser;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class MatchesController {

    @FXML
    private Text ageField;

    @FXML
    private Button chatButton;

    @FXML
    private TextArea bioField;

    @FXML
    private Text majorField;

    @FXML
    private Button declineButton;

    @FXML
    private ImageView imageView;

    @FXML
    private Text nameField;

    @FXML
    private Button acceptButton;

    @FXML
    private Button homeButton;

    Mainuser mainUser;

    List<Mainuser> matchedUsers = Collections.emptyList();
    int numOfMatches = 0;
    int currentMatch = 0;

    public MatchesController(Mainuser newUser) {
        mainUser = newUser;
    }

    @FXML
    void returnHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Profile.fxml"));
        ProfileController profCtrl = new ProfileController(mainUser);
        loader.setController(profCtrl);
        Parent profileView = loader.load();
        Scene profileScene = new Scene(profileView);

        Scene currentScene = ((Node) event.getSource()).getScene();

        Stage stage = (Stage) currentScene.getWindow();
        stage.setScene(profileScene);
        stage.show();
    }

    @FXML
    void openChat(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ChatView.fxml"));
        Parent profileView = loader.load();
        Scene profileScene = new Scene(profileView);

        Scene currentScene = ((Node) event.getSource()).getScene();

        Stage stage = (Stage) currentScene.getWindow();
        stage.setScene(profileScene);
        stage.show();
    }
    

    @FXML
    void declineUser(ActionEvent event) {

        Mainuser[] matchedUserArray = matchedUsers.toArray(new Mainuser[0]);
        currentMatch++;

        if (numOfMatches > currentMatch) {

            Query bioQuery = manager.createNamedQuery("Bio.findById");
            bioQuery.setParameter("id", matchedUserArray[currentMatch].getId());
            List<Bio> bioList = bioQuery.getResultList();
            Bio mainBio = bioList.get(0);

            ageField.setText(String.valueOf(matchedUserArray[currentMatch].getAge()));
            bioField.setText(mainBio.getContents());
            majorField.setText(matchedUserArray[currentMatch].getMajor());
            nameField.setText(matchedUserArray[currentMatch].getName());
            String imageName = "/resources/images/" + matchedUserArray[currentMatch].getProfpic();
            Image pfp;
            try {
                pfp = new Image(getClass().getResourceAsStream(imageName));
            } catch (NullPointerException e) {
                pfp = new Image(getClass().getResourceAsStream("/resources/images/default.jpg"));
            }
            imageView.setImage(pfp);
            centerImage();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("End of matched list");
            alert.setHeaderText("You have run out of matches");
            alert.setContentText("");
            alert.showAndWait();
        }
    }

    @FXML
    void acceptUser(ActionEvent event) {

        Mainuser[] matchedUserArray = matchedUsers.toArray(new Mainuser[0]);
        currentMatch++;

        if (numOfMatches > currentMatch) {

            Query bioQuery = manager.createNamedQuery("Bio.findById");
            bioQuery.setParameter("id", matchedUserArray[currentMatch].getId());
            List<Bio> bioList = bioQuery.getResultList();
            Bio mainBio = bioList.get(0);

            ageField.setText(String.valueOf(matchedUserArray[currentMatch].getAge()));
            bioField.setText(mainBio.getContents());
            majorField.setText(matchedUserArray[currentMatch].getMajor());
            nameField.setText(matchedUserArray[currentMatch].getName());
            String imageName = "/resources/images/" + matchedUserArray[currentMatch].getProfpic();
            Image pfp;
            try {
                pfp = new Image(getClass().getResourceAsStream(imageName));
            } catch (NullPointerException e) {
                pfp = new Image(getClass().getResourceAsStream("/resources/images/default.jpg"));
            }
            imageView.setImage(pfp);
            centerImage();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("End of matched list");
            alert.setHeaderText("You have run out of matches");
            alert.setContentText("");
            alert.showAndWait();
        }
    }

    EntityManager manager;

    public void initialize() {

        manager = (EntityManager) Persistence.createEntityManagerFactory("Group_8_Final_ProjectPU").createEntityManager();
        matchedUsers = GetMatches();

        Query bioQuery = manager.createNamedQuery("Bio.findById");
        bioQuery.setParameter("id", matchedUsers.get(0).getId());
        List<Bio> bioList = bioQuery.getResultList();
        Bio mainBio = bioList.get(0);

        ageField.setText(String.valueOf(matchedUsers.get(0).getAge()));
        bioField.setText(mainBio.getContents());
        majorField.setText(matchedUsers.get(0).getMajor());
        nameField.setText(matchedUsers.get(0).getName());
        String imageName = "/resources/images/" + matchedUsers.get(0).getProfpic();
        Image pfp;
        try {
            pfp = new Image(getClass().getResourceAsStream(imageName));
        } catch (NullPointerException e) {
            pfp = new Image(getClass().getResourceAsStream("/resources/images/default.jpg"));
        }
        imageView.setImage(pfp);
        centerImage();
    }

    private List<Mainuser> GetMatches() {
        manager = (EntityManager) Persistence.createEntityManagerFactory("Group_8_Final_ProjectPU").createEntityManager();

        Query bioQuery = manager.createNamedQuery("Bio.findById");
        bioQuery.setParameter("id", mainUser.getId());
        List<Bio> bioList = bioQuery.getResultList();
        Bio mainBio = bioList.get(0);

        String bio = mainBio.getContents();
        String[] bioArray = bio.split(", ");

        List<Mainuser> newUserList = new LinkedList<>();
        Set<Bio> matchedBioList = new HashSet<>();
        for (int i = 0; i < bioArray.length; i++) {
            Query query = manager.createNamedQuery("Bio.findBiosContaining");
            query.setParameter("contents", "%" + bioArray[i].toLowerCase() + "%");
            matchedBioList.addAll(query.getResultList());
        }

        Iterator<Bio> bioSet = matchedBioList.iterator();
        while (bioSet.hasNext()) {

            Query userQuery = manager.createNamedQuery("Mainuser.findById");
            userQuery.setParameter("id", bioSet.next().getId());
            newUserList.addAll(userQuery.getResultList());
        }

        newUserList.remove(mainUser);

        numOfMatches = newUserList.size();
        return newUserList;
    }

    public void centerImage() {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

        }
    }
}

