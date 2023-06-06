package com.example.groceryclient.utils;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public abstract class ApplicationUtils extends Application {
    public Rectangle2D getScreenSizes () {
        Screen screen = Screen.getPrimary();
        return screen.getVisualBounds();
    }
}
