package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MokejimoLentele {
    private final ObservableList<Mokejimas> tableData = FXCollections.observableArrayList();
    private final TableView<Mokejimas> dataTable;

    public MokejimoLentele(TableView<Mokejimas> dataTable,
        TableColumn<Mokejimas, Integer> menuo,
        TableColumn<Mokejimas, Double> menesineImoka,
        TableColumn<Mokejimas, Double> pagrindineDalis,
        TableColumn<Mokejimas, Double> palukanuDalis,
        TableColumn<Mokejimas, Double> likusiSuma) {
        this.dataTable = dataTable;

        menuo.setCellValueFactory(new PropertyValueFactory<>("menuo"));
        menesineImoka.setCellValueFactory(new PropertyValueFactory<>("menesineImoka"));
        pagrindineDalis.setCellValueFactory(new PropertyValueFactory<>("pagrindineDalis"));
        palukanuDalis.setCellValueFactory(new PropertyValueFactory<>("palukanuDalis"));
        likusiSuma.setCellValueFactory(new PropertyValueFactory<>("likusiSuma"));

        dataTable.setItems(tableData);
    }

    public void clearTable() {
        tableData.clear();
        dataTable.refresh();
    }

    public void setVisible(boolean visible){
        dataTable.setVisible(visible);
    }

    public void addPayment(Mokejimas mokejimas) {
        tableData.add(mokejimas);
        dataTable.refresh();
    }
}
