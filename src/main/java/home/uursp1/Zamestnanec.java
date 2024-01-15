package home.uursp1;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Zamestnanec implements Comparable<Zamestnanec> {
    private StringProperty jmeno,prijmeni,email,datum;
    public Zamestnanec(String jmeno, String prijmeni, String email, String datum){
        this.jmeno = new SimpleStringProperty(jmeno);
        this.prijmeni = new SimpleStringProperty(prijmeni);
        this.email = new SimpleStringProperty(email);
        this.datum = new SimpleStringProperty(datum);
    }

    @Override
    public int compareTo(Zamestnanec o) {
        return this.getJmeno().compareTo(o.getJmeno());
    }

    public String getJmeno() {
        return jmeno.get();
    }

    public StringProperty jmenoProperty() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno.set(jmeno);
    }

    public String getPrijmeni() {
        return prijmeni.get();
    }

    public StringProperty prijmeniProperty() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni.set(prijmeni);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getDatum() {
        return datum.get();
    }

    public StringProperty datumProperty() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum.set(datum);
    }
}
