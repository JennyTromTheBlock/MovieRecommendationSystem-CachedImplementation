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
import javafx.scene.control.Label;
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
import java.text.DecimalFormat;
import java.util.Random;
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
    private HBox hBoxBottom;
    @FXML
    private TilePane tilePaneRight;
    @FXML
    private Label labelLeft, labelRight, labelMovieTitle;
    @FXML
    private ImageView image, iconLeft, iconRight;
    @FXML
    private HBox hBoxFavorites, hBoxPopular, hBoxRecommended;
    @FXML
    private Button btnSearch;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView ivMenu, ivAccount;
    @FXML
    private VBox mainViewSidebar, homeView, infoView;
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

    private Random random = new Random();
    private DecimalFormat df = new DecimalFormat("#.##");
    private Image iconHeart = new Image("/iconHeart.png");
    private Image iconStar = new Image("/iconStar.png");

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

    private void openMovieInfo(Movie movie) {
        //Setopacity 1 og ryk til front fra mainview

        contentArea.setDisable(true);
        contentArea.setOpacity(0.2);

        infoView.setDisable(false);
        infoView.setOpacity(1);

        //TODO Load in correct info from selected movie
        loadMovieInfo(movie);
        System.out.println(movie.getTitle());
    }

    private void loadMovieInfo(Movie movie) {
        Image imagePoster = new Image("/poster" + (1 + random.nextInt(9)) + ".jpg");
        image.setImage(imagePoster); //Movie Poster image

        iconLeft.setImage(iconHeart); //Icon for personal rating
        labelLeft.setText(""); //Label for personal rating

        iconRight.setImage(iconStar); //Icon for average rating
        labelRight.setText(df.format(movie.getAverageRating())); //Label for average rating

        labelMovieTitle.setText(movie.getTitle()); //Label for movie title

        int count = 0;
        for(TopMovie topMovie : recommended) {
            count++;
            if(count>10) { break; }
            Movie simMovie = topMovie.getMovie();
            VBox movieCard = loadMovieCard(simMovie);
            hBoxBottom.getChildren().add(movieCard);
        }

        count = 0;
        for(UserSimilarity user : similarUsers) {
            if (count>10) break;
            count++;
            VBox userCard = loadUserCard(user);
            tilePaneRight.getChildren().add(userCard);
        }
    }

    public void handleCloseInfo(ActionEvent actionEvent) {
        infoView.setDisable(true);
        infoView.setOpacity(0);

        contentArea.setDisable(false);
        contentArea.setOpacity(1);

        hBoxBottom.getChildren().clear();
        tilePaneRight.getChildren().clear();
    }

    public void handleUsers() {
        handleMenu();
        clearContentArea();
        contentArea.setContent(flowPane);

        int count = 0;
        for(UserSimilarity user : similarUsers) {
            if (count>27) break;
            count++;
            VBox userCard = loadUserCard(user);
            flowPane.getChildren().add(userCard);
        }
    }

    private VBox loadUserCard(UserSimilarity user) {
        VBox userCard;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/presentation/view/Card.fxml"));
            userCard = loader.load();
            cardController = loader.getController();
            cardController.setUserContent(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        userCard.setOnMouseClicked(event -> openUserInfo(user));
        return userCard;
    }

    private void openUserInfo(UserSimilarity simUser) {
        //Setopacity 1 og ryk til front fra mainview

        contentArea.setDisable(true);
        contentArea.setOpacity(0.2);

        infoView.setDisable(false);
        infoView.setOpacity(1);

        //TODO Load in correct info from selected movie
        loadUserInfo(simUser);
        System.out.println(simUser.getName());
    }

    private void loadUserInfo(UserSimilarity user) {
        Image avatar = new Image("/" + random.nextInt(50) + ".png");
        image.setImage(avatar); //User image

        iconLeft.setImage(iconStar); //Icon for total no. of ratings
        labelLeft.setText(String.valueOf(user.getUser().getRatingsSize())); //Label for total no. of ratings

        //iconRight.setImage(); //Icon for similarity
        labelRight.setText(user.getSimilarityPercent()); //Label for similarity

        labelMovieTitle.setText(user.getName()); //Label for user name

        int count = 0;
        for(TopMovie topMovie : recommended) {
            count++;
            if(count>10) { break; }
            Movie simMovie = topMovie.getMovie();
            VBox movieCard = loadMovieCard(simMovie);
            hBoxBottom.getChildren().add(movieCard);
        }

        count = 0;
        for(UserSimilarity simUser : similarUsers) {
            if (count>10) break;
            count++;
            VBox userCard = loadUserCard(simUser);
            tilePaneRight.getChildren().add(userCard);
        }
    }


    private void clearContentArea() {

        flowPane.getChildren().clear();
        bpCenter.getChildren().get(bpCenter.getChildren().indexOf(contentArea)).setOpacity(1);
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