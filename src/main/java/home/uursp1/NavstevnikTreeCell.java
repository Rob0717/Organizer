package home.uursp1;

import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;

public class NavstevnikTreeCell extends TreeCell<Navstevnik> {
    private TextField textTF;
    public void startEdit() {
        super.startEdit();
        if (textTF == null) {
            createEditor();
        }
        setText(null);
        textTF.setText(createEditorContent());
        setGraphic(textTF);
    }
    public void cancelEdit() {
        super.cancelEdit();
        setText(createContent());
        setGraphic(null);
    }
    public void updateItem(Navstevnik item, boolean empty) {
        super.updateItem(item, empty);
        if(empty){
            setText(null);
            setGraphic(null);
        }else{
            if(isEditing()){
                if(textTF != null){
                    textTF.setText(createEditorContent());
                    setText(null);
                    setGraphic(textTF);
                }
            }else{
                setText(createContent());
                setGraphic(null);
            }
        }
    }

    private String createContent() {
        return getItem().getJmenoNavstevnika() + " " + getItem().getPrijmeniNavstevnika();
    }

    private String createEditorContent() {
        return getItem().getJmenoNavstevnika() + " " + getItem().getPrijmeniNavstevnika();
    }

    private void createEditor() {
        textTF = new TextField();
        textTF.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (textTF.getText().length() == 0) {
                    cancelEdit();
                } else {
                    if(Controller.czech){
                        Navstevnik navstevnik = getItem();
                        if(navstevnik.getJmenoNavstevnika().equals("Nemocniční")){
                            navstevnik.setJmenoNavstevnika("Nemocniční");
                            navstevnik.setPrijmeniNavstevnika("Recepce");
                            commitEdit(navstevnik);
                        }else{
                            String text = textTF.getText();
                            char[] z = new char[textTF.getText().length()];
                            String novyTextJmeno = "";
                            String novyTextPrijmeni = "";
                            for(int i = 0;i < text.length();i++){
                                z[i] = text.charAt(i);
                            }
                            for(int i = 0;i < text.length();i++){
                                if(z[i] == ' '){
                                    break;
                                }
                                novyTextJmeno += z[i];
                            }
                            for(int i = text.length() - 1;i >= 0;i--){
                                if(z[i] == ' '){
                                    break;
                                }
                                novyTextPrijmeni = z[i] + novyTextPrijmeni;
                            }
                            navstevnik.setJmenoNavstevnika(novyTextJmeno);
                            navstevnik.setPrijmeniNavstevnika(novyTextPrijmeni);
                            commitEdit(navstevnik);
                        }
                    }else{
                        Navstevnik navstevnik = getItem();
                        if(navstevnik.getJmenoNavstevnika().equals("Hospital")){
                            navstevnik.setJmenoNavstevnika("Hospital");
                            navstevnik.setPrijmeniNavstevnika("Reception");
                            commitEdit(navstevnik);
                        }else{
                            String text = textTF.getText();
                            char[] z = new char[textTF.getText().length()];
                            String novyTextJmeno = "";
                            String novyTextPrijmeni = "";
                            for(int i = 0;i < text.length();i++){
                                z[i] = text.charAt(i);
                            }
                            for(int i = 0;i < text.length();i++){
                                if(z[i] == ' '){
                                    break;
                                }
                                novyTextJmeno += z[i];
                            }
                            for(int i = text.length() - 1;i >= 0;i--){
                                if(z[i] == ' '){
                                    break;
                                }
                                novyTextPrijmeni = z[i] + novyTextPrijmeni;
                            }
                            navstevnik.setJmenoNavstevnika(novyTextJmeno);
                            navstevnik.setPrijmeniNavstevnika(novyTextPrijmeni);
                            commitEdit(navstevnik);
                        }
                    }

                }
            } else if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }
}
