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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    void openEditor(ActionEvent event) throws IOException {
            
            FXMLLoader loader = new FXMLLoader (getClass().getResource("/View/EditProfile.fxml"));
            Parent editProfileView = loader.load();
            Scene profileScene = new Scene(editProfileView);
            EditProfileController editProfileController = loader.getController();
            
            
            Stage stage = new Stage();
            stage.setScene(profileScene);
            stage.show();
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
        if(mainUser.getProfpic() != null){
            imageName = "/resources/images/" + mainUser.getProfpic();
        }else{
            imageName = "/resources/images/default.jpg";
        }
        Image pfp = new Image(getClass().getResourceAsStream(imageName));
        imageField.setImage(pfp);
    }   
    
    
    
}
