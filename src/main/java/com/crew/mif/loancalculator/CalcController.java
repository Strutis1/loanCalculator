package com.crew.mif.loancalculator;

import calculations.Annuity;
import calculations.Linear;
import data.Mokejimas;
import data.MokejimoLentele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    @FXML
    private RadioButton linijinisButton;

    @FXML
    private Button calculateButton;

    @FXML
    private Button clearButton;

    @FXML
    private TextField interestRate;

    @FXML
    private TextField loanAmount;

    @FXML
    private TextField timeMonths;

    @FXML
    private TextField timeYears;

    private MokejimoLentele mokejimoLentele;
    private Linear linear;
    private Annuity annuity;

    public void initialize() {
        mokejimoLentele = new MokejimoLentele(dataTable, menuo, menesineImoka, pagrindineDalis, palukanuDalis, likusiSuma);

        clearButton.setOnAction(this::handleClear);
        calculateButton.setOnAction(this::checkInput);
    }

    @FXML
    private void handleClear(ActionEvent event) {
        loanAmount.clear();
        timeYears.clear();
        timeMonths.clear();
        interestRate.clear();
        anuitetoButton.setSelected(false);
        linijinisButton.setSelected(false);
        mokejimoLentele.clearTable();
        mokejimoLentele.setVisible(false);
    }

    @FXML
    private void checkInput(ActionEvent actionEvent) {
        if (isPosDouble(loanAmount.getText()) &&
                isPosInt(timeMonths.getText()) &&
                isPosInt(timeYears.getText()) &&
                (anuitetoButton.isSelected() || linijinisButton.isSelected())) {
            handleCalculate();
        }
    }

    private void handleCalculate() {
        try {
            double totalAmount = Double.parseDouble(loanAmount.getText());
            int totalMonths = Integer.parseInt(timeYears.getText()) * 12 + Integer.parseInt(timeMonths.getText());
            double annualInterestRate = Double.parseDouble(interestRate.getText());

            if (linijinisButton.isSelected()) {
                linear = new Linear(totalAmount, totalMonths, annualInterestRate);
                mokejimoLentele.clearTable();

                double pagrindineDalisValue = linear.calculateMonthlyPayment();
                for (int month = 1; month <= totalMonths; month++) {
                    double palukanuDalisValue = linear.calculateInterestPayment(month);
                    double menesineImokaValue = linear.calculatePrincipalPayment(month);
                    double likusiSumaValue = linear.getRemainingAmount(month);

                    Mokejimas payment = new Mokejimas(month, menesineImokaValue, pagrindineDalisValue, palukanuDalisValue, likusiSumaValue);
                    mokejimoLentele.addPayment(payment);
                }
                dataTable.setVisible(true);
            }
            else if(anuitetoButton.isSelected()){
                annuity = new Annuity(totalAmount, totalMonths, annualInterestRate);
                mokejimoLentele.clearTable();

                double menesineImokaValue = annuity.getMonthlyPayment();
                for (int month = 1; month <= totalMonths; month++) {
                    double palukanuDalisValue = annuity.calculateInterestPayment(month);
                    double pagrindineDalisValue = annuity.calculatePrincipalPayment(month);
                    double likusiSumaValue = annuity.getRemainingAmount(month);

                    Mokejimas payment = new Mokejimas(month, menesineImokaValue, pagrindineDalisValue, palukanuDalisValue, likusiSumaValue);
                    mokejimoLentele.addPayment(payment);
                }
                dataTable.setVisible(true);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numbers for loan amount, years, months, and interest rate.");
        }
    }


    private boolean isPosInt(String text) {
        try {
            int value = Integer.parseInt(text);
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isPosDouble(String text) {
        try {
            double value = Double.parseDouble(text);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
