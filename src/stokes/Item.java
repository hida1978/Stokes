
package stokes;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class Item {
    
    private final SimpleStringProperty name;
    private final SimpleStringProperty description;
    private final SimpleStringProperty quantity;
    private final SimpleStringProperty something;    
    private final SimpleStringProperty id;
    private CheckBox select;
    
    public Item() {
        this.name = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.quantity = new SimpleStringProperty("");
        this.something = new SimpleStringProperty("");        
        this.id = new SimpleStringProperty("");
        this.select = new CheckBox();        
    }
 
    public Item(String name, String descr, String quant, String something) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(descr);
        this.quantity = new SimpleStringProperty(quant);
        this.something = new SimpleStringProperty(something);        
        this.id = new SimpleStringProperty("");
        this.select = new CheckBox();
    }
    
    public Item(Integer id, String name, String descr, String quant, String something) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(descr);
        this.quantity = new SimpleStringProperty(quant);
        this.something = new SimpleStringProperty(something);        
        this.id = new SimpleStringProperty(String.valueOf(id));
        this.select = new CheckBox();        
    }
    
    public String getName() {
        return name.get();
    }
    public void setName(String Name) {
        name.set(Name);
    }
        
    public String getDescription() {
        return description.get();
    }
    public void setDescription(String fName) {
        description.set(fName);
    }
    
    public String getQuantity() {
        return quantity.get();
    }
    public void setQuantity(String fName) {
        quantity.set(fName);
    }

    public String getId(){
        return id.get();
    }
    
    public void setId(String fId){
        id.set(fId);
    }

    public String getSomething(){
        return something.get();
    }
    public void setSomething(String fName){
        something.set(fName);
    }  
    
    public CheckBox getSelect() {
        return select;
    }
    public void setSelect(CheckBox select){
        this.select = select;
    }      
}
