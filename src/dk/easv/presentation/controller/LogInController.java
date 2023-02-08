package dk.easv.presentation.controller;

import dk.easv.entities.User;
import dk.easv.presentation.model.AppModel;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    public BorderPane logInBorderPane;
    private int sliderDistance = -367;
    private boolean directionLeft;
    public Label loginSlider;
    public AnchorPane loginBackGround;
    public GridPane loginInputGridPane;
    public GridPane loginInputGridPane1;
    public ImageView loginLogo;
    public Label goToCreateAccount;
    public Label goToLogInForm;
    public TextField userId1;
    public PasswordField passwordField1;
    @FXML private PasswordField passwordField;
    @FXML private TextField userId;
    private AppModel model;

    private boolean isLoginSliderLeft = true;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image img = new Image("Tv-Remote-50-Red.png");
        loginLogo.setImage(img);
        model = new AppModel();

    }

    public void logIn(ActionEvent actionEvent) {
        model.loadUsers();
        model.loginUserFromUsername(userId.getText());
        if(model.getObsLoggedInUser()!=null){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/presentation/view/App.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Movie Recommendation System 0.01 Beta");
            stage.show();
            AppController controller = loader.getController();

            controller.setModel(model);


        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load App.fxml");
            alert.showAndWait();
        }

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong username or password");
            alert.showAndWait();
        }
    }

    public void signUp(ActionEvent actionEvent) {
        System.out.println("Sign-Up");
    }

    public void handleSliderOnPress(MouseEvent mouseEvent) {
        if(isLoginSliderLeft){
            isLoginSliderLeft = false;


        }else {
            isLoginSliderLeft = true;
            directionLeft = true;
        }
        sliderDistance = sliderDistance * -1;
        System.out.println(sliderDistance);
        toogleSignInForm();

        TranslateTransition translate = new TranslateTransition();
        translate.setNode(loginSlider);
        translate.setDuration(Duration.millis(500));
        translate.setByX(sliderDistance);
        translate.play();

    }

    /**
     * sets the buttons and inoutFileds active or disables them if createAccount is selected or not
     */
    private void toogleSignInForm() {
        if (isLoginSliderLeft) {
            //sets sign in form false
            goToCreateAccount.setDisable(false);
            userId.setDisable(false);
            passwordField.setDisable(false);
            //sets log in true
            goToLogInForm.setDisable(true);
            userId1.setDisable(true);
            passwordField1.setDisable(true);

        }else {
            goToCreateAccount.setDisable(true);
            userId.setDisable(true);
            passwordField.setDisable(true);

            goToLogInForm.setDisable(false);
            userId1.setDisable(false);
            passwordField1.setDisable(false);
        }
    }

}
