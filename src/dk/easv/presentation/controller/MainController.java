package dk.easv.presentation.controller;

import dk.easv.entities.Movie;
import dk.easv.entities.TopMovie;
import dk.easv.entities.User;
import dk.easv.entities.UserSimilarity;
import dk.easv.presentation.model.AppModel;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public MenuItem menuItmTitleAZ;
    public ImageView ivFilter;
    public MenuButton menuBtnSortBy;
    @FXML
    private MenuItem menuItemMyAccount, menuItemSettings, menuItemTheme, menuItemLogOut;
    @FXML
    private MenuButton menuButtonAccount;
    @FXML
    private Button btnSearch;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView ivMenu, ivAccount;
    @FXML
    private VBox mainViewSidebar;
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
    private InfoController infoController;
    @FXML
    private ImageView imgSearch, ivLogo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ivMenu.setImage(new Image("/hamburger-menu.png"));
        imgSearch.setImage(new Image("/IconSearch.png"));
        ivLogo.setImage(new Image("/IconLogo.png"));

        //Setting Account button icon and Account MenuItem icons
        ivAccount.setImage(new Image("/9.png"));
        menuItemMyAccount.setGraphic(new ImageView(new Image("IconMyAccount.png")));
        menuItemSettings.setGraphic(new ImageView(new Image("IconSettings.png")));
        menuItemTheme.setGraphic(new ImageView(new Image("IconTheme.png")));
        menuItemLogOut.setGraphic(new ImageView(new Image("IconLogOut.png")));

        //Filter menu
        ivFilter.setImage(new Image("IconFilter.png"));
        menuItmTitleAZ.setGraphic(new ImageView(new Image("IconTitleRising.png")));
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
    }

    public void handleRecommended() {
        handleMenu();
        clearContentArea();

        int count = 0;
        for (TopMovie topMovie : recommended) {
            Movie movie = topMovie.getMovie();
            count++;
            if (count>27) { break; }
            loadMoiveCard(movie);
        }
    }

    public void handlePopular() {
        handleMenu();
        clearContentArea();

        int count = 0;
        for (Movie movie : popular) {
            count++;
            if (count>27) { break; }
            loadMoiveCard(movie);
        }
    }

    public void handleFavorites() {
        handleMenu();
        clearContentArea();

        int count = 0;
        for (Movie movie : favorites) {
            count++;
            if (count>27) { break; }
            loadMoiveCard(movie);
        }
    }

    private void loadMoiveCard(Movie movie) {
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
        flowPane.getChildren().add(movieCard);
    }


    public void handleUsers() {
        handleMenu();
        clearContentArea();


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
        contentArea.setContent(flowPane);

        bpCenter.getChildren().get(bpCenter.getChildren().indexOf(contentArea)).setOpacity(1);

        //Binds the width of the flow pane to the size of the BorderPane Center
        flowPane.prefHeightProperty().bind(contentArea.heightProperty());
        flowPane.prefWidthProperty().bind(contentArea.widthProperty());
    }

    public void handleLogo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/presentation/view/InfoView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        infoController = loader.getController();
        stage.show();
    }
}