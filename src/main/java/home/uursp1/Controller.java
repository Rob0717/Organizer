package home.uursp1;

import home.uursp1.Navstevnik.Pohlavi;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller implements Initializable {
    @FXML
    private Button buttonDomu;
    @FXML
    private Button buttonOAplikaci;
    @FXML
    private Button buttonPacienti;
    @FXML
    private Button buttonTreeView;
    @FXML
    private Button buttonZamestnanci;
    @FXML
    private Label labelHead1;
    @FXML
    private Label labelHead11;
    @FXML
    private Pane paneHead1;
    @FXML
    private Button buttonPridej;
    @FXML
    private Button buttonOdeber;
    @FXML
    private TextField textJmeno;
    @FXML
    private TextField textPrijmeni;
    @FXML
    private TextField textEmail;
    @FXML
    private TableView<Zamestnanec> tabulkaZamestnanci;
    @FXML
    private TableColumn<Zamestnanec, LocalDate> kolonkaDatumPridani;
    @FXML
    private TableColumn<Zamestnanec, String> kolonkaEmail;
    @FXML
    private TableColumn<Zamestnanec, String> kolonkaJmeno;
    @FXML
    private TableColumn<Zamestnanec, String> kolonkaPrijmeni;
    @FXML
    private Button buttonPridejPacient;
    @FXML
    private Button buttonOdeberPacient;
    @FXML
    private Button buttonUlozPacient;
    @FXML
    private TextField textJmenoPacient;
    @FXML
    private TextField textPrijmeniPacient;
    @FXML
    private TableView<Pacient> tabulkaPacienti;
    @FXML
    private DatePicker datePickerDatumNarozeniPacienti;
    @FXML
    private TableColumn<Pacient,LocalDate> kolonkaDatumNarozeniPacienti;
    @FXML
    private TableColumn<Pacient, LocalDate> kolonkaDatumPridaniPacienti;
    @FXML
    private TableColumn<Pacient,String> kolonkaOsetrujiciLekarPacienti;
    @FXML
    private TableColumn<Pacient, String> kolonkaJmenoPacienti;
    @FXML
    private TableColumn<Pacient, String> kolonkaPrijmeniPacienti;

    ObservableList<Zamestnanec> zamestnanci;
    ObservableList<Zamestnanec> selectedZamestnanci;
    ObservableList<Pacient> pacienti;
    ObservableList<Pacient> selectedPacienti;
    ObservableList<String> zamestnanciObservable = FXCollections.observableArrayList();

    @FXML
    private GridPane paneOAplikaci;
    @FXML
    private GridPane paneZamestnanci;
    @FXML
    private GridPane panePacienti;
    @FXML
    private GridPane paneTreeView;
    @FXML
    private GridPane paneDomu;
    @FXML
    private Button buttonDbLoad;
    @FXML
    private TreeView<Navstevnik> treeViewNavstevnici;
    @FXML
    private TextField jmenoFieldTree;
    @FXML
    private TextField prijmeniFieldTree;
    @FXML
    private TextArea textAreaTree;
    @FXML
    private Button buttonPridejTree;
    @FXML
    private Button buttonOdeberTree;
    @FXML
    private ChoiceBox<Navstevnik.Pohlavi> choiceBoxPohlavi;
    @FXML
    private TextArea textAreaOAplikaci;
    @FXML
    private ImageView imageOAplikaci;
    @FXML
    private Button buttonJazyk;
    public static boolean czech = true;
    public static final String
            CZ_LANG =  "cs", CZ_COUNTRY = "CZ",
            EN_LANG =  "en", EN_COUNTRY = "UK",
            DEF_LANG = CZ_LANG, DEF_COUNTRY = CZ_COUNTRY;
    private Translations translations;
    private static final String
            CZ_FLAG = "/IMG/cz.png",EN_FLAG = "/IMG/uk.png";
    private Image czImgFlag,enImgFlag,actualImgFlag;

    @FXML
    private Text textVitejte;
    @FXML
    private Text textDatumPrihlaseni;
    @FXML
    private TextField textFieldDatumPrihlaseni;

    private LocalDate datumPrihlaseni = LocalDate.now();
    private LocalTime casPrihlaseni = LocalTime.now();

    @FXML
    private Text textPrihlasenyUzivatel;
    @FXML
    private Text textJmenoUzivatele;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale.setDefault(new Locale(DEF_LANG,DEF_COUNTRY));

        textFieldDatumPrihlaseni.setEditable(false);

        translations = new Translations(Locale.getDefault());
        czImgFlag = new Image(getClass().getResourceAsStream(CZ_FLAG));
        enImgFlag = new Image(getClass().getResourceAsStream(EN_FLAG));

        tabulkaZamestnanci.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabulkaZamestnanci.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tabulkaPacienti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabulkaPacienti.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        zamestnanci = FXCollections.observableArrayList();
        selectedZamestnanci = tabulkaZamestnanci.getSelectionModel().getSelectedItems();

        pacienti = FXCollections.observableArrayList();
        selectedPacienti = tabulkaPacienti.getSelectionModel().getSelectedItems();

        kolonkaJmeno.setCellValueFactory(new PropertyValueFactory<>("jmeno"));
        kolonkaPrijmeni.setCellValueFactory(new PropertyValueFactory<>("prijmeni"));
        kolonkaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        kolonkaDatumPridani.setCellValueFactory(new PropertyValueFactory<>("datum"));
        tabulkaZamestnanci.setItems(zamestnanci);


        tabulkaPacienti.setEditable(true);
        kolonkaJmenoPacienti.setCellValueFactory(new PropertyValueFactory<>("jmeno"));
        kolonkaJmenoPacienti.setCellFactory(TextFieldTableCell.forTableColumn());
        kolonkaPrijmeniPacienti.setCellValueFactory(new PropertyValueFactory<>("prijmeni"));
        kolonkaPrijmeniPacienti.setCellFactory(TextFieldTableCell.forTableColumn());
        kolonkaOsetrujiciLekarPacienti.setCellValueFactory(new PropertyValueFactory<>("zamestnanciComboBox"));
        kolonkaDatumNarozeniPacienti.setCellValueFactory(new PropertyValueFactory<>("datumNarozeni"));
        kolonkaDatumNarozeniPacienti.setCellFactory(column -> new DatePickerCell());
        kolonkaDatumNarozeniPacienti.setOnEditCommit(this::commitDatumNarozeni);

        kolonkaDatumPridaniPacienti.setCellValueFactory(new PropertyValueFactory<>("datum"));
        tabulkaPacienti.setItems(pacienti);

        textAreaTree.setEditable(false);
        textAreaTree.setText(vytvorPopis());

        choiceBoxPohlavi.setItems(FXCollections.observableArrayList(Navstevnik.Pohlavi.values()));

        treeViewNavstevnici.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        treeViewNavstevnici.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                textAreaTree.setText(vytvorPopis()));
        treeViewNavstevnici.setCellFactory(e -> new NavstevnikTreeCell());
        treeViewNavstevnici.setEditable(true);
        treeViewNavstevnici.setOnEditCommit(e -> {
            seradNavstevniky(e.getTreeItem().getParent());
            textAreaTree.setText(vytvorPopis());
        });
        treeViewNavstevnici.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends TreeItem<Navstevnik>> zmena)
                -> textAreaTree.setText(vytvorPopis()));
        treeViewNavstevnici.setRoot(new TreeItem<>(new Navstevnik("Nemocniční","Recepce",Pohlavi.ZENA)));
        vytvorPuvodniNavstevniky(treeViewNavstevnici.getRoot());

        textAreaOAplikaci.setEditable(false);
        textAreaOAplikaci.setText("Autor: Robert Onder\n\n" +
                                  "Aplikace pro ukládání dat o zaměstnancích, pacientech a návštěvnících.\n" +
                                  "Tato aplikace komunikuje s univerzitní databází phpMyAdmin.");

        aktualizujJazyk();
        paneDomu.toFront();
    }
    @FXML
    private void eventHandler(ActionEvent actionEvent) throws FileNotFoundException {
        if(actionEvent.getSource() == buttonDomu){
            if(czech){
                labelHead1.setText("ÚVODNÍ STRANA");
            }else{
                labelHead1.setText("HOME PAGE");
            }
            paneHead1.setBackground(new Background(new BackgroundFill(Color.rgb(150,0,0),CornerRadii.EMPTY,Insets.EMPTY)));
            paneDomu.toFront();
        } else if(actionEvent.getSource() == buttonZamestnanci){
            if(czech){
                labelHead1.setText("ZAMĚSTNANCI");
            }else{
                labelHead1.setText("EMPLOYEES");
            }
            paneHead1.setBackground(new Background(new BackgroundFill(Color.rgb(0,114,114),CornerRadii.EMPTY,Insets.EMPTY)));
            paneZamestnanci.toFront();
        } else if(actionEvent.getSource() == buttonPacienti){
            if(czech){
                labelHead1.setText("PACIENTI");
            }else{
                labelHead1.setText("PATIENTS");
            }
            paneHead1.setBackground(new Background(new BackgroundFill(Color.rgb(0,14,122),CornerRadii.EMPTY,Insets.EMPTY)));
            panePacienti.toFront();
        } else if(actionEvent.getSource() == buttonTreeView){
            if(czech){
                labelHead1.setText("NÁVŠTĚVNÍCI");
            }else{
                labelHead1.setText("VISITORS");
            }
            paneHead1.setBackground(new Background(new BackgroundFill(Color.rgb(0, 79,35),CornerRadii.EMPTY,Insets.EMPTY)));
            paneTreeView.toFront();
        } else if(actionEvent.getSource() == buttonOAplikaci){
            if(czech){
                labelHead1.setText("O APLIKACI");
                textJmenoUzivatele.setText(Main.uzivatel);
            }else{
                labelHead1.setText("ABOUT APP");
                textJmenoUzivatele.setText(Main.uzivatel);
            }
            paneHead1.setBackground(new Background(new BackgroundFill(Color.rgb(45, 29, 63),CornerRadii.EMPTY,Insets.EMPTY)));
            paneOAplikaci.toFront();
        }

        if(actionEvent.getSource() == buttonPridej){
            String j = textJmeno.getText();
            String p = textPrijmeni.getText();
            String e = textEmail.getText();
            String d = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));

            ArrayList<String> emails = new ArrayList<>();
            emails.add(e);
            String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(emails.get(0));
            emails.remove(e);

            if((!(j.equals(""))) && (!(p.equals(""))) && (!(e.equals("")))) {
                if(matcher.matches()){
                    zamestnanciObservable.add(j + " " + p);
                    zamestnanci.add(new Zamestnanec(j,p,e,d));

                    String jmDb = "'" + j + "'";
                    String prDb = "'" + p + "'";
                    String emDb = "'" + e + "'";
                    String datDb = "'" + d + "'";
                    String sql = "INSERT INTO zamestnanec (jmeno,prijmeni,email,datum) VALUES " +
                            "(" + jmDb + "," + prDb + "," + emDb + "," + datDb + ")";
                    Main.executeUpdate(sql);
                    textJmeno.setText("");
                    textPrijmeni.setText("");
                    textEmail.setText("");
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    if(czech){
                        alert.setTitle("Chyba");
                        alert.setHeaderText("Chyba emailové adresy");
                        alert.setContentText("Emailová adresa musí být ve správném tvaru!");
                    }else{
                        alert.setTitle("Error");
                        alert.setHeaderText("Email address error");
                        alert.setContentText("The email address must be in the correct format!");
                    }
                    alert.show();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if(czech){
                    alert.setTitle("Chyba");
                    alert.setHeaderText("Chyba při vkládání zaměstnance");
                    alert.setContentText("Musí být vepsáno jméno, příjmení a emailová adresa!");
                }else{
                    alert.setTitle("Error");
                    alert.setHeaderText("Error during inserting employee");
                    alert.setContentText("First name, last name and email address must be entered!");
                }

                alert.show();
            }
            tabulkaZamestnanci.getSelectionModel().clearSelection();
        }
        if(actionEvent.getSource() == buttonOdeber){
            ArrayList<Zamestnanec> radky = new ArrayList<>(selectedZamestnanci);
            if(radky.size() != 0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                if(czech){
                    alert.setTitle("Potvrzení smazání");
                    alert.setHeaderText("Potvrzení odebrání zvolených zaměstnanců z tabulky");
                    alert.setContentText("Přejete si zvolené zaměstnance smazat?");
                }else{
                    alert.setTitle("Confirm deletion");
                    alert.setHeaderText("Confirmation of removal of selected employees from the table");
                    alert.setContentText("Do you wish to delete the selected employees?");
                }

                Optional<ButtonType> potvrzeni = alert.showAndWait();
                if(potvrzeni.isPresent() && potvrzeni.get() == ButtonType.OK){
                    for(int i = 0;i < radky.size();i++){
                        zamestnanciObservable.remove(selectedZamestnanci.get(i).getJmeno() + " " + selectedZamestnanci.get(i).getPrijmeni());
                        String emDb = "'" + radky.get(i).getEmail() + "'";
                        String sql = "DELETE FROM zamestnanec WHERE email = " + emDb;
                        Main.executeUpdate(sql);
                    }

                    radky.forEach(selected -> tabulkaZamestnanci.getItems().remove(selected));

                    tabulkaZamestnanci.getSelectionModel().clearSelection();
                    Alert smazano = new Alert(Alert.AlertType.INFORMATION);
                    if(czech){
                        smazano.setTitle("Smazání provedeno");
                        smazano.setHeaderText("SMAZÁNO");
                        smazano.setContentText("Zvolení zaměstnanci byli odebráni.");
                    }else{
                        smazano.setTitle("Delete done");
                        smazano.setHeaderText("DELETED");
                        smazano.setContentText("Elected employees were removed.");
                    }

                    smazano.showAndWait();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if(czech){
                    alert.setTitle("Upozornění");
                    alert.setHeaderText("Problém při mazání");
                    alert.setContentText("Musíte zvolit alespoň jednoho zaměstnance pro smazání.");
                }else{
                    alert.setTitle("Warning");
                    alert.setHeaderText("Delete problem");
                    alert.setContentText("You must select at least one employee to delete.");
                }
                alert.show();
            }
            tabulkaZamestnanci.getSelectionModel().clearSelection();
        }

        if(actionEvent.getSource() == buttonDbLoad){
            nactiDataZDatabazeDoTabulky("zamestnanec");
        }

        if(actionEvent.getSource() == buttonPridejPacient){
            String j = textJmenoPacient.getText();
            String p = textPrijmeniPacient.getText();
            LocalDate datumNarozeni = datePickerDatumNarozeniPacienti.getValue();
            String d = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
            if((!(j.equals("")))&&(!(p.equals("")))&&(datumNarozeni != null)){
                pacienti.add(new Pacient(j,p,zamestnanciObservable,datumNarozeni,d));
                textJmenoPacient.setText("");
                textPrijmeniPacient.setText("");
                datePickerDatumNarozeniPacienti.setValue(null);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if(czech){
                    alert.setTitle("Chyba");
                    alert.setHeaderText("Chyba při vkládání pacienta");
                    alert.setContentText("Musí být vepsáno jméno, příjmení a zvolit datum narození!");
                }else{
                    alert.setTitle("Error");
                    alert.setHeaderText("Error during insering patient");
                    alert.setContentText("First name, last name and date of birth must be entered!");
                }
                alert.show();
            }
            tabulkaPacienti.getSelectionModel().clearSelection();
        }
        if(actionEvent.getSource() == buttonOdeberPacient){
            ArrayList<Pacient> radky = new ArrayList<>(selectedPacienti);
            if(radky.size() != 0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                if(czech){
                    alert.setTitle("Potvrzení smazání");
                    alert.setHeaderText("Potvrzení odebrání zvolených pacientů z tabulky");
                    alert.setContentText("Přejete si zvolené pacienty smazat?");
                }else{
                    alert.setTitle("Confirm deletion");
                    alert.setHeaderText("Confirmation of removal of selected patients from the table");
                    alert.setContentText("Do you wish to delete the selected patients?");
                }
                Optional<ButtonType> potvrzeni = alert.showAndWait();
                if(potvrzeni.isPresent() && potvrzeni.get() == ButtonType.OK){
                    radky.forEach(selected -> tabulkaPacienti.getItems().remove(selected));
                    tabulkaPacienti.getSelectionModel().clearSelection();
                    Alert smazano = new Alert(Alert.AlertType.INFORMATION);
                    if(czech){
                        smazano.setTitle("Smazání provedeno");
                        smazano.setHeaderText("SMAZÁNO");
                        smazano.setContentText("Zvolení pacienti byli odebráni.");
                    }else{
                        smazano.setTitle("Delete done");
                        smazano.setHeaderText("DELETED");
                        smazano.setContentText("Selected patients were removed.");
                    }
                    smazano.showAndWait();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if(czech){
                    alert.setTitle("Upozornění");
                    alert.setHeaderText("Problém při mazání");
                    alert.setContentText("Musíte zvolit alespoň jednoho pacienta pro smazání.");
                }else{
                    alert.setTitle("Warning");
                    alert.setHeaderText("Delete problem");
                    alert.setContentText("You must select at least one patient to delete.");
                }
                alert.show();
            }
            tabulkaPacienti.getSelectionModel().clearSelection();
        }
        if(actionEvent.getSource() == buttonUlozPacient){
            FileChooser fileChooser = new FileChooser();
            List<Pacient> selected = new ArrayList<>(tabulkaPacienti.getSelectionModel().getSelectedItems());
            if(selected.size() == 0){
                Alert alert;
                if(czech){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Chyba");
                    alert.setHeaderText("Chyba při ukládání do souboru");
                    alert.setContentText("Nebyl zvolen žádný pacient pro uložení do souboru!");
                    alert.show();
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error during saving to file");
                    alert.setContentText("No selected patient to be saved to file!");
                    alert.show();
                }
            }else{
                File file = fileChooser.showSaveDialog(new Stage());
                if(file != null){
                    PrintWriter printWriter = new PrintWriter(file);
                    if(czech){
                        printWriter.write("Jméno | Příjmení | Ošetřující lékař | Datum narození \n\n");
                    }else{
                        printWriter.write("Name | Surname |  Doctor  | Date of birth \n\n");
                    }
                    for(Pacient p:selected){
                        printWriter.write(p.getJmeno() + " " + p.getPrijmeni() + " | " + p.getZamestnanciComboBox().getValue() + " | " + p.getDatumNarozeni() + "\n");
                    }
                    printWriter.close();
                }
            }
        }

        if(actionEvent.getSource() == buttonPridejTree){
            pridejNavstevnika();
        }
        if(actionEvent.getSource() == buttonOdeberTree){
            odeberNavstevnika();
        }

        if(actionEvent.getSource() == buttonJazyk){
            zmenJazyk();
            if(labelHead1.getText().equals("ÚVODNÍ STRANA")){
                labelHead1.setText("HOME PAGE");
            }else if(labelHead1.getText().equals("ZAMĚSTNANCI")){
                labelHead1.setText("EMPLOYEES");
            }else if(labelHead1.getText().equals("PACIENTI")){
                labelHead1.setText("PATIENTS");
            }else if(labelHead1.getText().equals("NÁVŠTĚVNÍCI")){
                labelHead1.setText("VISITORS");
            }else if(labelHead1.getText().equals("O APLIKACI")){
                labelHead1.setText("ABOUT APP");
            }else if(labelHead1.getText().equals("HOME PAGE")){
                labelHead1.setText("ÚVODNÍ STRANA");
            }else if(labelHead1.getText().equals("EMPLOYEES")){
                labelHead1.setText("ZAMĚSTNANCI");
            }else if(labelHead1.getText().equals("PATIENTS")){
                labelHead1.setText("PACIENTI");
            }else if(labelHead1.getText().equals("TREE VIEW")){
                labelHead1.setText("TREE VIEW");
            }else if(labelHead1.getText().equals("ABOUT APP")){
                labelHead1.setText("O APLIKACI");
            }

            if(Locale.getDefault().getCountry().equals(CZ_COUNTRY)){
                treeViewNavstevnici.getRoot().getValue().setJmenoNavstevnika("Nemocniční");
                treeViewNavstevnici.getRoot().getValue().setPrijmeniNavstevnika("Recepce");
            }else{
                treeViewNavstevnici.getRoot().getValue().setJmenoNavstevnika("Hospital");
                treeViewNavstevnici.getRoot().getValue().setPrijmeniNavstevnika("Reception");
            }

            treeViewNavstevnici.refresh();
            textAreaTree.setText(vytvorPopis());
            tabulkaZamestnanci.refresh();
            tabulkaPacienti.refresh();
        }

    }

    private void aktualizujJazyk(){
        labelHead11.setText(translations.getText("labelHead11"));
        textVitejte.setText(translations.getText("textVitejte"));
        textDatumPrihlaseni.setText(translations.getText("textDatumPrihlaseni"));
        textJmeno.setPromptText(translations.getText("textJmeno"));
        textPrijmeni.setPromptText(translations.getText("textPrijmeni"));
        textEmail.setPromptText(translations.getText("textEmail"));
        buttonPridej.setText(translations.getText("buttonPridej"));
        buttonOdeber.setText(translations.getText("buttonOdeber"));
        buttonDbLoad.setText(translations.getText("buttonDbLoad"));
        kolonkaJmeno.setText(translations.getText("kolonkaJmeno"));
        kolonkaPrijmeni.setText(translations.getText("kolonkaPrijmeni"));
        kolonkaEmail.setText(translations.getText("kolonkaEmail"));
        kolonkaDatumPridani.setText(translations.getText("kolonkaDatumPridani"));
        textJmenoPacient.setPromptText(translations.getText("textJmenoPacient"));
        textPrijmeniPacient.setPromptText(translations.getText("textPrijmeniPacient"));
        buttonPridejPacient.setText(translations.getText("buttonPridejPacient"));
        buttonOdeberPacient.setText(translations.getText("buttonOdeberPacient"));
        kolonkaJmenoPacienti.setText(translations.getText("kolonkaJmenoPacienti"));
        kolonkaPrijmeniPacienti.setText(translations.getText("kolonkaPrijmeniPacienti"));
        kolonkaOsetrujiciLekarPacienti.setText(translations.getText("kolonkaOsetrujiciLekarPacienti"));
        kolonkaDatumNarozeniPacienti.setText(translations.getText("kolonkaDatumNarozeniPacienti"));
        kolonkaDatumPridaniPacienti.setText(translations.getText("kolonkaDatumPridaniPacienti"));
        jmenoFieldTree.setPromptText(translations.getText("jmenoFieldTree"));
        prijmeniFieldTree.setPromptText(translations.getText("prijmeniFieldTree"));
        buttonPridejTree.setText(translations.getText("buttonPridejTree"));
        buttonOdeberTree.setText(translations.getText("buttonOdeberTree"));
        buttonDomu.setText(translations.getText("buttonDomu"));
        buttonZamestnanci.setText(translations.getText("buttonZamestnanci"));
        buttonPacienti.setText(translations.getText("buttonPacienti"));
        buttonTreeView.setText(translations.getText("buttonTreeView"));
        buttonOAplikaci.setText(translations.getText("buttonOAplikaci"));
        buttonUlozPacient.setText(translations.getText("buttonUlozPacient"));
        textAreaOAplikaci.setText(translations.getText("textAreaOAplikaci"));
        buttonJazyk.setText(translations.getText("buttonJazyk"));
        textPrihlasenyUzivatel.setText(translations.getText("textPrihlasenyUzivatel"));

        if(Locale.getDefault().getCountry().equals(CZ_COUNTRY)){
            czech = true;
            actualImgFlag = czImgFlag;
            textFieldDatumPrihlaseni.setText(datumPrihlaseni.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
            textFieldDatumPrihlaseni.redo();
        }else{
            czech = false;
            actualImgFlag = enImgFlag;
            textFieldDatumPrihlaseni.setText(datumPrihlaseni.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
            textFieldDatumPrihlaseni.redo();
        }
        imageOAplikaci.setImage(actualImgFlag);
    }
    private void zmenJazyk(){
        if(Locale.getDefault().getCountry().equals(CZ_COUNTRY)){
            Locale.setDefault(new Locale(EN_LANG,EN_COUNTRY));
        }else{
            Locale.setDefault(new Locale(CZ_LANG,CZ_COUNTRY));
        }
        translations = new Translations(Locale.getDefault());
        aktualizujJazyk();
    }
    private void vytvorPuvodniNavstevniky(TreeItem<Navstevnik> rodic) {
        TreeItem<Navstevnik> navstevnik1 = new TreeItem<>(new Navstevnik("Petr","Rychlý",Pohlavi.MUZ));
        TreeItem<Navstevnik> navstevnik2 = new TreeItem<>(new Navstevnik("Alexandra","Velká",Pohlavi.ZENA));
        TreeItem<Navstevnik> navstevnik3 = new TreeItem<>(new Navstevnik("Jaromír","Eben",Pohlavi.MUZ));
        navstevnik2.getChildren().add(navstevnik3);
        rodic.getChildren().addAll(navstevnik1,navstevnik2);
        seradNavstevniky(rodic);
    }
    private String getRadekPopisu(Navstevnik navstevnik) {
        return navstevnik.getJmenoNavstevnika() + " " + navstevnik.getPrijmeniNavstevnika() + " (" + navstevnik.getPohlavi().getSymbol() + ")";
    }
    private String vytvorPopis() {
        TreeItem<Navstevnik> zvolen = treeViewNavstevnici.getSelectionModel().getSelectedItem();
        if(czech){
            if(zvolen == null){
                return "Není zvolen žádný návštěvník";
            }else{
                StringBuilder vysledek = new StringBuilder("jméno: " + getRadekPopisu(zvolen.getValue()) + "\n");
                if(zvolen.getValue().getJmenoNavstevnika().equals("Nemocniční")){
                    vysledek = new StringBuilder();
                }
                TreeItem<Navstevnik> rodic = zvolen.getParent();
                if(rodic != null){
                    if((!zvolen.getValue().getJmenoNavstevnika().equals("Nemocniční")) && (!rodic.getValue().getJmenoNavstevnika().equals("Nemocniční"))){
                        vysledek.append(rodic.getValue().getPohlavi().getRodicPohlavi() + ": " + getRadekPopisu(rodic.getValue()) + "\n");
                    }
                }else{
                    return vysledek.toString();
                }
                rodic = rodic.getParent();
                String pra = "";
                String babicka = "babička";
                String dedecek = "dědeček";
                while(rodic != null){
                    boolean ba = false;
                    boolean de = false;
                    if(rodic.getValue().getPohlavi().equals(Pohlavi.MUZ) && !de && !ba){
                        if(!rodic.getValue().getJmenoNavstevnika().equals("Nemocniční")){
                            vysledek.append(pra + dedecek + ": " + getRadekPopisu(rodic.getValue()) + "\n");
                            de = true;
                        }
                    }else if(rodic.getValue().getPohlavi().equals(Pohlavi.ZENA) && !ba && !de){
                        if(!rodic.getValue().getJmenoNavstevnika().equals("Nemocniční")){
                            vysledek.append(pra + babicka + ": " + getRadekPopisu(rodic.getValue()) + "\n");
                            ba = true;
                        }
                    }else if(rodic.getValue().getPohlavi().equals(Pohlavi.MUZ)){
                        if(!rodic.getValue().getJmenoNavstevnika().equals("Nemocniční")){
                            vysledek.append(pra + dedecek + ": " + getRadekPopisu(rodic.getValue()) + "\n");
                        }
                    }else if(rodic.getValue().getPohlavi().equals(Pohlavi.ZENA)){
                        if(!rodic.getValue().getJmenoNavstevnika().equals("Nemocniční")){
                            vysledek.append(pra + babicka + ": " + getRadekPopisu(rodic.getValue()) + "\n");
                        }
                    }
                    pra = "pra-" + pra;
                    rodic = rodic.getParent();
                }
                return vysledek.toString();
            }
        }else{
            if(zvolen == null){
                return "No visitor is selected";
            }else{
                StringBuilder vysledek = new StringBuilder("name: " + getRadekPopisu(zvolen.getValue()) + "\n");
                if(zvolen.getValue().getJmenoNavstevnika().equals("Hospital")){
                    vysledek = new StringBuilder("");
                }
                TreeItem<Navstevnik> rodic = zvolen.getParent();
                if(rodic != null){
                    if((!zvolen.getValue().getJmenoNavstevnika().equals("Hospital")) && (!rodic.getValue().getJmenoNavstevnika().equals("Hospital"))){
                        if(rodic.getValue().getPohlavi() == Pohlavi.MUZ){
                            vysledek.append("father: " + getRadekPopisu(rodic.getValue()) + "\n");
                        }else if(rodic.getValue().getPohlavi() == Pohlavi.ZENA){
                            vysledek.append("mother: " + getRadekPopisu(rodic.getValue()) + "\n");
                        }
                    }
                }else{
                    return vysledek.toString();
                }
                rodic = rodic.getParent();
                String pra = "";
                String babicka = "grandmother";
                String dedecek = "grandfather";
                while(rodic != null){
                    boolean ba = false;
                    boolean de = false;
                    if(rodic.getValue().getPohlavi().equals(Pohlavi.MUZ) && !de && !ba){
                        if(!rodic.getValue().getJmenoNavstevnika().equals("Hospital")){
                            vysledek.append(pra + dedecek + ": " + getRadekPopisu(rodic.getValue()) + "\n");
                            de = true;
                        }
                    }else if(rodic.getValue().getPohlavi().equals(Pohlavi.ZENA) && !ba && !de){
                        if(!rodic.getValue().getJmenoNavstevnika().equals("Hospital")){
                            vysledek.append(pra + babicka + ": " + getRadekPopisu(rodic.getValue()) + "\n");
                            ba = true;
                        }
                    }else if(rodic.getValue().getPohlavi().equals(Pohlavi.MUZ)){
                        if(!rodic.getValue().getJmenoNavstevnika().equals("Hospital")){
                            vysledek.append(pra + dedecek + ": " + getRadekPopisu(rodic.getValue()) + "\n");
                        }
                    }else if(rodic.getValue().getPohlavi().equals(Pohlavi.ZENA)){
                        if(!rodic.getValue().getJmenoNavstevnika().equals("Hospital")){
                            vysledek.append(pra + babicka + ": " + getRadekPopisu(rodic.getValue()) + "\n");
                        }
                    }
                    pra = "grand-" + pra;
                    rodic = rodic.getParent();
                }
                return vysledek.toString();
            }
        }
    }
    private void pridejNavstevnika(){
        TreeItem<Navstevnik> zvolen = treeViewNavstevnici.getSelectionModel().getSelectedItem();
        if(zvolen == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if(czech){
                alert.setTitle("Chyba vkládání");
                alert.setHeaderText("Chyba vkládání návštěvníka");
                alert.setContentText("Není zvolena oblast kam bude vložen návštěvník!");
            }else{
                alert.setTitle("Insertion error");
                alert.setHeaderText("Error while inserting visitor");
                alert.setContentText("No area for placing the visitor is selected!");
            }
            alert.showAndWait();
        }else{
            String jmenoNavstevnika = jmenoFieldTree.getText();
            String prijmeniNavstevnika = prijmeniFieldTree.getText();
            Pohlavi pohlavi = choiceBoxPohlavi.getValue();
            if(jmenoNavstevnika.length() == 0 || prijmeniNavstevnika.length() == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if(czech){
                    alert.setTitle("Chyba vkládání");
                    alert.setHeaderText("Chyba při vkládání návštěvníka");
                    alert.setContentText("Musí být poskytnuto jméno a příjmení návštěvníka!");
                }else{
                    alert.setTitle("Insertion error");
                    alert.setHeaderText("Error while inserting visitor");
                    alert.setContentText("The visitor's first and last name must be provided!");
                }
                alert.showAndWait();
            }else if(pohlavi == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if(czech){
                    alert.setTitle("Chyba vkládání");
                    alert.setHeaderText("Chyba při vkládání návštěvníka");
                    alert.setContentText("Musí být poskytnuto pohlaví návštěvníka!");
                }else{
                    alert.setTitle("Insertion error");
                    alert.setHeaderText("Error while inserting visitor");
                    alert.setContentText("Visitor gender must be provided!");
                }
                alert.showAndWait();
            }else{
                Navstevnik novyNavstevnik = new Navstevnik(jmenoNavstevnika,prijmeniNavstevnika,pohlavi);
                zvolen.getChildren().add(new TreeItem<>(novyNavstevnik));
                seradNavstevniky(zvolen);
                zvolen.setExpanded(true);
                treeViewNavstevnici.getSelectionModel().clearSelection();
                jmenoFieldTree.setText("");
                prijmeniFieldTree.setText("");
            }
        }
    }
    private void odeberNavstevnika() {
        TreeItem<Navstevnik> zvolen = treeViewNavstevnici.getSelectionModel().getSelectedItem();
        if(zvolen == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if(czech){
                alert.setTitle("Chyba mazání");
                alert.setHeaderText("Nic není vybráno!");
                alert.setContentText("Musíte vybrat alespoň jednoho návštevníka");
            }else{
                alert.setTitle("Delete error");
                alert.setHeaderText("Nothing is selected!");
                alert.setContentText("You have to choose at least one visitor");
            }
            alert.showAndWait();
        }else{
            TreeItem<Navstevnik> rodic = zvolen.getParent();
            if(rodic == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if(czech){
                    alert.setTitle("Chyba mazání");
                    alert.setHeaderText("Zvolený návštěvník nemůže být odstraněn");
                    alert.setContentText("Nelze odstranit kořenový název!");
                }else{
                    alert.setTitle("Delete error");
                    alert.setHeaderText("Chosen visitor cannot be deleted");
                    alert.setContentText("Root can't be deleted!");
                }
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                if(czech){
                    alert.setTitle("Mazání");
                    alert.setHeaderText("Mazání návštěvníka");
                    alert.setContentText("Přejete si vymazat zvoleného návštěvníka?");
                }else{
                    alert.setTitle("Deleting");
                    alert.setHeaderText("Deleting visitor");
                    alert.setContentText("Do you wish to delete this visitor?");
                }
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> {
                            rodic.getChildren().remove(zvolen);
                            treeViewNavstevnici.getSelectionModel().clearSelection();
                        });
            }
            treeViewNavstevnici.getSelectionModel().clearSelection();
        }
    }
    private void seradNavstevniky(TreeItem<Navstevnik> rodic) {
            if (rodic != null) {
                rodic.getChildren().sort(Comparator.comparing(TreeItem::getValue));
            }
    }
    private void commitDatumNarozeni(TableColumn.CellEditEvent<Pacient,LocalDate> event) {
        event.getRowValue().setDatumNarozeni(event.getNewValue());
    }
    private void nactiDataZDatabazeDoTabulky(String nazevTabulky){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/uursp1",
                        "root", ""); // For MySQL
                Statement stmt = conn.createStatement()
        ) {
            String sql = "SELECT * FROM `" + nazevTabulky + "`";
            ResultSet rs = stmt.executeQuery(sql);

            String column1Value;
            String column2Value;
            String column3Value;
            String column4Value;

            boolean jeVObservableListu = false;
            boolean jeVTabulce = false;

            while (rs.next()) {
                column1Value = rs.getString("jmeno");
                column2Value = rs.getString("prijmeni");
                column3Value = rs.getString("email");
                column4Value = rs.getString("datum");

                for(String s : zamestnanciObservable) {
                    if(s.equals(column1Value + " " + column2Value)) {
                        jeVObservableListu = true;
                        break;
                    }
                }

                for(int i = 0;i < tabulkaZamestnanci.getItems().size();i++){
                    if(tabulkaZamestnanci.getItems().get(i).getJmeno().equals(column1Value) &&
                       tabulkaZamestnanci.getItems().get(i).getPrijmeni().equals(column2Value)){
                        jeVTabulce = true;
                    }
                }

                if(!jeVObservableListu){
                    zamestnanciObservable.add(column1Value + " " + column2Value);
                }

                if(!jeVTabulce){
                    zamestnanci.add(new Zamestnanec(column1Value,column2Value,column3Value,column4Value));
                }
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}