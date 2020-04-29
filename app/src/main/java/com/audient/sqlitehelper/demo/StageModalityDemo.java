package com.audient.sqlitehelper.demo;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StageModalityDemo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Stage stage1 = new Stage();
        stage1.setTitle("stage1");
        stage1.show();

        // 只能关闭2之后，才能操作1
        Stage stage2 = new Stage();
        stage2.setTitle("stage2");
        stage2.initModality(Modality.WINDOW_MODAL);
        stage2.initOwner(stage1);
        stage2.show();

        // 只能关闭3之后，才能操作其它窗口
//        Stage stage3 = new Stage();
//        stage3.setTitle("stage3");
//        stage3.initModality(Modality.APPLICATION_MODAL);
//        stage3.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
