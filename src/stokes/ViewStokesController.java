
package stokes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


public class ViewStokesController implements Initializable {
    
//<editor-fold defaultstate="collapsed" desc="FXML import">
    @FXML
    SplitPane mainSplit;
    @FXML
    AnchorPane anchor;
// LOGIN PANE
    @FXML
    Pane loginPane;    
    @FXML
    Button loginButton;
    @FXML
    TextField emailLoginInput;
    @FXML
    TextField pwLoginInput;
// USER PANE
    @FXML
    Pane contactPane;    
    @FXML
    TableView contactTable;   
    @FXML    
    TextField inputLastName;
    @FXML
    TextField inputFirstName;
    @FXML
    TextField inputEmail;
    @FXML
    TextField inputPassword;
    @FXML
    Button addNewContactButton;    
//REGISTER PANE
    @FXML
    Pane registerPane;
    @FXML
    Button registerButton;
    @FXML
    Button confirmRegisterButton;     
    @FXML
    TextField inputLastNameR;
    @FXML
    TextField inputFirstNameR;
    @FXML
    TextField inputEmailR;
    @FXML
    TextField inputPasswordR;       
// ITEM PANE
    @FXML
    TableView itemTable;
    @FXML
    Pane itemPane;  
    @FXML    
    TextField inputItemName;
    @FXML
    TextField inputItemDescription;
    @FXML
    TextField inputItemQuantity;
    @FXML
    Button addNewItemButton;
    @FXML
    TextField inputSomething;
   
//EXPORT PANE    
    @FXML
    Pane exportPane;
    @FXML
    TextField inputExportName;
    @FXML
    Button exportButton;
// MENU PANE    
    @FXML
    StackPane menuPane;

//</editor-fold>

 
    DB db = new DB();
    DBItem dbItem = new DBItem();

    private final String MENU_USERS = "Felhasználók";
    private final String MENU_LIST = "Felhasználók listája";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_ITEMS = "Termékek";    
    private final String MENU_EXIT = "Kilépés";

    
    public String logPw = "";
    public String logEmail = ""; 
    public static boolean grantAccess = false;

    
    private final ObservableList<Person> data = FXCollections.observableArrayList();  
    private final ObservableList<Item> dataItem = FXCollections.observableArrayList(new Item ("saher torta", "a", "a", "a"));     
    
    public void setTableData() {
        TableColumn lastNameCol = new TableColumn("Vezetéknév");
        lastNameCol.setMinWidth(130);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setLastName(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );

        TableColumn firstNameCol = new TableColumn("Keresztnév");
        firstNameCol.setMinWidth(130);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

        firstNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setFirstName(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );

        TableColumn emailCol = new TableColumn("Email cím");
        emailCol.setMinWidth(180);
        emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());

        emailCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setEmail(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );
        
