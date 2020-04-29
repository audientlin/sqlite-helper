package com.audient.sqlitehelper.demo;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

public class Utils extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 用系统默认浏览器打开一个链接
//        HostServices host = getHostServices();
//        host.showDocument("www.baidu.com");

        // 截图
        Robot robot = new Robot();
        BufferedImage bufferedImage = robot.createScreenCapture(new Rectangle(100, 100, 500, 500));
        WritableImage wi = SwingFXUtils.toFXImage(bufferedImage, null);
        // 保存截图到粘贴板
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putImage(wi);
        clipboard.setContent(content);
        // 保存图片到本地
        ImageIO.write(bufferedImage, "jpg", new File("C:\\Users\\ddy\\Desktop\\capture.jpg"));

        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
