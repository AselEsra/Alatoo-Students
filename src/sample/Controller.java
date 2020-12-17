package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TextField tfID;
    public TextField tfName;
    public TextField tfFaculty;
    public TextField tfEmail;
    public TextField tfPhone;

    public TableView<Students> tvStudents;
    public TableColumn<Students,Integer> colID;
    public TableColumn<Students, String> colName;
    public TableColumn<Students,String> colFaculty;
    public TableColumn<Students,String> colEmail;
    public TableColumn<Students,Integer> colPhone;

    public Button btnInsert;
    public Button btnUpdate;
    public Button btnDelete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showStudents();
    }

    public void handleButtonAction(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnInsert){
            insertRecord();
        }else if (actionEvent.getSource() == btnUpdate){
            updateRecord();
        }else if (actionEvent.getSource() == btnDelete){
            deleteRecord();
        }

    }
    public Connection getConnection(){
        Connection con;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localHost:3306/library","root","Database24!");
            return con;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ObservableList<Students> getStudentsList(){
        ObservableList<Students> studentList = FXCollections.observableArrayList();
        Connection con = getConnection();
        String query = "SELECT * FROM students";
        Statement statement;
        ResultSet resultSet;

        try{
            statement = con.createStatement();
            resultSet = statement.executeQuery(query);
            Students students;
            while (resultSet.next()){
                students = new Students(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("faculty"), resultSet.getString("email"), resultSet.getInt("phoneNumber"));
                studentList.add(students);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return studentList;
    }
    public void showStudents(){
        ObservableList<Students> list = getStudentsList();

        colID.setCellValueFactory(new PropertyValueFactory<Students,Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Students,String>("name"));
        colFaculty.setCellValueFactory(new PropertyValueFactory<Students,String>("faculty"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Students,String>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Students,Integer>("phoneNumber"));

        tvStudents.setItems(list);
    }
    private void insertRecord(){
        String query = "INSERT INTO students VALUES(" + tfID.getText() + ",'"+ tfName.getText() + "','" + tfFaculty.getText() + "','" + tfEmail.getText() + "'," + tfPhone.getText() + ")";
        executeQuery(query);
        showStudents();
        deleteFields();
    }
    private void deleteFields(){
        tfID.clear();
        tfName.clear();
        tfFaculty.clear();
        tfEmail.clear();
        tfPhone.clear();
    }
    private void updateRecord(){
        String query = "UPDATE students SET name = '" + tfName.getText() + "', faculty = '" + tfFaculty.getText() + "', email = '" + tfEmail.getText() + "', phoneNumber = " + tfPhone.getText() + " WHERE id = " + tfID.getText() + "";
        executeQuery(query);
        showStudents();

    }
    private void deleteRecord(){
        String query = "DELETE FROM students WHERE id = " + tfID.getText() + "";
        executeQuery(query);
        showStudents();
    }

    private void executeQuery(String query){
        Connection connection = getConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void handleMouseAction(MouseEvent event){
        Students students = tvStudents.getSelectionModel().getSelectedItem();
        tfID.setText("" + students.getId());
        tfName.setText(students.getName());
        tfFaculty.setText(students.getFaculty());
        tfEmail.setText(students.getEmail());
        tfPhone.setText("" + students.getPhoneNumber());
    }
}
