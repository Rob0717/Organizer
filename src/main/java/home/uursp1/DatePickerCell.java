package home.uursp1;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DatePickerCell extends TableCell<Pacient,LocalDate> {
    private DatePicker datePicker;
    public void startEdit() {
        super.startEdit();
        if (datePicker == null) {
            createDatePicker();
        }
        setText(null);
        setGraphic(datePicker);
        datePicker.show();
    }

    public void cancelEdit() {
        super.cancelEdit();
        datePicker.setValue(getItem());
        if(getItem() != null){
            setText(getItem().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
        }else{
            setText(null);
        }
        setGraphic(null);
    }
    private void createDatePicker() {
        datePicker = new DatePicker(getItem());
        datePicker.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.DELETE) {
                e.consume();
            }
        });
        datePicker.setOnAction(e -> {
            if (datePicker.getValue() != null) {
                commitEdit(datePicker.getValue());
            } else {
                e.consume();
                cancelEdit();
            }
        });
    }
    public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(item);
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                setText(item.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).toString());
                setGraphic(null);
            }
        }
    }
}