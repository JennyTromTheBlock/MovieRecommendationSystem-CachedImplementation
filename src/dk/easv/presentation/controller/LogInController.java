package dk.easv.presentation.controller;

import dk.easv.entities.User;
import dk.easv.presentation.model.AppModel;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    public BorderPane logInBorderPane;
    public Button btnCloseApp;
    public Button btnLogIn;
    public Button btnSignUp;
    public ImageView profileImg;
    public Label profileLabel;
    public Button btnEditProfileImg;
    public Pane avatarListPane;
    private int sliderDistance = -390;
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
        Image userAvatar = new Image("UnknownUser.png");
        profileImg.setImage(userAvatar);



        model = new AppModel();

    }

    public void logIn(ActionEvent actionEvent) {
        model.loadUsers();
        model.loginUserFromUsername(userId.getText());
        if(model.getObsLoggedInUser()!=null){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/presentation/view/MainWindowView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add(getClass().getResource("/dk/easv/presentation/CSS/Test.css").toExternalForm());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            MainController controller = loader.getController();

            //controller.setModel(model);

            closeScene();


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
        }
        sliderDistance = sliderDistance * -1;
        toogleSignInForm();

        TranslateTransition translate = new TranslateTransition();
        translate.setNode(loginSlider);
        translate.setDuration(Duration.millis(500));
        translate.setByX(sliderDistance);
        translate.play();
    }

    /**
     * sets the buttons and inoutFields active or disables them if createAccount is selected or not
     */
    private void toogleSignInForm() {
        if (isLoginSliderLeft) {
            //sets sign in form false
            goToCreateAccount.setDisable(false);
            userId.setDisable(false);
            passwordField.setDisable(false);
            btnLogIn.setDisable(false);
            //sets log in true
            goToLogInForm.setDisable(true);
            userId1.setDisable(true);
            passwordField1.setDisable(true);
            btnSignUp.setDisable(true);
        }else {
            goToCreateAccount.setDisable(true);
            userId.setDisable(true);
            passwordField.setDisable(true);
            btnLogIn.setDisable(true);

            goToLogInForm.setDisable(false);
            userId1.setDisable(false);
            btnSignUp.setDisable(false);
            passwordField1.setDisable(false);
        }
    }

    public void handleCloseApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void closeScene(){
        Stage stage = (Stage) btnCloseApp.getScene().getWindow();
        stage.close();
    }

    public void handleChangeProfilePic(MouseEvent mouseEvent) {
    }

    public void handleOpenAvatarHBox(ActionEvent actionEvent) {

        ImageView imageView = new ImageView(new Image("UnknownUser.png"));
        ImageView imageView1 = new ImageView(new Image("UnknownUser.png"));
        ImageView imageView2 = new ImageView(new Image("UnknownUser.png"));

        FlowPane pane = new FlowPane(imageView1, imageView2, imageView);
        avatarListPane.getChildren().add(pane);


    }
}
