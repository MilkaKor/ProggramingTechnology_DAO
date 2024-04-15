package com.example.proggramingtechnologydao;

import com.example.proggramingtechnologydao.dao.EventDao;
import com.example.proggramingtechnologydao.infrastructure.IEventDao;
import com.example.proggramingtechnologydao.model.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public Button searchButton;
    public Button deleteButton;
    public Button addButton;
    public ListView<String> listAccount;
    @FXML
    private Label welcomeText;
    public TextField event_name;
    public TextField idAccount;
    public TextField event_description;
    public TextField start_datetime;
    public Label id;
    private IEventDao accountDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountDao = new EventDao();
        try {
            var list = accountDao.getAll().stream().map(Event::toString).toList();
            System.out.println(list.get(0));
            listAccount.setItems(FXCollections.observableArrayList(list));
            listAccount.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if(t1==null){
                        return;
                    }
                    int sid = Integer.parseInt(t1.split(" ")[0]);
                    id.setText(String.valueOf(sid));
                    addButton.setText("Изменить");
                    try {
                        Event model = accountDao.get(sid);
                        event_name.setText(model.event_name);
                        event_description.setText(model.event_description);
                        start_datetime.setText(model.start_datetime);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteButtonHandler(ActionEvent actionEvent) throws SQLException {
        accountDao.delete(Integer.parseInt(id.getText()));
        var list = accountDao.getAll().stream().map(Event::toString).toList();
        listAccount.getItems().clear();
        listAccount.setItems(FXCollections.observableArrayList(list));
        resetValues();
    }

    public void searchButtonHandler(ActionEvent actionEvent) throws SQLException {
        if (Objects.equals(idAccount.getText(), "")) {
            var list = accountDao.getAll().stream().map(Event::toString).toList();
            listAccount.getItems().clear();
            listAccount.setItems(FXCollections.observableArrayList(list));
            return;
        }

        var item = accountDao.get(Integer.parseInt(idAccount.getText()));

        if(item != null)
        {
        listAccount.getItems().clear();
        listAccount.setItems(FXCollections.observableArrayList(item.toString()));
        }
    }

    public void addButtonHandler(ActionEvent actionEvent) throws SQLException, ParseException {
        if (id.getText().equals("")) {
            String eventName = event_name.getText();
            String eventDescription = event_description.getText();
            String startDatetime = start_datetime.getText();

            Event newEvent = new Event(Integer.parseInt(idAccount.getText()), eventName, eventDescription, startDatetime);
            try {
                accountDao.add(newEvent);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
        {
            int sid = Integer.parseInt(id.getText());
            String eventName = event_name.getText();
            String eventDescription = event_description.getText();
            String startDatetime = start_datetime.getText();

            Event newEvent = new Event(sid, eventName, eventDescription, startDatetime);
            accountDao.update(newEvent);
        }
        resetValues();
        refreshEventList();
    }

    private void refreshEventList() throws SQLException {
        var list = accountDao.getAll().stream().map(Event::toString).toList();
        listAccount.getItems().clear();
        listAccount.setItems(FXCollections.observableArrayList(list));
    }

    public void reset(ActionEvent actionEvent) {
        resetValues();
    }

    private void resetValues() {
        addButton.setText("Добавить");
        id.setText("");
        event_name.setText("");
        event_description.setText("");
        start_datetime.setText("");
    }

}