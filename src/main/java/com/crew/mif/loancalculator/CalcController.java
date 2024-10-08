package com.crew.mif.loancalculator;

import calculations.Annuity;
import calculations.Linear;
import data.Mokejimas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CalcController {

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

    @FXML
    private CheckBox showGraphs;

    public void initialize() {
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
    }

    @FXML
    private void checkInput(ActionEvent actionEvent) {
        if (isPosDouble(loanAmount) && isPosInt(timeMonths) && isPosInt(timeMonths) &&
                (anuitetoButton.isSelected() || linijinisButton.isSelected()) &&
                (Integer.parseInt(timeYears.getText()) != 0 || Integer.parseInt(timeMonths.getText()) != 0)) {
            handleCalculate();
        }

    }

    private void handleCalculate() {
        try {
            double totalAmount = Double.parseDouble(loanAmount.getText());
            int totalMonths = Integer.parseInt(timeYears.getText()) * 12 + Integer.parseInt(timeMonths.getText());
            double annualInterestRate = Double.parseDouble(interestRate.getText());



            double[] monthlyPayments = new double[totalMonths];
            double[] interestPayments = new double[totalMonths];
            double[] principalPayments = new double[totalMonths];

            ObservableList<Mokejimas> payments = FXCollections.observableArrayList();
            ObservableList<XYChart.Series<Number, Number>> chartData = FXCollections.observableArrayList();

            if (linijinisButton.isSelected()) {
                Linear linear = new Linear(totalAmount, totalMonths, annualInterestRate);
                double pagrindineDalisValue = linear.calculateMonthlyPayment();

                for (int month = 1; month <= totalMonths; month++) {
                    double menesineImokaValue = linear.calculatePrincipalPayment(month);
                    double palukanuDalisValue = linear.calculateInterestPayment(month);
                    double likusiSumaValue = linear.getRemainingAmount(month);

                    monthlyPayments[month - 1] = menesineImokaValue;
                    interestPayments[month - 1] = palukanuDalisValue;
                    principalPayments[month - 1] = pagrindineDalisValue;

                    Mokejimas payment = new Mokejimas(month, menesineImokaValue, pagrindineDalisValue, palukanuDalisValue, likusiSumaValue);
                    payments.add(payment);
                }
            } else if (anuitetoButton.isSelected()) {
                Annuity annuity = new Annuity(totalAmount, totalMonths, annualInterestRate);
                double menesineImokaValue = annuity.getMonthlyPayment();
                for (int month = 1; month <= totalMonths; month++) {
                    double pagrindineDalisValue = annuity.calculatePrincipalPayment(month);
                    double palukanuDalisValue = annuity.calculateInterestPayment(month);
                    double likusiSumaValue = annuity.getRemainingAmount(month);

                    monthlyPayments[month - 1] = menesineImokaValue;
                    interestPayments[month - 1] = palukanuDalisValue;
                    principalPayments[month - 1] = pagrindineDalisValue;

                    Mokejimas payment = new Mokejimas(month, menesineImokaValue, pagrindineDalisValue, palukanuDalisValue, likusiSumaValue);
                    payments.add(payment);
                }
            }

            if (showGraphs.isSelected()) {
            }                XYChart.Series<Number, Number> monthlySeries = new XYChart.Series<>();
            monthlySeries.setName("Menesinė įmoka");

            XYChart.Series<Number, Number> interestSeries = new XYChart.Series<>();
            interestSeries.setName("Palūkanų dalis");

            XYChart.Series<Number, Number> principalSeries = new XYChart.Series<>();
            principalSeries.setName("Pagrindinė dalis");

            for (int month = 1; month <= totalMonths; month++) {
                monthlySeries.getData().add(new XYChart.Data<>(month, monthlyPayments[month - 1]));
                interestSeries.getData().add(new XYChart.Data<>(month, interestPayments[month - 1]));
                principalSeries.getData().add(new XYChart.Data<>(month, principalPayments[month - 1]));
            }


            chartData.addAll(monthlySeries, interestSeries, principalSeries);


//          closeCurrentStage();
            openSecondStage(payments, chartData, showGraphs.isSelected());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numbers for loan amount, years, months, and interest rate.");
        }
    }

    private void sendData(ObservableList<Mokejimas> payments, ObservableList<XYChart.Series<Number, Number>> chartData, boolean graphs) {
        DataHolder holder = DataHolder.getInstance();
        holder.setPayments(payments);
        holder.setChartData(chartData);
        holder.setGraphs(graphs);
    }

    private void openSecondStage(ObservableList<Mokejimas> payments, ObservableList<XYChart.Series<Number, Number>> chartData, boolean graphs) {
        try {
            sendData(payments, chartData, graphs);

            FXMLLoader secondLoader = new FXMLLoader(getClass().getResource("mokejimoLentele.fxml"));
            Parent secondRoot = secondLoader.load();

            Stage secondStage = new Stage();
            Scene secondScene = new Scene(secondRoot, 1000, 750);

            DataController dataController = secondLoader.getController();

            secondStage.initModality(Modality.APPLICATION_MODAL);
            secondStage.setTitle("Mokėjimų Lentelė");
            secondStage.setScene(secondScene);
            secondStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    private void closeCurrentStage() {
//        Stage currentStage = (Stage) calculateButton.getScene().getWindow();
//        currentStage.close();
//    }

    private boolean isPosInt(TextField textField) {
        try {
            int value;
            if (textField.getText() == null || textField.getText().trim().isEmpty()) {
                textField.setStyle("-fx-border-color: red");
                return false;
            } else {
                value = Integer.parseInt(textField.getText());
            }

            if (value >= 0) {
                textField.setStyle("-fx-border-color: black");
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }

    private boolean isPosDouble(TextField textField) {
        try {
            double value;
            if (textField.getText() == null || textField.getText().trim().isEmpty()) {
                textField.setStyle("-fx-border-color: red");
                return false;
            } else {
                value = Double.parseDouble(textField.getText());
            }
            if (value >= 0) {
                textField.setStyle("-fx-border-color: black");
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }
}
