package com.crew.mif.loancalculator;

import calculations.*;
import data.Mokejimas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.crew.mif.loancalculator.Utility.*;

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

    @FXML
    private TextField defermentFor;

    @FXML
    private ComboBox<Integer> defermentFrom;

    @FXML
    private Label deferForText;

    @FXML
    private CheckBox defermentOn;

    public void initialize() {
        clearButton.setOnAction(this::handleClear);
        calculateButton.setOnAction(this::checkInput);
        defermentOn.setOnAction(this::handleDefer);

    }

    private void handleDefer(ActionEvent actionEvent) {
        if (defermentOn.isSelected()) {
            defermentFor.setDisable(false);
            defermentFrom.setDisable(false);
            defermentFor.setVisible(true);
            defermentFrom.setVisible(true);
            deferForText.setVisible(true);
            deferForText.setDisable(false);

            if (isPosInt(timeYears) && isPosInt(timeMonths)) {
                int totalMonths = Integer.parseInt(timeYears.getText()) * 12 + Integer.parseInt(timeMonths.getText());
                populateComboBox(totalMonths, defermentFrom);
            }
        } else {
            defermentFor.setDisable(true);
            defermentFrom.setDisable(true);
            defermentFor.setVisible(false);
            defermentFrom.setVisible(false);
            deferForText.setVisible(false);
            deferForText.setDisable(true);
        }
    }

    @FXML
    private void handleClear(ActionEvent event) {
        loanAmount.clear();
        timeYears.clear();
        timeMonths.clear();
        interestRate.clear();
        anuitetoButton.setSelected(false);
        linijinisButton.setSelected(false);
        defermentOn.setSelected(false);
        defermentFor.setDisable(true);
        defermentFrom.setDisable(true);
        defermentFor.setVisible(false);
        defermentFrom.setVisible(false);
    }

    @FXML
    private void checkInput(ActionEvent actionEvent) {
        if (isPosDouble(loanAmount) && isPosInt(timeYears) && isPosInt(timeMonths) &&
                (anuitetoButton.isSelected() || linijinisButton.isSelected()) &&
                (Integer.parseInt(timeYears.getText()) != 0 || Integer.parseInt(timeMonths.getText()) != 0)) {

            if (defermentOn.isSelected() && (defermentFrom.getItems() == null || !isPosInt(defermentFor)
                    || defermentFrom.getValue() == null || defermentFrom.getValue() < 0)) {
                return;
            }

            if (defermentOn.isSelected() && defermentFrom.getValue() != null && defermentFrom.getValue() >
                    (Integer.parseInt(timeYears.getText()) * 12 + Integer.parseInt(timeMonths.getText()) - Integer.parseInt(defermentFor.getText()))) {
                defermentFor.setStyle("-fx-border-color: red");
                return;
            }

            defermentFor.setStyle("-fx-border-color: black");

            handleCalculate();
        }
    }


    private void handleCalculate() {
        try {
            double totalAmount = Double.parseDouble(loanAmount.getText());
            int totalMonths = Integer.parseInt(timeYears.getText()) * 12 + Integer.parseInt(timeMonths.getText());
            double annualInterestRate = Double.parseDouble(interestRate.getText());
            int deferStart = (defermentOn.isSelected()) ? defermentFrom.getValue() : 0;
            int deferDuration = (defermentOn.isSelected()) ? Integer.parseInt(defermentFor.getText()) : 0;
            int deferEnd = deferStart + deferDuration - 1;


            int allMonths = totalMonths + deferDuration;

            double[] monthlyPayments = new double[allMonths];
            double[] interestPayments = new double[allMonths];
            double[] principalPayments = new double[allMonths];

            ObservableList<Mokejimas> payments = FXCollections.observableArrayList();
            ObservableList<XYChart.Series<Number, Number>> chartData = FXCollections.observableArrayList();

            Method method = MethodFactory.methodCreator(linijinisButton.isSelected(), totalAmount, totalMonths, annualInterestRate);
            if (defermentOn.isSelected()) {
                method = new DefermentDecorator(method, deferStart, deferEnd, deferDuration);
            }
            for (int month = 1; month <= allMonths; ++month) {
                double pagrindineDalisValue;
                double menesineImokaValue;
                double palukanuDalisValue;
                double likusiSumaValue;

                pagrindineDalisValue = method.calculatePrincipalPayment(month);
                menesineImokaValue = method.calculateMonthlyPayment(month);
                palukanuDalisValue = method.calculateInterestPayment(month);
                likusiSumaValue = method.getRemainingAmount(month);


                monthlyPayments[month - 1] = menesineImokaValue;
                interestPayments[month - 1] = palukanuDalisValue;
                principalPayments[month - 1] = pagrindineDalisValue;

                Mokejimas payment = new Mokejimas(month, menesineImokaValue, pagrindineDalisValue, palukanuDalisValue, likusiSumaValue);
                payments.add(payment);
            }

            if (showGraphs.isSelected()) {
                XYChart.Series<Number, Number> monthlySeries = new XYChart.Series<>();
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
            }

            closeCurrentStage();
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


            secondStage.initModality(Modality.APPLICATION_MODAL);
            secondStage.setTitle("Mokėjimų Lentelė");
            secondStage.setScene(secondScene);
            secondStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeCurrentStage() {
        Stage currentStage = (Stage) calculateButton.getScene().getWindow();
        currentStage.close();
    }

}
