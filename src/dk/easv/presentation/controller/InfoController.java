package dk.easv.presentation.controller;

import dk.easv.entities.Movie;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoController implements Initializable {
    public ImageView imageViewPicture;
    public Label labelTopUsersRatings;
    public Label labelMoviesLikeThis;
    public Label labelName;
    public VBox vBoxTopUsersRatings;
    public HBox hBoxMoviesLikeThis;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageViewPicture.setImage(new Image("IconMovieCover.png"));

    }
}

