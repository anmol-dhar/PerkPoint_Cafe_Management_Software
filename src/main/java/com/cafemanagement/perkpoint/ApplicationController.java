package com.cafemanagement.perkpoint;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {
    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private Button si_loginButton;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_CreateButton;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_signupAnswer;

    @FXML
    private Button su_signupButton;

    @FXML
    private PasswordField su_signupPass;

    @FXML
    private ComboBox<?> su_signupQuestion;

    @FXML
    private TextField su_signupUser;

    @FXML
    private Button side_AlreadyAccount;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public Alert alert;


    public void loginButton(){

        if(si_username.getText().isEmpty() || si_password.getText().isEmpty()){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill Username and Password");
            alert.showAndWait();

        }
        else{
            String selectLoginData = "select username, password from employee where username = ? and password = ?";

            connect = database.connectDB();
            try{

                prepare = connect.prepareStatement(selectLoginData);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());

                result = prepare.executeQuery();

                if(result.next()){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Successful");
                    alert.showAndWait();
                }
                else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username or Password");
                    alert.showAndWait();
                }

            }
            catch(Exception ex){
                ex.printStackTrace();
            }

        }
    }

    public void regButton(){
        if(su_signupUser.getText().isEmpty() || su_signupPass.getText().isEmpty() || su_signupQuestion.getSelectionModel().getSelectedItem() == null || su_signupAnswer.getText().isEmpty()){

           alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error Message");
           alert.setHeaderText(null);
           alert.setContentText("Please fill all blank fields");
           alert.showAndWait();
        }
        else{
            String reqData = "insert into employee (username, password, question, answer, date)" + "values(?,?,?,?,?)";
            connect = database.connectDB();

            try{
                String checkUsername = "select username from employee where username = '"+su_signupUser.getText()+"'";
                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();

                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(su_signupUser.getText() + " username is already taken");
                    alert.showAndWait();
                }
                else if(su_signupPass.getText().length() < 8){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid password, atleast 8 characters are needed");
                    alert.showAndWait();
                }
                else{
                    prepare = connect.prepareStatement(reqData);
                    prepare.setString(1, su_signupUser.getText());
                    prepare.setString(2, su_signupPass.getText());
                    prepare.setString(3, (String) su_signupQuestion.getSelectionModel().getSelectedItem());
                    prepare.setString(4, su_signupAnswer.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Account successfully registered!");
                    alert.showAndWait();

                    su_signupUser.setText("");
                    su_signupPass.setText("");
                    su_signupQuestion.getSelectionModel().clearSelection();
                    su_signupAnswer.setText("");

                    TranslateTransition slider = new TranslateTransition();

                    slider.setNode(side_form);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(.5));
                    slider.setOnFinished((ActionEvent e) ->{
                        side_AlreadyAccount.setVisible(false);
                        side_CreateButton.setVisible(true);
                    });
                    slider.play();
                }

            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private String[] questionList = {"What is your favorite Color?", "What is your favorite food?", "What is your birth date?"};

    public void regQuestionList(){
        List<String> listQ = new ArrayList<>();

        for(String data : questionList){
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_signupQuestion.setItems(listData);
    }

    public void switchForm(ActionEvent event){

        TranslateTransition slider = new TranslateTransition();

        if(event.getSource() == side_CreateButton){
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) ->{
                side_AlreadyAccount.setVisible(true);
                side_CreateButton.setVisible(false);

                regQuestionList();
            });
            slider.play();
        }
        else if(event.getSource() == side_AlreadyAccount){
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) ->{
                side_AlreadyAccount.setVisible(false);
                side_CreateButton.setVisible(true);
            });
            slider.play();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb){


    }

}