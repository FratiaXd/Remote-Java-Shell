package com.shell.javafxclientapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.web.WebView;

/**
 *
 * @author fratiaxd
 */

/**
 * @brief JavaFX App class launches JavaFX client instance
 */
public class App extends Application {

    /**
    * Opens the application window 
    * @param primaryStage host window
    */
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Client");

        WebView webView = new WebView();

        webView.getEngine().load("http://localhost:2222");

        VBox vBox = new VBox(webView);
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}