        TableColumn passwordCol = new TableColumn("Jelszó");
        passwordCol.setMinWidth(120);
        passwordCol.setCellValueFactory(new PropertyValueFactory<Person, String>("passWord"));
        passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());

        passwordCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setPassWord(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );
        
        TableColumn removeCol = new TableColumn( "Törlés" );
        emailCol.setMinWidth(100);

        Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory = 
                new Callback<TableColumn<Person, String>, TableCell<Person, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Person, String> param )
                    {
                        final TableCell<Person, String> cell = new TableCell<Person, String>()
                        {   
                            final Button btn = new Button( "Törlés" );

                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                if ( empty )
                                {
                                    setGraphic( null );
                                    setText( null );
                                }
                                else
                                {
                                    btn.setOnAction( ( ActionEvent event ) ->
                                            {
                                                Person person = getTableView().getItems().get( getIndex() );
                                                data.remove(person);
                                                db.removeContact(person);
                                       } );
                                    setGraphic( btn );
                                    setText( null );
                                }
                            }
                        };
                        return cell;
                    }
                };

        removeCol.setCellFactory( cellFactory );
        contactTable.getColumns().addAll(lastNameCol, firstNameCol, emailCol, passwordCol, removeCol);
        data.addAll(db.getAllContacts());
        contactTable.setItems(data);
    }

    public void setItemTableData() {
        TableColumn nameCol = new TableColumn("Termék");
        nameCol.setMinWidth(130);
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));

        nameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Item, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Item, String> t) {
                Item actualItem = (Item) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualItem.setName(t.getNewValue());
                dbItem.updateItem(actualItem);
            }
        }
        );

        TableColumn descriptionCol = new TableColumn("Leírás");
        descriptionCol.setMinWidth(200);
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));

        descriptionCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Item, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Item, String> t) {
                Item actualItem = (Item) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualItem.setDescription(t.getNewValue());
                dbItem.updateItem(actualItem);
            }
        }
        );

        TableColumn quantityCol = new TableColumn("Mennyiség");
        quantityCol.setMinWidth(20);
        quantityCol.setCellValueFactory(new PropertyValueFactory<Item, String>("quantity"));
        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn());

        quantityCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Item, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Item, String> t) {
                Item actualItem = (Item) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualItem.setQuantity(t.getNewValue());
                dbItem.updateItem(actualItem);
            }
        }
        );
        
        TableColumn somethingCol = new TableColumn("Egyéb");
        somethingCol.setMinWidth(50);
        somethingCol.setCellValueFactory(new PropertyValueFactory<Item, String>("something"));
        somethingCol.setCellFactory(TextFieldTableCell.forTableColumn());

        somethingCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Item, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Item, String> t) {
                Item actualItem = (Item) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualItem.setSomething(t.getNewValue());
                dbItem.updateItem(actualItem);
            }
        }
        );
        
        TableColumn removeCol = new TableColumn( "Törlés" );
        removeCol.setMinWidth(100);

        Callback<TableColumn<Item, String>, TableCell<Item, String>> cellFactory = 
                new Callback<TableColumn<Item, String>, TableCell<Item, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Item, String> param )
                    {
                        final TableCell<Item, String> cell = new TableCell<Item, String>()
                        {   
                            final Button btn = new Button( "Törlés" );

                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                if ( empty )
                                {
                                    setGraphic( null );
                                    setText( null );
                                }
                                else
                                {
                                    btn.setOnAction( ( ActionEvent event ) ->
                                            {
                                                Item item1 = getTableView().getItems().get( getIndex() );
                                                dataItem.remove(item1);
                                                dbItem.removeItem(item1);
                                       } );
                                    setGraphic( btn );
                                    setText( null );
                                }
                            }
                        };
                        return cell;
                    }
                };

        removeCol.setCellFactory( cellFactory );
        itemTable.getColumns().addAll(nameCol, descriptionCol, quantityCol, somethingCol, removeCol);
        dataItem.addAll(dbItem.getAllItems());
        itemTable.setItems(dataItem);
    }
    
    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);

        TreeItem<String> nodeItemA = new TreeItem<>(MENU_USERS);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_ITEMS);              
        TreeItem<String> nodeItemC = new TreeItem<>(MENU_EXIT);
  
        nodeItemA.setExpanded(true);
        nodeItemB.setExpanded(true);        

        Node contactsNode = new ImageView(new Image(getClass().getResourceAsStream("/contacts.png")));
        Node exportNode = new ImageView(new Image(getClass().getResourceAsStream("/export.png")));
        Node cakeNode = new ImageView(new Image(getClass().getResourceAsStream("/cake.png")));        
        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_LIST, contactsNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_EXPORT, exportNode);
        TreeItem<String> nodeItemB1 = new TreeItem<>(MENU_ITEMS, cakeNode);       
        
        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);
        nodeItemB.getChildren().addAll(nodeItemB1);        
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemB, nodeItemC);

        menuPane.getChildren().add(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();

                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case MENU_USERS:
                            selectedItem.setExpanded(true);
                            break;
                        case MENU_LIST:
                            contactPane.setVisible(true);
                            exportPane.setVisible(false);
                            itemPane.setVisible(false);                            
                            break;
                        case MENU_EXPORT:
                            contactPane.setVisible(false);
                            exportPane.setVisible(true);                            
                            itemPane.setVisible(false);
                            break;
                        case MENU_ITEMS:
                            contactPane.setVisible(false);
                            exportPane.setVisible(false);                            
                            itemPane.setVisible(true);
                            break;                            
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }

            }
        });

    }

    
    @FXML
    private void loginButton(ActionEvent event) {
        logPw = pwLoginInput.getText();
        logEmail = emailLoginInput.getText();
        db.checkPw(logPw, logEmail);     
        if (grantAccess == true){
            loginPane.setVisible(false);
            mainSplit.setVisible(true);            
//            contactPane.setVisible(true);  
//            registerPane.setVisible(false);             
            itemPane.setVisible(true);   
//            exportPane.setVisible(false);            
        }

    }

    @FXML
    private void registerButton(ActionEvent event) {
            loginPane.setVisible(false);
            registerPane.setVisible(true);              
    }
    
    @FXML
    private void confirmRegisterButton(ActionEvent event) {
        String email = inputEmailR.getText();
        if (email.length() > 3 && email.contains("@") && email.contains(".")) {
            Person newPerson = new Person(inputLastNameR.getText(), inputFirstNameR.getText(), email, inputPasswordR.getText());
            data.add(newPerson);
            db.addContact(newPerson);
            registerPane.setVisible(false); 
            loginPane.setVisible(true);
  
        }else{
            alertReg("Adj meg egy valódi e-mail címet!");
        }
    } 
    
    @FXML
    private void addContact(ActionEvent event) {
        String email = inputEmail.getText();
        if (email.length() > 3 && email.contains("@") && email.contains(".")) {
            Person newPerson = new Person(inputLastName.getText(), inputFirstName.getText(), email, inputPassword.getText());
            data.add(newPerson);
            db.addContact(newPerson);
            inputLastName.clear();
            inputFirstName.clear();
            inputEmail.clear();
            inputPassword.clear();            
        }else{
            alert("Adj meg egy valódi e-mail címet!");
        }
    }    

    @FXML
    private void addNewItem(ActionEvent event) {
            Item newItem = new Item(inputItemName.getText(), inputItemDescription.getText(), inputItemQuantity.getText(), inputSomething.getText());
            dataItem.add(newItem);
            dbItem.addItem(newItem);
            inputItemName.clear();
            inputItemDescription.clear();
            inputItemQuantity.clear();
            inputSomething.clear();            
    } 
    
    private void alert(String text) {
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.4);

        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);
        
        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox.setVisible(false);
            }
        });
        
        anchor.getChildren().add(vbox);
        anchor.setTopAnchor(vbox, 300.0);
        anchor.setLeftAnchor(vbox, 300.0);
    }

    private void alertReg(String text) {
        registerPane.setDisable(true);            
        registerPane.setOpacity(0.4);    

        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);
        
        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                registerPane.setDisable(false);
                registerPane.setOpacity(1);
                vbox.setVisible(false);

            }
        });
        
        anchor.getChildren().add(vbox);
        anchor.setTopAnchor(vbox, 300.0);
        anchor.setLeftAnchor(vbox, 300.0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    setTableData();
    setItemTableData();    
    setMenuData(); 
   

    }    
    
}
