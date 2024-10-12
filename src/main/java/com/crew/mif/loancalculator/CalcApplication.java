package com.crew.mif.loancalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalcApplication extends Application {

    @Override
    public void start(Stage firstStage) throws Exception {

        FXMLLoader firstLoader = new FXMLLoader(CalcApplication.class.getResource("calculator.fxml"));
        Parent firstRoot = firstLoader.load();

        Scene firstScene = new Scene(firstRoot, 1150, 470);
        firstStage.setTitle("Loan Calculator");
        firstStage.setScene(firstScene);
        firstStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
