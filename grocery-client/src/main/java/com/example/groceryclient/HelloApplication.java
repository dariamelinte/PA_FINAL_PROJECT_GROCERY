package com.example.groceryclient;

import com.example.groceryclient.utils.ApplicationUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends ApplicationUtils {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Rectangle2D screenSizes = getScreenSizes();
        Scene scene = new Scene(fxmlLoader.load(), screenSizes.getWidth(), screenSizes.getHeight());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}