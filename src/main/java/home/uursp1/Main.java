package home.uursp1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class Main extends Application {
    TableView<Admin> adminTableView;
    TableColumn<Admin,String> username,password;
    ObservableList<Admin> udaje;
    Label textNazev,registrace;
    TextField t1,jmeno;
    PasswordField t2,heslo;
    Button log,reg,quit,registruj,vymazUdaje;
    Stage login, registationStage;
    Encryptor encryptor = new Encryptor();
    Stage adminStage;
    public static String uzivatel;

    @Override
    public void start(Stage primaryStage) throws IOException {
        login = new Stage();
        login.setWidth(400);
        login.setHeight(300);
        login.setResizable(false);
        login.setTitle("Login");
        Scene scene = new Scene((Parent) getLogin());
        login.setScene(scene);
        login.show();

        log.setOnMouseEntered(mouseEvent -> log.setStyle("-fx-background-color : #0A76D1;-fx-font-size: 14px; -fx-font-weight: bold"));
        log.setOnMouseExited(mouseEvent -> log.setStyle("-fx-background-color: #0D64B1;-fx-font-size: 14px; -fx-font-weight: bold"));
        log.setOnMousePressed(mouseEvent -> log.setStyle("-fx-background-color : #5AB1FB;-fx-font-size: 14px; -fx-font-weight: bold"));
        log.setOnMouseReleased(mouseEvent -> log.setStyle("-fx-background-color : #0A76D1;-fx-font-size: 14px; -fx-font-weight: bold"));

        reg.setOnMouseEntered(mouseEvent -> reg.setStyle("-fx-background-color : #0A76D1;-fx-font-size: 14px; -fx-font-weight: bold"));
        reg.setOnMouseExited(mouseEvent -> reg.setStyle("-fx-background-color: #0D64B1;-fx-font-size: 14px; -fx-font-weight: bold"));
        reg.setOnMousePressed(mouseEvent -> reg.setStyle("-fx-background-color : #5AB1FB;-fx-font-size: 14px; -fx-font-weight: bold"));
        reg.setOnMouseReleased(mouseEvent -> reg.setStyle("-fx-background-color : #0A76D1;-fx-font-size: 14px; -fx-font-weight: bold"));

        quit.setOnMouseEntered(mouseEvent -> quit.setStyle("-fx-background-color : #DE0000;-fx-font-size: 14px; -fx-font-weight: bold"));
        quit.setOnMouseExited(mouseEvent -> quit.setStyle("-fx-background-color: #BB0000;-fx-font-size: 14px; -fx-font-weight: bold"));
        quit.setOnMousePressed(mouseEvent -> quit.setStyle("-fx-background-color : #FF350E;-fx-font-size: 14px; -fx-font-weight: bold"));
        quit.setOnMouseReleased(mouseEvent -> quit.setStyle("-fx-background-color : #DE0000;-fx-font-size: 14px; -fx-font-weight: bold"));

        adminTableView = new TableView<>();
        adminTableView.setEditable(true);
        adminTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        adminTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        username = new TableColumn<>("uživatelské jméno");
        password = new TableColumn<>("heslo");

        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        password.setCellValueFactory(new PropertyValueFactory<>("passWord"));

        adminTableView.getColumns().add(username);
        adminTableView.getColumns().add(password);

        adminStage = new Stage();
        adminStage.setScene(new Scene((Parent) makeAdminStage()));
        adminStage.setWidth(500);
        adminStage.setHeight(400);


        udaje = FXCollections.observableArrayList();

        nactiPrihlasovaciUdajeDoTabulky("uzivatel");

        log.setOnAction(actionEvent -> {
            if(t1.getText().equals("admin") && t2.getText().equals("admin")){
                t1.setText("");
                t2.setText("");
                adminStage.show();
                vymazUdaje.setOnAction(e -> {
                    ObservableList<Admin> selected = adminTableView.getSelectionModel().getSelectedItems();
                    ObservableList<Admin> zvoleneUdaje = FXCollections.observableArrayList(selected);
                    if(zvoleneUdaje.size() != 0){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Potvrzení smazání");
                        alert.setHeaderText("Potvrzení odebrání přihlašovacích údajů");
                        alert.setContentText("Přejete si zvolené údaje vymazat?");
                        Optional<ButtonType> potvrzeni = alert.showAndWait();
                        if(potvrzeni.isPresent() && potvrzeni.get() == ButtonType.OK){

                            for(int i = 0;i < udaje.size();i++){
                                for (Admin admin : zvoleneUdaje) {
                                    if (udaje.get(i).getUserName().equals(admin.getUserName()) &&
                                            udaje.get(i).getPassWord().equals(admin.getPassWord())) {
                                        udaje.remove(i);
                                    }
                                }
                            }

                            for (Admin admin : zvoleneUdaje) {
                                adminTableView.getItems().remove(admin);
                                String uzivJmeno = "'" + admin.getUserName() + "'";
                                String sql = "DELETE FROM uzivatel WHERE username = " + uzivJmeno;
                                executeUpdate(sql);
                            }
                            Alert smazano = new Alert(Alert.AlertType.INFORMATION);
                            smazano.setTitle("Smazání provedeno");
                            smazano.setHeaderText("SMAZÁNO");
                            smazano.setContentText("Zvolené přihlašovací údaje byly vymazány.");
                            smazano.showAndWait();
                        }else{
                            adminTableView.getSelectionModel().clearSelection();
                        }
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Chyba smazání");
                        alert.setHeaderText("Chyba mazání přihlašovacích údajů");
                        alert.setContentText("Musí být zvolen alespoň jeden údaj!");
                        alert.show();
                    }
                    adminTableView.getSelectionModel().clearSelection();
                });
            }else if(t1.getText().equals("") || t2.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Chyba přihlášení");
                alert.setHeaderText("Chyba při přihlašování");
                alert.setContentText("Musí být vyplněno uživatelské jméno a heslo!");
                alert.show();
            }else if(t1.getText().length() < 6 || t2.getText().length() < 6){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Chyba přihlášení");
                alert.setHeaderText("Chyba při přihlašování");
                alert.setContentText("Požadovaná minimální délka uživatelského jména a hesla je 6.");
                alert.show();
                if(t1.getText().length() < 6 && t2.getText().length() < 6){
                    t1.setText("");
                    t2.setText("");
                }else if(t1.getText().length() < 6 && t2.getText().length() > 5){
                    t1.setText("");
                }else if(t1.getText().length() > 5 && t2.getText().length() < 6){
                    t2.setText("");
                }
            }else{
                boolean spusteno = false;
                for (Admin admin : udaje) {
                    try {
                        if (admin.userName.get().equals(t1.getText()) &&
                                admin.passWord.get().equals(encryptor.encryptString(t2.getText()))) {
                            if (!spusteno) {
                                spusteno = true;
                                if (registationStage != null) {
                                    registationStage.close();
                                }

                                login.close();
                                if (adminStage.isShowing()) {
                                    adminStage.close();
                                }
                                uzivatel = admin.userName.get();
                                primaryStage.show();
                            }
                        }
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(!spusteno){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Chyba přihlášení");
                    alert.setHeaderText("Chyba při přihlašování");
                    alert.setContentText("Zadány chybné údaje!");
                    alert.show();
                }
            }
        });

        reg.setOnAction(actionEvent -> {
            registationStage = new Stage();
            registationStage.setScene(new Scene((Parent) makeRegistrationStage()));
            registationStage.setWidth(200);
            registationStage.setHeight(150);
            registationStage.setResizable(false);

            t1.setText("");
            t2.setText("");
            registationStage.show();

            registruj.setOnAction(event -> {
                if(jmeno.getText().equals("") || heslo.getText().equals("")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Chyba registrace");
                    alert.setHeaderText("Chyba při registraci");
                    alert.setContentText("Musí být vyplněno uživatelské jméno a heslo!");
                    alert.show();
                }else if(jmeno.getText().length() < 6 || heslo.getText().length() < 6){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Chyba registrace");
                    alert.setHeaderText("Chyba při registraci");
                    alert.setContentText("Požadovaná minimální délka uživatelského jména a hesla je 6.");
                    alert.show();
                    if(jmeno.getText().length() < 6 && heslo.getText().length() < 6){
                        jmeno.setText("");
                        heslo.setText("");
                    }else if(jmeno.getText().length() < 6 && heslo.getText().length() > 5){
                        jmeno.setText("");
                    }else if(jmeno.getText().length() > 5 && heslo.getText().length() < 6){
                        heslo.setText("");
                    }
                }else{
                    String loginReg = "'" + jmeno.getText() + "'";
                    String password = "'" + heslo.getText() + "'";

                    String sql = "INSERT INTO uzivatel (username,password) VALUES " +
                            "(" + loginReg + ",MD5(" + password + "))";
                    try {
                        boolean jeVObservableListu = false;
                        for (Admin admin : udaje) {
                            if (admin.getUserName().equals(jmeno.getText()) &&
                                    admin.getPassWord().equals(encryptor.encryptString(heslo.getText()))) {
                                jeVObservableListu = true;
                            }
                        }
                        if(!jeVObservableListu){
                            executeUpdate(sql);
                            udaje.add(new Admin(jmeno.getText(),encryptor.encryptString(heslo.getText())));
                        }
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        boolean jeVTabulce = false;
                        for(int i = 0;i < adminTableView.getItems().size();i++){
                            if(adminTableView.getItems().get(i).getUserName().equals(jmeno.getText()) &&
                               adminTableView.getItems().get(i).getPassWord().equals(encryptor.encryptString(heslo.getText()))){
                                jeVTabulce = true;
                            }
                        }
                        if(!jeVTabulce){
                            adminTableView.getItems().add(new Admin(jmeno.getText(),encryptor.encryptString(heslo.getText())));
                        }
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                    jmeno.setText("");
                    heslo.setText("");
                    registationStage.close();
                    login.show();
                }
            });
            primaryStage.close();
        });

        quit.setOnAction(actionEvent -> System.exit(0));

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("myFXML.fxml")));
        primaryStage.setWidth(1150);
        primaryStage.setHeight(650);
        primaryStage.setMinWidth(1150);
        primaryStage.setMinHeight(650);
        primaryStage.setTitle("ORGANIZÉR");
        primaryStage.setScene(new Scene(root));
    }
    private Node makeRegistrationStage() {
        BorderPane registrationPane = new BorderPane();
        registrationPane.setStyle("-fx-background-color: #000000");
        VBox komponentyRegistraceVBox = new VBox();
        registrace = new Label("REGISTRACE");
        registrace.setStyle("-fx-font-size: 20px;-fx-text-fill: #008cff;-fx-font-weight: bold;-fx-alignment: CENTER;");
        jmeno = new TextField();
        jmeno.setPrefSize(120,20);
        jmeno.setPromptText("uživatelské jméno");
        jmeno.setFocusTraversable(false);
        jmeno.setOnMouseClicked(mouseEvent -> {
            jmeno.setFocusTraversable(true);
            heslo.setFocusTraversable(true);
        });
        heslo = new PasswordField();
        heslo.setPrefSize(120,20);
        heslo.setPromptText("heslo");
        heslo.setFocusTraversable(false);
        heslo.setOnMouseClicked(mouseEvent -> {
            heslo.setFocusTraversable(true);
            jmeno.setFocusTraversable(true);
        });
        registruj = new Button("Registruj");
        registruj.setPrefSize(120,20);
        registruj.setStyle("-fx-background-color: #0D64B1;-fx-font-size: 14px; -fx-font-weight: bold");
        registruj.setOnMouseEntered(mouseEvent -> registruj.setStyle("-fx-background-color : #0A76D1;-fx-font-size: 14px; -fx-font-weight: bold"));
        registruj.setOnMousePressed(mouseEvent -> registruj.setStyle("-fx-background-color : #5AB1FB;-fx-font-size: 14px; -fx-font-weight: bold"));
        registruj.setOnMouseReleased(mouseEvent -> registruj.setStyle("-fx-background-color : #0A76D1;-fx-font-size: 14px; -fx-font-weight: bold"));
        registruj.setOnMouseExited(mouseEvent -> registruj.setStyle("-fx-background-color: #0D64B1;-fx-font-size: 14px; -fx-font-weight: bold"));

        HBox hBox0 = new HBox(registrace);
        hBox0.setAlignment(Pos.CENTER);
        HBox hBox1 = new HBox(jmeno);
        hBox1.setAlignment(Pos.CENTER);
        HBox hBox2 = new HBox(heslo);
        hBox2.setAlignment(Pos.CENTER);
        HBox hBox3 = new HBox(registruj);
        hBox3.setAlignment(Pos.CENTER);

        komponentyRegistraceVBox.getChildren().add(0,hBox0);
        komponentyRegistraceVBox.getChildren().add(1,hBox1);
        komponentyRegistraceVBox.getChildren().add(2,hBox2);
        komponentyRegistraceVBox.getChildren().add(3,hBox3);

        komponentyRegistraceVBox.setAlignment(Pos.CENTER);
        registrationPane.setCenter(komponentyRegistraceVBox);

        return registrationPane;
    }
    private Node makeAdminStage() {
        BorderPane adminPane = new BorderPane();
        adminPane.setCenter(adminTableView);
        adminPane.setBottom(getDeleteButton());
        return adminPane;
    }
    private Node getDeleteButton() {
        HBox hBox = new HBox();
        vymazUdaje = new Button("Odebrat přihlašovací údaje");
        vymazUdaje.setStyle("-fx-font-size: 14px; -fx-font-weight: bold");
        vymazUdaje.setPrefSize(500,50);
        hBox.getChildren().addAll(vymazUdaje);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }
    private Node getLogin() {
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        vBox.setBackground(new Background(
                new BackgroundImage(
//                        new Image("https://img.freepik.com/free-vector/" +
//                                "background-realistic-abstract-technology-particle_23-2148431735" +
//                                ".jpg?w=1380&t=st=1683719934~exp=1683720534~hmac=" +
//                                "c8ecf89a36047e1c54da7a41f4b801c5b6ebbd0ff7553bcb0d0c800353700e77"),
                          new Image("https://images.unsplash.com/photo-1567935850829-e52d7bfe37ce?ixlib=" +
                                       "rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&" +
                                       "fit=crop&w=764&q=80"),
                        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                        new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                )));
        HBox hBox0 = new HBox();
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        HBox hBox3 = new HBox();
        HBox hBox4 = new HBox();
        HBox hBox5 = new HBox();

        textNazev = new Label("ORGANIZÉR");
        textNazev.setStyle("-fx-font-size: 25px;-fx-text-fill: #00ffff;-fx-font-weight: bold;-fx-alignment: CENTER;");

        t1 = new TextField();
        t1.setPromptText("uživatelské jméno");
        t1.setFocusTraversable(false);
        t1.setOnMouseClicked(mouseEvent -> {
            t1.setFocusTraversable(true);
            t2.setFocusTraversable(true);
        });
        t2 = new PasswordField();
        t2.setPromptText("heslo");
        t2.setFocusTraversable(false);
        t2.setOnMouseClicked(mouseEvent -> {
            t2.setFocusTraversable(true);
            t1.setFocusTraversable(true);
        });

        log = new Button("Přihlásit");
        log.setPrefSize(150,20);
        log.setStyle("-fx-background-color: #0D64B1;-fx-font-size: 14px; -fx-font-weight: bold");
        reg = new Button("Registrovat");
        reg.setPrefSize(150,20);
        reg.setStyle("-fx-background-color: #0D64B1;-fx-font-size: 14px; -fx-font-weight: bold");
        quit = new Button("Ukončit");
        quit.setPrefSize(150,20);
        quit.setStyle("-fx-background-color: #BB0000;-fx-font-size: 14px; -fx-font-weight: bold");

        hBox0.getChildren().add(textNazev);
        hBox0.setAlignment(Pos.CENTER);
        hBox1.getChildren().add(t1);
        hBox1.setAlignment(Pos.CENTER);
        hBox2.getChildren().add(t2);
        hBox2.setAlignment(Pos.CENTER);
        hBox3.getChildren().add(log);
        hBox3.setAlignment(Pos.CENTER);
        hBox4.getChildren().add(reg);
        hBox4.setAlignment(Pos.CENTER);
        hBox5.getChildren().add(quit);
        hBox5.setAlignment(Pos.CENTER);

        vBox.getChildren().add(0,textNazev);
        vBox.getChildren().add(1,hBox1);
        vBox.getChildren().add(2,hBox2);
        vBox.getChildren().add(3,hBox3);
        vBox.getChildren().add(4,hBox4);
        vBox.getChildren().add(5,hBox5);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        borderPane.setCenter(vBox);
        return borderPane;
    }
    public static void executeUpdate(String sql){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/uursp1",
                        "root", "");
                Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void nactiPrihlasovaciUdajeDoTabulky(String nazevTabulky) {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/uursp1",
                        "root", "");
                Statement stmt = conn.createStatement()
        ) {
            String sql = "SELECT * FROM `" + nazevTabulky + "`";
            ResultSet rs = stmt.executeQuery(sql);

            String column1Value;
            String column2Value;

            boolean jeVObservableListu = false;
            boolean jeVTabulce = false;

            while (rs.next()) {
                column1Value = rs.getString("username");
                column2Value = rs.getString("password");
                for (Admin admin : udaje) {
                    if (admin.getUserName().equals(column1Value) &&
                            admin.getPassWord().equals(column2Value)) {
                        jeVObservableListu = true;
                    }
                }
                for(int i = 0;i < adminTableView.getItems().size();i++){
                    if(adminTableView.getItems().get(i).getUserName().equals(column1Value) &&
                            adminTableView.getItems().get(i).getPassWord().equals(column2Value)){
                        jeVTabulce = true;
                    }
                }
                if(!jeVObservableListu){
                    udaje.add(new Admin(column1Value,column2Value));
                }

                if(!jeVTabulce){
                    adminTableView.getItems().add(new Admin(column1Value,column2Value));
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}