
package stokes;

import javafx.beans.property.SimpleStringProperty;

public class Item {
    
    private final SimpleStringProperty name;
    private final SimpleStringProperty description;
    private final SimpleStringProperty quantity;
    private final SimpleStringProperty passWord;    
    private final SimpleStringProperty id;
    
    public Item() {
        this.name = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.quantity = new SimpleStringProperty("");
        this.passWord = new SimpleStringProperty("");        
        this.id = new SimpleStringProperty("");
    }
 
    public Item(String name, String descr, String quant, String pWord) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(descr);
        this.quantity = new SimpleStringProperty(quant);
        this.passWord = new SimpleStringProperty(pWord);        
        this.id = new SimpleStringProperty("");
    }
    
    public Item(Integer id, String name, String descr, String quant, String pWord) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(descr);
        this.quantity = new SimpleStringProperty(quant);
        this.passWord = new SimpleStringProperty(pWord);        
        this.id = new SimpleStringProperty(String.valueOf(id));
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

    public String getPassWord() {
        return passWord.get();
    }
    public void setPassWord(String fName) {
        passWord.set(fName);
    }        
}
