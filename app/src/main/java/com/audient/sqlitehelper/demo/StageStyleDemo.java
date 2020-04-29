package com.audient.sqlitehelper.demo;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageStyleDemo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Stage stage1 = new Stage(StageStyle.DECORATED);
        stage1.setTitle("stage1");
        stage1.show();

        Stage stage2 = new Stage(StageStyle.UNDECORATED);
        stage2.setTitle("stage2");
        stage2.show();

        Stage stage3 = new Stage(StageStyle.TRANSPARENT);
        stage3.setTitle("stage3");
        stage3.show();

        Stage stage4 = new Stage(StageStyle.UNIFIED);
        stage4.setTitle("stage4");
        stage4.show();

        Stage stage5 = new Stage(StageStyle.UTILITY);
        stage5.setTitle("stage5");
        stage5.show();

        // 全部退出
//        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
