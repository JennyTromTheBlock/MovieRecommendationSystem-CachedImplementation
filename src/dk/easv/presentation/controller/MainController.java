package dk.easv.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public ImageView imgViewLogo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("Tv-Remote-50-Red.png");
        imgViewLogo.setImage(img);
    }

    public void handleHome(ActionEvent actionEvent) {
    }

    public void handleAllMovies(ActionEvent actionEvent) {
    }

    public void handlePopular(ActionEvent actionEvent) {
    }

    public void handleFavorites(ActionEvent actionEvent) {
    }

    public void handleAddCategory(ActionEvent actionEvent) {
    }

    public void handleAddMovie(ActionEvent actionEvent) {
    }

    public void handleEnter(KeyEvent keyEvent) {
    }

    public void handleSearch(ActionEvent actionEvent) {
    }

    public void handleSearchSettings(ActionEvent actionEvent) {
    }

    public void handleResize(ActionEvent actionEvent) {
    }

    public void handleClose(ActionEvent actionEvent) {
    }

}
