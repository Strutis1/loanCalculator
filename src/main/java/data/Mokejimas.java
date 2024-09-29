package data;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.text.DecimalFormat;

public class Mokejimas {
    private final SimpleIntegerProperty menuo;
    private final SimpleDoubleProperty menesineImoka;
    private final SimpleDoubleProperty pagrindineDalis;
    private final SimpleDoubleProperty palukanuDalis;
    private final SimpleDoubleProperty likusiSuma;

    private final DecimalFormat decimalFormat = new DecimalFormat("#.00");

    public Mokejimas(int menuo, double menesineImoka, double pagrindineDalis, double palukanuDalis, double likusiSuma) {
        this.menuo = new SimpleIntegerProperty(menuo);
        this.menesineImoka = new SimpleDoubleProperty(menesineImoka);
        this.pagrindineDalis = new SimpleDoubleProperty(pagrindineDalis);
        this.palukanuDalis = new SimpleDoubleProperty(palukanuDalis);
        this.likusiSuma = new SimpleDoubleProperty(likusiSuma);
    }


    //need getters for tableview
    public int getMenuo() {
        return menuo.get();
    }


    public String getMenesineImoka() {
        return decimalFormat.format(menesineImoka.get());
    }


    public String getPagrindineDalis() {
        return decimalFormat.format(pagrindineDalis.get());
    }

    public String getPalukanuDalis() {
        return decimalFormat.format(palukanuDalis.get());
    }


    public String getLikusiSuma() {
        return decimalFormat.format(likusiSuma.get());
    }
}
