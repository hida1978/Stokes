
package stokes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class ViewStokesController implements Initializable {
    
    @FXML
    TableView itemTable;
    @FXML
    TableView contactTable;    
    @FXML
    TextField inputLastname;
    @FXML
    TextField inputFirstName;
    @FXML
    TextField inputEmail;
    @FXML
    Button addNewContactButton;
    @FXML
    StackPane menuPane;
    @FXML
    Pane contactPane;
    @FXML
    Pane exportPane;
    @FXML
    SplitPane mainSplit;
    @FXML
    AnchorPane anchor;
    @FXML
    TextField inputExportName;
    @FXML
    Button exportButton;
    @FXML
    Button loginButton;    
    @FXML
    TextField emailLoginInput;
    @FXML
    TextField pwLoginInput;    
    
    @FXML
    private void loginButton(ActionEvent event) {
        System.out.println("You clicked me!");

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}