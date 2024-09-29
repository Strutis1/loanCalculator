package com.crew.mif.loancalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalcApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(CalcApplication.class.getResource("hello-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1100, 750);

        stage.setTitle("Loan Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
