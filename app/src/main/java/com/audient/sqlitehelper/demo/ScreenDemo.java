package com.audient.sqlitehelper.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ScreenDemo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Screen screen = Screen.getPrimary();

        // 屏幕范围
        Rectangle2D bounds = screen.getBounds();
        System.out.println(bounds.toString());

        // 屏幕可视范围
        Rectangle2D visualBounds = screen.getVisualBounds();
        System.out.println(visualBounds.toString());

        System.out.println("dpi=" + screen.getDpi());

        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
