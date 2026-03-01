package com.se333.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple inventory management system.
 * Tracks products with quantities and prices.
 */
public class InventoryManager {

    private final Map<String, Product> inventory;

    public InventoryManager() {
        this.inventory = new HashMap<>();
    }

    /**
     * Add a new product or update quantity if it already exists.
     */
    public void addProduct(String name, int quantity, double price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        if (inventory.containsKey(name)) {
            Product existing = inventory.get(name);
            existing.setQuantity(existing.getQuantity() + quantity);
            existing.setPrice(price);
        } else {
            inventory.put(name, new Product(name, quantity, price));
        }
    }

    /**
     * Remove a product entirely from inventory.
     *
     * @return true if the product was removed, false if it didn't exist
     */
    public boolean removeProduct(String name) {
        if (name == null) {
            return false;
        }
        return inventory.remove(name) != null;
    }

    /**
     * Get the current quantity of a product.
     *
     * @return quantity, or -1 if the product is not found
     */
    public int getQuantity(String name) {
        Product product = inventory.get(name);
        return product != null ? product.getQuantity() : -1;
    }

    /**
     * Sell a specified quantity of a product.
     *
     * @throws IllegalArgumentException if product not found or insufficient stock
     */
    public double sellProduct(String name, int quantity) {
        if (name == null || !inventory.containsKey(name)) {
            throw new IllegalArgumentException("Product not found: " + name);
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Sell quantity must be positive");
        }

        Product product = inventory.get(name);
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException(
                "Insufficient stock. Available: " + product.getQuantity() + ", requested: " + quantity
            );
        }

        product.setQuantity(product.getQuantity() - quantity);
        return product.getPrice() * quantity;
    }

    /**
     * Get the total value of all products in inventory.
     */
    public double getTotalInventoryValue() {
        double total = 0;
        for (Product product : inventory.values()) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }

    /**
     * Get a list of products that are low in stock (below threshold).
     */
    public List<String> getLowStockProducts(int threshold) {
        List<String> lowStock = new ArrayList<>();
        for (Product product : inventory.values()) {
            if (product.getQuantity() < threshold) {
                lowStock.add(product.getName());
            }
        }
        return lowStock;
    }

    /**
     * Get the total number of distinct products.
     */
    public int getProductCount() {
        return inventory.size();
    }

    /**
     * Check if a product exists in the inventory.
     */
    public boolean hasProduct(String name) {
        return name != null && inventory.containsKey(name);
    }

    // ── Inner class ──────────────────────────────────

    /**
     * Represents a product in the inventory.
     */
    public static class Product {
        private final String name;
        private int quantity;
        private double price;

        public Product(String name, int quantity, double price) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }

        public String getName() { return name; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }

        @Override
        public String toString() {
            return name + " [qty=" + quantity + ", price=$" + String.format("%.2f", price) + "]";
        }
    }
}
