package dk.easv.presentation.controller;

import dk.easv.entities.Movie;
import dk.easv.entities.TopMovie;
import dk.easv.entities.User;
import dk.easv.presentation.model.AppModel;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView ivMenu;
    @FXML
    private VBox mainViewSidebar;
    @FXML
    private StackPane bpCenter;
    @FXML
    private ScrollPane contentArea;

    private AppModel model;
    private FlowPane flowPane = new FlowPane();
    private ObservableList<TopMovie> recommended;
    private ObservableList<Movie> favorites;
    private ObservableList<Movie> popular;
    private boolean isMenuOpen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ivMenu.setImage(new Image("/hamburger-menu.png"));
    }

    public void setModel(AppModel model) {
        this.model = model;
        model.loadData(new User(572962, "Mikhail Yeung"));

        recommended = model.getObsTopMoviesSimilarUsers();
        popular = model.getObsTopMovieNotSeen();
        favorites = model.getObsTopMovieSeen();
    }

    public void handleMenu() {
        bpCenter.getChildren().get(bpCenter.getChildren().indexOf(mainViewSidebar)).toFront();

        TranslateTransition transition = new TranslateTransition();
        transition.setNode(mainViewSidebar);
        transition.setDuration(Duration.millis(150));

        if(!isMenuOpen) {
            isMenuOpen = true;
            transition.setToX(0);
            if (bpCenter.getChildren().size()>1) {
                bpCenter.getChildren().get(bpCenter.getChildren().indexOf(contentArea)).setOpacity(0.2);
                contentArea.setOnMouseClicked(event -> handleMenu());
            }
        } else {
            isMenuOpen = false;
            transition.setToX(-100);
            if (bpCenter.getChildren().size()>1) {
                bpCenter.getChildren().get(bpCenter.getChildren().indexOf(contentArea)).setOpacity(1);
            }
        }
        transition.play();
    }

    public void handleHome() {
        handleMenu();
    }

    public void handleRecommended() {
        handleMenu();
        clearContentArea();

        for (TopMovie topMovie : recommended) {
            VBox movieCard = new VBox();
            movieCard.getChildren().add(new Label(topMovie.getTitle()));
            movieCard.getChildren().add(new Label(String.valueOf(topMovie.getYear())));
            double avgRating = topMovie.getAverageRating();
            DecimalFormat df = new DecimalFormat("#.##");
            movieCard.getChildren().add(new Label(df.format(avgRating)));

            movieCard.setPadding(new Insets(5, 5, 5, 5));
            flowPane.getChildren().add(movieCard);
        }
    }

    public void handlePopular() {
        handleMenu();
        clearContentArea();

        int count = 0;
        for (Movie movie : popular) {
            if (count > 200) { break; }
            count++;
            VBox movieCard = new VBox();
            movieCard.getChildren().add(new Label(movie.getTitle()));
            movieCard.getChildren().add(new Label(String.valueOf(movie.getYear())));
            double avgRating = movie.getAverageRating();
            DecimalFormat df = new DecimalFormat("#.##");
            movieCard.getChildren().add(new Label(df.format(avgRating)));

            movieCard.setPadding(new Insets(5, 5, 5, 5));
            flowPane.getChildren().add(movieCard);
        }
    }

    public void handleFavorites() {
        handleMenu();
        clearContentArea();

        for (Movie movie : favorites) {
            VBox movieCard = new VBox();
            movieCard.getChildren().add(new Label(movie.getTitle()));
            movieCard.getChildren().add(new Label(String.valueOf(movie.getYear())));
            double avgRating = movie.getAverageRating();
            DecimalFormat df = new DecimalFormat("#.##");
            movieCard.getChildren().add(new Label(df.format(avgRating)));

            movieCard.setPadding(new Insets(5, 5, 5, 5));
            flowPane.getChildren().add(movieCard);
        }
    }

    public void handleUsers() {
        handleMenu();
    }

    private void clearContentArea() {

        flowPane.getChildren().clear();

        contentArea.setContent(flowPane);

        bpCenter.getChildren().get(bpCenter.getChildren().indexOf(contentArea)).setOpacity(1);

        //Binds the width of the flow pane to the size of the BorderPane Center
        flowPane.prefHeightProperty().bind(contentArea.heightProperty());
        flowPane.prefWidthProperty().bind(contentArea.widthProperty());
    }
}
