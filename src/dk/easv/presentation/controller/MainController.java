package dk.easv.presentation.controller;

import dk.easv.entities.Movie;
import dk.easv.entities.TopMovie;
import dk.easv.entities.User;
import dk.easv.entities.UserSimilarity;
import dk.easv.presentation.model.AppModel;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private HBox hBoxFavorites, hBoxPopular, hBoxRecommended;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnAccount;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView ivMenu, ivAccount;
    @FXML
    private VBox mainViewSidebar, homeView;
    @FXML
    private StackPane bpCenter;
    @FXML
    private ScrollPane contentArea;
    private AppModel model;
    private User loggedInUser;
    private FlowPane flowPane = new FlowPane();
    private ObservableList<TopMovie> recommended;
    private ObservableList<Movie> favorites;
    private ObservableList<Movie> popular;
    private ObservableList<UserSimilarity> similarUsers;
    private boolean isMenuOpen;
    private CardController cardController;
    @FXML
    private ImageView imgSearch, ivLogo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ivMenu.setImage(new Image("/hamburger-menu.png"));
        imgSearch.setImage(new Image("/searchIcon.png"));
        testSearch();
        ivAccount.setImage(new Image("/9.png"));
        ivLogo.setImage(new Image("/IconLogo.png"));

        //Binds the width of the homeView to the size of the BorderPane Center
        homeView.prefWidthProperty().bind(contentArea.widthProperty());

        //Binds the width of the flow pane to the size of the BorderPane Center
        flowPane.prefHeightProperty().bind(contentArea.heightProperty());
        flowPane.prefWidthProperty().bind(contentArea.widthProperty());
    }

    public void setModel(AppModel model) {
        this.model = model;
        loggedInUser = model.getObsLoggedInUser();//
        model.loadData(loggedInUser);
        model.setObsLoggedInUser(loggedInUser);

        recommended = model.getObsTopMoviesSimilarUsers();
        popular = model.getObsTopMovieNotSeen();
        favorites = model.getObsTopMovieSeen();
        similarUsers = model.getObsSimilarUsers();

        loadHome();
    }

    public void handleMenu() {
        bpCenter.getChildren().get(bpCenter.getChildren().indexOf(mainViewSidebar)).toFront();

        TranslateTransition transition = new TranslateTransition();
        transition.setNode(mainViewSidebar);
        transition.setDuration(Duration.millis(150));

        if(!isMenuOpen) {
            isMenuOpen = true;
            transition.setToX(0);
            contentArea.setOpacity(0.2);
            EventHandler<MouseEvent> menuHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    handleMenu();
                    contentArea.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                }
            };
            contentArea.addEventHandler(MouseEvent.MOUSE_CLICKED, menuHandler);
            flowPane.setDisable(true);
        } else {
            isMenuOpen = false;
            transition.setToX(-100);
            contentArea.setOpacity(1);
            flowPane.setDisable(false);
        }
        transition.play();
    }

    public void handleHome() {
        handleMenu();
        clearContentArea();

        contentArea.setContent(homeView); //VBox HomeView?
    }

    private void loadHome() {
        //Load all recommended movies
        int count = 0;
        for (TopMovie topMovie : recommended) {
            Movie movie = topMovie.getMovie();
            count++;
            if (count>27) { break; }
            VBox movieCard = loadMovieCard(movie);
            hBoxRecommended.getChildren().add(movieCard);
        }

        //Load all popular movies
        count = 0;
        for (Movie movie : popular) {
            count++;
            if (count>27) { break; }
            VBox movieCard = loadMovieCard(movie);
            hBoxPopular.getChildren().add(movieCard);
        }

        //Load all favorite movies
        count = 0;
        for (Movie movie : favorites) {
            count++;
            if (count>27) { break; }
            VBox movieCard = loadMovieCard(movie);
            hBoxFavorites.getChildren().add(movieCard);
        }
    }

    public void handleRecommended() {
        handleMenu();
        clearContentArea();
        contentArea.setContent(flowPane);

        int count = 0;
        for (TopMovie topMovie : recommended) {
            Movie movie = topMovie.getMovie();
            count++;
            if (count>27) { break; }
            VBox movieCard = loadMovieCard(movie);
            flowPane.getChildren().add(movieCard);
        }
    }

    public void handlePopular() {
        handleMenu();
        clearContentArea();
        contentArea.setContent(flowPane);

        int count = 0;
        for (Movie movie : popular) {
            count++;
            if (count>27) { break; }
            VBox movieCard = loadMovieCard(movie);
            flowPane.getChildren().add(movieCard);
        }
    }

    public void handleFavorites() {
        handleMenu();
        clearContentArea();
        contentArea.setContent(flowPane);

        int count = 0;
        for (Movie movie : favorites) {
            count++;
            if (count>27) { break; }
            VBox movieCard = loadMovieCard(movie);
            flowPane.getChildren().add(movieCard);
        }
    }

    private VBox loadMovieCard(Movie movie) {
        VBox movieCard;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/presentation/view/Card.fxml"));
            movieCard = loader.load();
            cardController = loader.getController();
            cardController.setMovieContent(movie);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        movieCard.setOnMouseClicked(event -> openMovieInfo(movie));
        return movieCard;
    }


    public void handleUsers() {
        handleMenu();
        clearContentArea();
        contentArea.setContent(flowPane);

        int count = 0;
        for(UserSimilarity user : similarUsers) {
            if (count>27) break;
            count++;
            loadUserCard(user);
        }
    }

    private void loadUserCard(UserSimilarity user) {
        VBox userCard;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/presentation/view/Card.fxml"));
            userCard = loader.load();
            cardController = loader.getController();
            cardController.setUserContent(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        flowPane.getChildren().add(userCard);
    }

    private void openMovieInfo(Movie movie) {
        contentArea.setDisable(true);
        contentArea.setOpacity(0.2);
        Rectangle rect = new Rectangle(100, 100, 500, 300);
        bpCenter.getChildren().add(rect);

        rect.setOnMouseClicked(event -> {
            contentArea.setDisable(false);
            contentArea.setOpacity(1);
            bpCenter.getChildren().remove(rect);
        });
        System.out.println(movie.getTitle());
    }

    private void clearContentArea() {

        flowPane.getChildren().clear();
        bpCenter.getChildren().get(bpCenter.getChildren().indexOf(contentArea)).setOpacity(1);
    }


    private void testSearch(){
        btnSearch.setOnMouseClicked(event -> System.out.println("clicked"));
        btnAccount.setOnMouseClicked(event -> System.out.println("Account clicked"));
    }
}