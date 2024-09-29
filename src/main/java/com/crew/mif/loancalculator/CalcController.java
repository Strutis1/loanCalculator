package com.crew.mif.loancalculator;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class CalcController {

    @FXML
    private RadioButton anuitetoButton;

    @FXML
    private AnchorPane base;

    @FXML
    private Button calculateButton;

    @FXML
    private Button clearButton;

    @FXML
    private LineChart<?, ?> graph;

    @FXML
    private TextField interestRate;

    @FXML
    private RadioButton linijinisButton;

    @FXML
    private TextField loanAmount;

    @FXML
    private ToggleGroup paymentMethodGroup;

    @FXML
    private CheckBox showGraphs;

    @FXML
    private TextField timeMonths;

    @FXML
    private TextField timeYears;




    private void handleClear(ActionEvent event) {
        loanAmount.clear();
        timeYears.clear();
        timeMonths.clear();
        anuitetoButton.setSelected(false);
        linijinisButton.setSelected(false);
        showGraphs.setSelected(false);
    }

    public void initialize() {
        clearButton.setOnAction(this::handleClear);
        calculateButton.setOnAction(this::checkInput);
    }

    private void checkInput(ActionEvent actionEvent) {
        if (isDouble(loanAmount, loanAmount.getText()) &&
                isInt(timeMonths, timeMonths.getText()) &&
                isInt(timeYears, timeYears.getText()) &&
                (anuitetoButton.isSelected() || linijinisButton.isSelected())) {

            handleCalculate();
        }
    }

    private void handleCalculate() {
        try {

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numbers for loan amount, years, months, and interest rate.");
        }
    }







    private boolean isInt(TextField textField, String text) {
        try {
            int amount = Integer.parseInt(text);
            textField.setStyle("-fx-border-color: black");  // Reset style if valid
            return true;
        } catch (NumberFormatException e) {
            textField.setStyle("-fx-border-color: red");  // Indicate error with red text
            return false;
        }
    }

    private boolean isDouble(TextField textField, String text) {
        try {
            double amount = Double.parseDouble(text);
            textField.setStyle("-fx-border-color: black");  // Reset style if valid
            return true;
        } catch (NumberFormatException e) {
            textField.setStyle("-fx-border-color: red");  // Indicate error with red text
            return false;
        }
    }



}


