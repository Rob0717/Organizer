package home.uursp1;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import java.time.LocalDate;

public class Pacient {
    private SimpleStringProperty jmeno,prijmeni,datum;
    private ObjectProperty<LocalDate> datumNarozeni;
    private ComboBox zamestnanciComboBox;
    public Pacient(String jmeno, String prijmeni, ObservableList<String> zamestnanciObservable, LocalDate datumNarozeni, String datum){
        super();
        this.jmeno = new SimpleStringProperty(jmeno);
        this.prijmeni = new SimpleStringProperty(prijmeni);
        this.datumNarozeni = new SimpleObjectProperty<>(datumNarozeni);
        this.zamestnanciComboBox = new ComboBox(zamestnanciObservable);
        this.datum = new SimpleStringProperty(datum);
    }

    public LocalDate getDatumNarozeni() {
        return datumNarozeni.get();
    }

    public ObjectProperty<LocalDate> datumNarozeniProperty() {
        return datumNarozeni;
    }

    public void setDatumNarozeni(LocalDate datumNarozeni) {
        this.datumNarozeni.set(datumNarozeni);
    }

    public ComboBox getZamestnanciComboBox() {
        return zamestnanciComboBox;
    }

    public void setZamestnanciComboBox(ComboBox zamestnanciComboBox) {
        this.zamestnanciComboBox = zamestnanciComboBox;
    }

    public String getJmeno() {
        return jmeno.get();
    }

    public SimpleStringProperty jmenoProperty() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno.set(jmeno);
    }

    public String getPrijmeni() {
        return prijmeni.get();
    }

    public SimpleStringProperty prijmeniProperty() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni.set(prijmeni);
    }

    public String getDatum() {
        return datum.get();
    }

    public SimpleStringProperty datumProperty() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum.set(datum);
    }
}
