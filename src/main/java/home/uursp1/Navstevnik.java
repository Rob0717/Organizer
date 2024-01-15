package home.uursp1;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Navstevnik implements Comparable<Navstevnik>{
    private StringProperty jmenoNavstevnika;
    private StringProperty prijmeniNavstevnika;
    private ObjectProperty<Pohlavi> pohlavi;

    public Navstevnik(String jmenoNavstevnika,String prijmeniNavstevnika,Pohlavi pohlavi) {
        super();
        this.jmenoNavstevnika = new SimpleStringProperty(jmenoNavstevnika);
        this.prijmeniNavstevnika = new SimpleStringProperty(prijmeniNavstevnika);
        this.pohlavi = new SimpleObjectProperty<>(pohlavi);
    }

    public Pohlavi getPohlavi(){
        return pohlavi.get();
    }
    public enum Pohlavi{
        MUZ,ZENA;
        public String toString(){
                switch (this) {
                    case MUZ:
                        return "\u2642";
                    case ZENA:
                        return "\u2640";
                }
            return "jiné";
        }

        public String getSymbol(){
            switch (this){
                case MUZ:
                    return "\u2642";
                case ZENA:
                    return "\u2640";
            }
            return "\u26B2";
        }

        public String getRodicPohlavi(){
                switch (this) {
                    case MUZ:
                        return "otec";
                    case ZENA:
                        return "matka";
                }
            return "rodič";
        }
    }

    public ObjectProperty<Pohlavi> pohlaviProperty() {
        return pohlavi;
    }

    public void setPohlavi(Pohlavi pohlavi) {
        this.pohlavi.set(pohlavi);
    }

    public String getJmenoNavstevnika() {
        return jmenoNavstevnika.get();
    }

    public StringProperty jmenoNavstevnikaProperty() {
        return jmenoNavstevnika;
    }

    public void setJmenoNavstevnika(String jmenoNavstevnika) {
        this.jmenoNavstevnika.set(jmenoNavstevnika);
    }

    public String getPrijmeniNavstevnika() {
        return prijmeniNavstevnika.get();
    }

    public StringProperty prijmeniNavstevnikaProperty() {
        return prijmeniNavstevnika;
    }

    public void setPrijmeniNavstevnika(String prijmeniNavstevnika) {
        this.prijmeniNavstevnika.set(prijmeniNavstevnika);
    }

    @Override
    public int compareTo(Navstevnik o) {
        return this.getJmenoNavstevnika().compareTo(o.getJmenoNavstevnika());
    }
}
