package com.crew.mif.loancalculator;

import data.Mokejimas;
import data.MokejimoLentele;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class DataController {

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
    private Button saveButton;

    @FXML
    private LineChart<Number, Number> mokejimoGrafikas;

    @FXML
    private CheckBox filterOn;

    @FXML
    private Button filterButton;

    @FXML
    private ComboBox<?> monthsFrom;

    @FXML
    private ComboBox<?> monthsTo;

    private MokejimoLentele mokejimoLentele;

    public void initialize() {
        mokejimoLentele = new MokejimoLentele(dataTable, menuo, menesineImoka, pagrindineDalis, palukanuDalis, likusiSuma);

        receiveData();
        
        saveButton.setOnAction(this::handleSave);
        filterOn.setOnAction(this::handleFilterOn);
        filterButton.setOnAction(this::handleFilter);
    }

    private void receiveData() {
        ObservableList<Mokejimas> payments = DataHolder.getInstance().getPayments();
        ObservableList<XYChart.Series<Number, Number>> chartData = DataHolder.getInstance().getChartData();

        if (payments != null) {
            dataTable.getItems().setAll(payments);
        }

        if (chartData != null) {
            mokejimoGrafikas.getData().addAll(chartData);
        }

        if(DataHolder.getInstance().graphs){
            mokejimoGrafikas.setVisible(true);
        }
    }

    private void handleFilter(ActionEvent actionEvent) {
        if(filterOn.isSelected()){

        }
    }

    private void handleFilterOn(ActionEvent actionEvent) {
        if (filterOn.isSelected()) {
            monthsFrom.setDisable(false);
            monthsTo.setDisable(false);
            monthsTo.setVisible(true);
            monthsFrom.setVisible(true);
        } else {
            monthsFrom.setDisable(true);
            monthsTo.setDisable(true);
            monthsTo.setVisible(false);
            monthsFrom.setVisible(false);
        }
    }

    private void handleSave(ActionEvent event) {
        exportDataCSV("Ataskaita.csv");
    }

    private void exportDataCSV(String fileName) {
        File file = new File(fileName);
        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            String columnNames = "Menuo,Menesine Imoka,Pagrindine dalis,Palukanu dalis,Likusi suma\n";
            writer.write(columnNames);
            for (Mokejimas mokejimas : dataTable.getItems()) {
                String text = mokejimas.getMenuo() + "," + mokejimas.getMenesineImoka() + "," +
                        mokejimas.getPagrindineDalis() + "," + mokejimas.getPalukanuDalis() + "," + mokejimas.getLikusiSuma() + "\n";
                writer.write(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
