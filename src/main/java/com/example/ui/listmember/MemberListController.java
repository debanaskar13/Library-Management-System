package com.example.ui.listmember;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.database.DatabaseHandler;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class MemberListController implements Initializable {
    ObservableList<Member> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Member> tableView;
    @FXML
    private TableColumn<Member, String> nameCol;
    @FXML
    private TableColumn<Member, String> idCol;
    @FXML
    private TableColumn<Member, String> mobileCol;
    @FXML
    private TableColumn<Member, String> emailCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loaddata();
    }

    private void loaddata() {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM member";
        ResultSet result = handler.execQuery(qu);
        try {
            while (result.next()) {
                String name = result.getString("name");
                String id = result.getString("id");
                String mobile = result.getString("mobile");
                String email = result.getString("email");
                list.add(new Member(name, id, mobile, email));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        tableView.getItems().setAll(list);
    }

    private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    public static class Member {
        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty email;

        Member(String name, String id, String mobile, String email) {
            this.name = new SimpleStringProperty(name);
            this.id = new SimpleStringProperty(id);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
        }

        public String getName() {
            return this.name.get();
        }

        public String getId() {
            return this.id.get();
        }

        public String getMobile() {
            return this.mobile.get();
        }

        public String getEmail() {
            return this.email.get();
        }

    }

}
