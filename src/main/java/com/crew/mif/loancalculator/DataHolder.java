package com.crew.mif.loancalculator;

import data.Mokejimas;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public final class DataHolder {

    private ObservableList<Mokejimas> payments;
    private ObservableList<XYChart.Series<Number, Number>> chartData;
    private boolean graphs;
    private final static DataHolder INSTANCE = new DataHolder();

    private DataHolder() {}

    public static DataHolder getInstance() {
        return INSTANCE;
    }

    public void setPayments(ObservableList<Mokejimas> payments) {
        this.payments = payments;
    }

    public void setGraphs(boolean graphs) {
        this.graphs = graphs;
    }

    public boolean getGraphs(){ return graphs; };

    public ObservableList<Mokejimas> getPayments() {
        return this.payments;
    }

    public void setChartData(ObservableList<XYChart.Series<Number, Number>> chartData) {
        this.chartData = chartData;
    }

    public ObservableList<XYChart.Series<Number, Number>> getChartData() {
        return this.chartData;
    }
}
