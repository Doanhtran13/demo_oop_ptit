package coffeeshop.database;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import coffeeshop.model.MenuItemSimple;
import coffeeshop.model.OrderSimple;
import coffeeshop.model.User;

public class FileDatabase {
    private static final String USERS_FILE = "users.txt";
    private static final String MENU_FILE = "menu.txt";
    private static final String ORDERS_FILE = "orders.txt";
    
    
    
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        
        if (!file.exists()) {
            
            User admin = new User("admin", "admin123", "ADMIN");
            users.add(admin);
            saveUsers(users);
            return users;
        }
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    User user = User.fromCSV(line);
                    if (user != null) {
                        users.add(user);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        
        return users;
    }
    
    public static void saveUsers(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(USERS_FILE), StandardCharsets.UTF_8))) {
            for (User user : users) {
                writer.println(user.toCSV());
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    public static void addUser(User user) {
        List<User> users = loadUsers();
        users.add(user);
        saveUsers(users);
    }
    
    public static User findUser(String username) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    
    
    public static List<MenuItemSimple> loadMenu() {
        List<MenuItemSimple> menu = new ArrayList<>();
        File file = new File(MENU_FILE);
        
        if (!file.exists()) {
            
            menu.add(new MenuItemSimple(1, "Cà phê đen", 15000));
            menu.add(new MenuItemSimple(2, "Cà phê sữa", 20000));
            menu.add(new MenuItemSimple(3, "Cappuccino", 45000));
            menu.add(new MenuItemSimple(4, "Latte", 50000));
            menu.add(new MenuItemSimple(5, "Trà đá", 10000));
            ensureExpandedMenuSeed(menu);
            pruneDeprecatedItems(menu);
            saveMenu(menu);
            return menu;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    MenuItemSimple item = MenuItemSimple.fromCSV(line);
                    if (item != null) {
                        menu.add(item);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading menu: " + e.getMessage());
        }
        ensureExpandedMenuSeed(menu);
        pruneDeprecatedItems(menu);
        saveMenu(menu);
        return menu;
    }

    private static void ensureExpandedMenuSeed(List<MenuItemSimple> menu) {
        boolean hasCroissant = false;
        for (MenuItemSimple i : menu) {
            if ("Croissant bơ".equalsIgnoreCase(i.getName())) {
                hasCroissant = true;
                break;
            }
        }
        if (hasCroissant) return;

        int id = 0;
        for (MenuItemSimple i : menu) {
            if (i.getId() > id) id = i.getId();
        }
        id++;

        String[][] items = new String[][]{
            {"Americano", "35000"},
            {"Mocha", "55000"},
            {"Espresso", "30000"},
            {"Matcha Latte", "52000"},
            {"Trà sữa trân châu", "40000"},
            {"Nước cam ép", "35000"},
            {"Sinh tố bơ", "45000"},
            {"Soda chanh", "28000"},
            {"Croissant bơ", "25000"},
            {"Bánh tiramisu", "45000"},
            {"Bánh phô mai", "50000"},
            {"Brownie chocolate", "38000"},
            {"Bánh cookies", "15000"},
            {"Bánh mousse dâu", "48000"}
        };

        for (String[] it : items) {
            menu.add(new MenuItemSimple(id++, it[0], Double.parseDouble(it[1])));
        }
    }

    private static void pruneDeprecatedItems(List<MenuItemSimple> menu) {
        menu.removeIf(i -> {
            String n = i.getName();
            return "Cà phê đen".equalsIgnoreCase(n) || "Cà phê sữa".equalsIgnoreCase(n);
        });
    }
    
    public static void saveMenu(List<MenuItemSimple> menu) {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(MENU_FILE), StandardCharsets.UTF_8))) {
            for (MenuItemSimple item : menu) {
                writer.println(item.toCSV());
            }
        } catch (IOException e) {
            System.err.println("Error saving menu: " + e.getMessage());
        }
    }
    
    public static void addMenuItem(MenuItemSimple item) {
        List<MenuItemSimple> menu = loadMenu();
        menu.add(item);
        saveMenu(menu);
    }
    
    public static void deleteMenuItem(int id) {
        List<MenuItemSimple> menu = loadMenu();
        menu.removeIf(item -> item.getId() == id);
        saveMenu(menu);
    }
    
    public static void updateMenuItem(MenuItemSimple updatedItem) {
        List<MenuItemSimple> menu = loadMenu();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).getId() == updatedItem.getId()) {
                menu.set(i, updatedItem);
                break;
            }
        }
        saveMenu(menu);
    }
    
    public static MenuItemSimple findMenuItem(int id) {
        List<MenuItemSimple> menu = loadMenu();
        for (MenuItemSimple item : menu) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
    
    public static int getNextMenuItemId() {
        List<MenuItemSimple> menu = loadMenu();
        int maxId = 0;
        for (MenuItemSimple item : menu) {
            if (item.getId() > maxId) {
                maxId = item.getId();
            }
        }
        return maxId + 1;
    }
    
    
    
    public static List<OrderSimple> loadOrders() {
        List<OrderSimple> orders = new ArrayList<>();
        File file = new File(ORDERS_FILE);
        
        if (!file.exists()) {
            return orders;
        }
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    OrderSimple order = OrderSimple.fromCSV(line);
                    if (order != null) {
                        orders.add(order);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading orders: " + e.getMessage());
        }
        
        return orders;
    }
    
    public static void saveOrders(List<OrderSimple> orders) {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(ORDERS_FILE), StandardCharsets.UTF_8))) {
            for (OrderSimple order : orders) {
                writer.println(order.toCSV());
            }
        } catch (IOException e) {
            System.err.println("Error saving orders: " + e.getMessage());
        }
    }
    
    public static void addOrder(OrderSimple order) {
        List<OrderSimple> orders = loadOrders();
        orders.add(order);
        saveOrders(orders);
    }
    
    public static List<OrderSimple> getOrdersByUsername(String username) {
        List<OrderSimple> allOrders = loadOrders();
        List<OrderSimple> userOrders = new ArrayList<>();
        for (OrderSimple order : allOrders) {
            if (order.getUsername().equals(username)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }
    
    public static int getNextOrderId() {
        List<OrderSimple> orders = loadOrders();
        int maxId = 0;
        for (OrderSimple order : orders) {
            if (order.getOrderId() > maxId) {
                maxId = order.getOrderId();
            }
        }
        return maxId + 1;
    }
}

