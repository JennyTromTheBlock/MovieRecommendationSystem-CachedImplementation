package dk.easv.presentation.model;

import dk.easv.entities.*;
import dk.easv.logic.LogicManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppModel {

    LogicManager logic = new LogicManager();

    private User currentUser = null;
    // Models of the data in the view
    private final ObservableList<User>  obsUsers = FXCollections.observableArrayList();
    private final ObservableList<Movie> obsTopMovieSeen = FXCollections.observableArrayList();
    private final ObservableList<Movie> obsTopMovieNotSeen = FXCollections.observableArrayList();
    private final ObservableList<UserSimilarity>  obsSimilarUsers = FXCollections.observableArrayList();
    private final ObservableList<TopMovie> obsTopMoviesSimilarUsers = FXCollections.observableArrayList();

    private final SimpleObjectProperty<User> obsLoggedInUser = new SimpleObjectProperty<>();

    public void loadUsers(){
        obsUsers.clear();
        obsUsers.addAll(logic.getAllUsers());
    }

    public void loadData(User user) {
        obsTopMovieSeen.clear();
        obsTopMovieSeen.addAll(logic.getTopAverageRatedMovies(user));

        obsTopMovieNotSeen.clear();
        obsTopMovieNotSeen.addAll(logic.getTopAverageRatedMoviesUserDidNotSee(user));

        obsSimilarUsers.clear();
        obsSimilarUsers.addAll(logic.getTopSimilarUsers(user));

        obsTopMoviesSimilarUsers.clear();
        obsTopMoviesSimilarUsers.addAll(logic.getTopMoviesFromSimilarPeople(user));
    }

    public ObservableList<User> getObsUsers() {
        return obsUsers;
    }

    public ObservableList<Movie> getObsTopMovieSeen() {
        return obsTopMovieSeen;
    }

    public ObservableList<Movie> getObsTopMovieNotSeen() {
        return obsTopMovieNotSeen;
    }

    public ObservableList<UserSimilarity> getObsSimilarUsers() {
        return obsSimilarUsers;
    }

    public ObservableList<TopMovie> getObsTopMoviesSimilarUsers() {
        return obsTopMoviesSimilarUsers;
    }

    public User getObsLoggedInUser() {
        return obsLoggedInUser.get();
    }

    public SimpleObjectProperty<User> obsLoggedInUserProperty() {
        return obsLoggedInUser;
    }

    public void setObsLoggedInUser(User obsLoggedInUser) {
        this.obsLoggedInUser.set(obsLoggedInUser);
    }

    public boolean loginUserFromUsername(String userName) {
        User u = logic.getUser(userName);
        obsLoggedInUser.set(u);
        if (u==null)
            return false;
        else
            return true;
    }

    public User loginUser(String username, String password){
        currentUser = logic.loginUser(username, password);
        return currentUser;
    }

    public User createNewUser(String username, String password){
        currentUser = logic.createUser(username, password);
        return null;
    }

    /**
     * sets the current user to null
     * should always set the login page at the same time
     */
    public void logOutUser(){
        currentUser = null;
    }

}
