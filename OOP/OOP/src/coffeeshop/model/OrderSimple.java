package coffeeshop.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderSimple {
    private int orderId;
    private String username;
    private LocalDateTime orderTime;
    private List<OrderItemSimple> items;
    private double totalAmount;
    
    public OrderSimple(int orderId, String username) {
        this.orderId = orderId;
        this.username = username;
        this.orderTime = LocalDateTime.now();
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
    }
    
    public int getOrderId() {
        return orderId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public LocalDateTime getOrderTime() {
        return orderTime;
    }
    
    public List<OrderItemSimple> getItems() {
        return items;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void addItem(OrderItemSimple item) {
        items.add(item);
        recalculateTotal();
    }
    
    private void recalculateTotal() {
        totalAmount = 0.0;
        for (OrderItemSimple item : items) {
            totalAmount += item.getSubtotal();
        }
    }
    
    
    public String toCSV() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append(orderId).append(",");
        sb.append(username).append(",");
        sb.append(orderTime.format(formatter)).append(",");
        sb.append(totalAmount).append(",");
        
        
        List<String> itemStrings = new ArrayList<>();
        for (OrderItemSimple item : items) {
            itemStrings.add(item.getItemName() + "x" + item.getQuantity() + "@" + item.getPrice());
        }
        sb.append(String.join(";", itemStrings));
        
        return sb.toString();
    }
    
    
    public static OrderSimple fromCSV(String csvLine) {
        String[] parts = csvLine.split(",", 5);
        if (parts.length >= 4) {
            try {
                int orderId = Integer.parseInt(parts[0]);
                String username = parts[1];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime orderTime = LocalDateTime.parse(parts[2], formatter);
                double totalAmount = Double.parseDouble(parts[3]);
                
                OrderSimple order = new OrderSimple(orderId, username);
                order.totalAmount = totalAmount;
                order.orderTime = orderTime;
                
                
                if (parts.length == 5 && !parts[4].isEmpty()) {
                    String[] itemStrings = parts[4].split(";");
                    for (String itemStr : itemStrings) {
                        String[] itemParts = itemStr.split("@");
                        if (itemParts.length == 2) {
                            String[] nameQty = itemParts[0].split("x");
                            if (nameQty.length == 2) {
                                String itemName = nameQty[0];
                                int quantity = Integer.parseInt(nameQty[1]);
                                double price = Double.parseDouble(itemParts[1]);
                                order.addItem(new OrderItemSimple(itemName, quantity, price));
                            }
                        }
                    }
                }
                
                return order;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}

