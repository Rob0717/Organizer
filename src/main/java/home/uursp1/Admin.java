package home.uursp1;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class Admin {
    StringProperty userName;
    StringProperty passWord;
    public Admin(String userName,String passWord) {
        this.userName = new SimpleStringProperty(userName);
        this.passWord = new SimpleStringProperty(passWord);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getPassWord() {
        return passWord.get();
    }

    public StringProperty passWordProperty() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord.set(passWord);
    }
}