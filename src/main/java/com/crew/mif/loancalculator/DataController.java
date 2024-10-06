package com.crew.mif.loancalculator;

import data.Mokejimas;
import data.MokejimoLentele;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

    private MokejimoLentele mokejimoLentele;

    public void initialize() {
        mokejimoLentele = new MokejimoLentele(dataTable, menuo, menesineImoka, pagrindineDalis, palukanuDalis, likusiSuma);
        saveButton.setOnAction(this::handleSave);
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

    public void setDataForSecondStage(ObservableList<Mokejimas> tableData, ObservableList<XYChart.Series<Number, Number>> chartData) {
        if(tableData != null) {
            dataTable.setItems(tableData);
        }
        if(chartData != null) {
            mokejimoGrafikas.getData().clear();
            mokejimoGrafikas.setVisible(true);
            mokejimoGrafikas.getData().addAll(chartData);
        }
    }
}
