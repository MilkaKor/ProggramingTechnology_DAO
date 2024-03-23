package com.example.proggramingtechnologydao;

import com.example.proggramingtechnologydao.dao.EventDao;
import com.example.proggramingtechnologydao.infrastructure.IEventDao;
import com.example.proggramingtechnologydao.model.Event;
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
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public Button searchButton;
    public Button deleteButton;
    public TextField idAccount;
    public ListView<String> listAccount;
    @FXML
    private Label welcomeText;

    private IEventDao accountDao;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountDao = new EventDao();
        try {
            var list = accountDao.getAll().stream().map(Event::toString).toList();
            System.out.println(list.get(0));
            listAccount.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteButtonHandler(ActionEvent actionEvent) throws SQLException {
        accountDao.delete(Integer.parseInt(idAccount.getText()));
        var list = accountDao.getAll().stream().map(Event::toString).toList();
        listAccount.getItems().clear();
        listAccount.setItems(FXCollections.observableArrayList(list));
    }

    public void searchButtonHandler(ActionEvent actionEvent) throws SQLException {
        if (Objects.equals(idAccount.getText(), "")){
            var list = accountDao.getAll().stream().map(Event::toString).toList();
            listAccount.getItems().clear();
            listAccount.setItems(FXCollections.observableArrayList(list));
            return;
        }

        var item = accountDao.get(Integer.parseInt(idAccount.getText()));


        listAccount.getItems().clear();
        listAccount.setItems(FXCollections.observableArrayList(item.toString()));
    }
}