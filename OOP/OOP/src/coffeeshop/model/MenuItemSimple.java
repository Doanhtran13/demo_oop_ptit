package coffeeshop.model;

public class MenuItemSimple {
    private int id;
    private String name;
    private double price;
    
    public MenuItemSimple(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    
    public String toCSV() {
        return id + "," + name + "," + price;
    }
    
    
    public static MenuItemSimple fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length == 3) {
            try {
                int id = Integer.parseInt(parts[0]);
                double price = Double.parseDouble(parts[2]);
                return new MenuItemSimple(id, parts[1], price);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}

