package coffeeshop.model;

public class OrderItemSimple {
    private String itemName;
    private int quantity;
    private double price;
    
    public OrderItemSimple(String itemName, int quantity, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public double getPrice() {
        return price;
    }
    
    public double getSubtotal() {
        return price * quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

