package dk.easv.presentation.controller;

import dk.easv.entities.Movie;
import dk.easv.entities.User;
import dk.easv.entities.UserSimilarity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.ResourceBundle;

public class CardController implements Initializable {
    @FXML
    private ImageView image, iconLeft, iconRight;
    @FXML
    private Label labelLeft, labelRight, labelBottom;

    private Random random = new Random();
    private DecimalFormat df = new DecimalFormat("#.##");
    private Image iconHeart = new Image("/iconHeart.png");
    private Image iconStar = new Image("/iconStar.png");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setMovieContent(Movie movie) {
        Image imagePoster = new Image("/poster" + (1 + random.nextInt(9)) + ".jpg");
        image.setImage(imagePoster); //Movie Poster image

        iconLeft.setImage(iconHeart); //Icon for personal rating
        labelLeft.setText(""); //Label for personal rating

        iconRight.setImage(iconStar); //Icon for average rating
        labelRight.setText(df.format(movie.getAverageRating())); //Label for average rating

        labelBottom.setText(movie.getTitle()); //Label for movie title
    }

    public void setUserContent(UserSimilarity user) {
        Image avatar = new Image("/" + random.nextInt(50) + ".png");
        image.setImage(avatar); //User image

        iconLeft.setImage(iconStar); //Icon for total no. of ratings
        labelLeft.setText(String.valueOf(user.getUser().getRatingsSize())); //Label for total no. of ratings

        //iconRight.setImage(); //Icon for similarity
        labelRight.setText(user.getSimilarityPercent()); //Label for similarity

        labelBottom.setText(user.getName()); //Label for movie title
    }
}
