package com.audient.sqlitehelper.demo;

import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.stage.Stage;

public class PlatformDemo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello World");
        primaryStage.show();

        // 放入队列执行，并未开新线程，还是在UI线程，可以做些轻量级UI更新
//        Platform.runLater(() -> {
//            System.out.println("runLater");
//        });
//        System.out.println("runOnUI");

        // 关闭最后一个窗口也不会退出主程序，需要调用exit退出
//        Platform.setImplicitExit(false);

        // 系统是否支持一些特性
        System.out.println(Platform.isSupported(ConditionalFeature.SCENE3D));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
