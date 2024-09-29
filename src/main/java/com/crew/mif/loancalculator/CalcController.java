package com.crew.mif.loancalculator;

import calculations.Annuity;
import calculations.Linear;
import data.Mokejimas;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class CalcController {

    @FXML
    private TableView<Mokejimas> dataTable;

    @FXML
    private TableColumn<Mokejimas, Integer> menuo;

    @FXML
    private TableColumn<Mokejimas, Double> menesineImoka;

    @FXML
    private TableColumn<Mokejimas, Double> pagrindineDalis;

    @FXML
    private TableColumn<Mokejimas, Double> palukanuDalis;

    @FXML
    private TableColumn<Mokejimas, Double> likusiSuma;


    @FXML
    private RadioButton anuitetoButton;

    private Annuity annuity;

    @FXML
    private AnchorPane base;

    @FXML
    private Button calculateButton;

    @FXML
    private Button clearButton;


    @FXML
    private TextField interestRate;

    @FXML
    private RadioButton linijinisButton;

    private Linear linear;

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
        dataTable.getItems().clear();
    }

    public void initialize() {
        menuo.setCellValueFactory(new PropertyValueFactory<>("Mėnuo"));
        menesineImoka.setCellValueFactory(new PropertyValueFactory<>("Menesinė Įmoka"));
        pagrindineDalis.setCellValueFactory(new PropertyValueFactory<>("Pagrindinė dalis"));
        palukanuDalis.setCellValueFactory(new PropertyValueFactory<>("Palūkanų dalis"));
        likusiSuma.setCellValueFactory(new PropertyValueFactory<>("Likusi suma"));
        clearButton.setOnAction(this::handleClear);
        calculateButton.setOnAction(this::checkInput);
    }

    private void checkInput(ActionEvent actionEvent) {
        if (isPosDouble(loanAmount, loanAmount.getText()) &&
                isPosInt(timeMonths, timeMonths.getText()) &&
                isPosInt(timeYears, timeYears.getText()) &&
                (anuitetoButton.isSelected() || linijinisButton.isSelected())) {

            handleCalculate();
        }
    }

    private void handleCalculate() {
        try {
            double totalAmount = Double.parseDouble(loanAmount.getText());
            int totalMonths = Integer.parseInt(timeYears.getText()) * 12 + Integer.parseInt(timeMonths.getText());
            double annualInterestRate = Double.parseDouble(interestRate.getText());



            if(linijinisButton.isSelected()) {
                linear = new Linear(totalAmount, totalMonths, annualInterestRate);

                dataTable.setVisible(true);

            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numbers for loan amount, years, months, and interest rate.");
        }
    }







    private boolean isPosInt(TextField textField, String text) {
        try {
            int amount = Integer.parseInt(text);
            if (amount > 0) {
                textField.setStyle("-fx-border-color: black");
                return true;
            } else {
                textField.setStyle("-fx-border-color: red");
                return false;
            }
        } catch (NumberFormatException e) {
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }

    private boolean isPosDouble(TextField textField, String text) {
        try {
            double amount = Double.parseDouble(text);
            if (amount > 0) {
                textField.setStyle("-fx-border-color: black");
                return true;
            } else {
                textField.setStyle("-fx-border-color: red");
                return false;
            }
        } catch (NumberFormatException e) {
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }



}


