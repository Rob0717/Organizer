package home.uursp1;

import java.util.Locale;
import java.util.ResourceBundle;

public class Translations {
    private static final String TEXT_FILE = "LANG/Text";
    private ResourceBundle texts;

    public Translations(Locale loc) {
        this.texts = ResourceBundle.getBundle(TEXT_FILE,loc);
    }

    public String getText(String key){
        if(texts.containsKey(key)){
            return texts.getString(key);
        }else{
            return "!" + key + "!";
        }
    }
}
