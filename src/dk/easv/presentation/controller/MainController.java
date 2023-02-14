package dk.easv.presentation.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public ImageView imgViewLogo;
    public VBox mainViewSidebar;
    public FlowPane flow;
    public BorderPane borderPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/presentation/view/MovieCard.fxml"));
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/dk/easv/presentation/view/HomeView.fxml"));
        try {
            VBox v = loader.load();
            AnchorPane v1 = loader1.load();
            borderPane.setCenter(v1);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
