package com.se333.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for InventoryManager class.
 */
class InventoryManagerTest {

    private InventoryManager manager;

    @BeforeEach
    void setUp() {
        manager = new InventoryManager();
    }

    // ── addProduct ─────────────────────────────────

    @Test
    @DisplayName("addProduct: add new product")
    void testAddProduct() {
        manager.addProduct("Laptop", 10, 999.99);
        assertEquals(10, manager.getQuantity("Laptop"));
    }

    @Test
    @DisplayName("addProduct: update existing product quantity")
    void testAddProductExisting() {
        manager.addProduct("Laptop", 5, 999.99);
        manager.addProduct("Laptop", 3, 1099.99);
        assertEquals(8, manager.getQuantity("Laptop"));
    }

    @Test
    @DisplayName("addProduct: null name throws exception")
    void testAddProductNullName() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.addProduct(null, 5, 10.0));
    }

    @Test
    @DisplayName("addProduct: empty name throws exception")
    void testAddProductEmptyName() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.addProduct("  ", 5, 10.0));
    }

    @Test
    @DisplayName("addProduct: negative quantity throws exception")
    void testAddProductNegativeQuantity() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.addProduct("Phone", -1, 10.0));
    }

    @Test
    @DisplayName("addProduct: negative price throws exception")
    void testAddProductNegativePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.addProduct("Phone", 5, -10.0));
    }

    // ── removeProduct ──────────────────────────────

    @Test
    @DisplayName("removeProduct: existing product")
    void testRemoveProductExists() {
        manager.addProduct("Mouse", 50, 29.99);
        assertTrue(manager.removeProduct("Mouse"));
        assertFalse(manager.hasProduct("Mouse"));
    }

    @Test
    @DisplayName("removeProduct: product does not exist")
    void testRemoveProductNotExists() {
        assertFalse(manager.removeProduct("NonExistent"));
    }

    @Test
    @DisplayName("removeProduct: null returns false")
    void testRemoveProductNull() {
        assertFalse(manager.removeProduct(null));
    }

    // ── getQuantity ────────────────────────────────

    @Test
    @DisplayName("getQuantity: product not found returns -1")
    void testGetQuantityNotFound() {
        assertEquals(-1, manager.getQuantity("Ghost"));
    }

    // ── sellProduct ────────────────────────────────

    @Test
    @DisplayName("sellProduct: valid sale returns total price")
    void testSellProduct() {
        manager.addProduct("Keyboard", 10, 49.99);
        double total = manager.sellProduct("Keyboard", 3);
        assertEquals(149.97, total, 0.01);
        assertEquals(7, manager.getQuantity("Keyboard"));
    }

    @Test
    @DisplayName("sellProduct: insufficient stock throws")
    void testSellProductInsufficientStock() {
        manager.addProduct("Monitor", 2, 199.99);
        assertThrows(IllegalArgumentException.class,
                () -> manager.sellProduct("Monitor", 5));
    }

    @Test
    @DisplayName("sellProduct: product not found throws")
    void testSellProductNotFound() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.sellProduct("Unknown", 1));
    }

    @Test
    @DisplayName("sellProduct: zero quantity throws")
    void testSellProductZeroQuantity() {
        manager.addProduct("Cable", 10, 5.99);
        assertThrows(IllegalArgumentException.class,
                () -> manager.sellProduct("Cable", 0));
    }

    @Test
    @DisplayName("sellProduct: null name throws")
    void testSellProductNullName() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.sellProduct(null, 1));
    }

    // ── getTotalInventoryValue ─────────────────────

    @Test
    @DisplayName("getTotalInventoryValue: multiple products")
    void testTotalValue() {
        manager.addProduct("A", 10, 10.0);
        manager.addProduct("B", 5, 20.0);
        assertEquals(200.0, manager.getTotalInventoryValue(), 0.01);
    }

    @Test
    @DisplayName("getTotalInventoryValue: empty inventory")
    void testTotalValueEmpty() {
        assertEquals(0.0, manager.getTotalInventoryValue(), 0.01);
    }

    // ── getLowStockProducts ────────────────────────

    @Test
    @DisplayName("getLowStockProducts: finds products below threshold")
    void testLowStock() {
        manager.addProduct("A", 2, 10.0);
        manager.addProduct("B", 50, 20.0);
        manager.addProduct("C", 3, 5.0);

        List<String> lowStock = manager.getLowStockProducts(5);
        assertEquals(2, lowStock.size());
        assertTrue(lowStock.contains("A"));
        assertTrue(lowStock.contains("C"));
    }

    @Test
    @DisplayName("getLowStockProducts: none below threshold")
    void testLowStockNone() {
        manager.addProduct("A", 100, 10.0);
        List<String> lowStock = manager.getLowStockProducts(5);
        assertTrue(lowStock.isEmpty());
    }

    // ── getProductCount ────────────────────────────

    @Test
    @DisplayName("getProductCount: counts correctly")
    void testProductCount() {
        assertEquals(0, manager.getProductCount());
        manager.addProduct("A", 1, 1.0);
        manager.addProduct("B", 1, 1.0);
        assertEquals(2, manager.getProductCount());
    }

    // ── hasProduct ─────────────────────────────────

    @Test
    @DisplayName("hasProduct: returns true when exists")
    void testHasProductTrue() {
        manager.addProduct("Widget", 5, 9.99);
        assertTrue(manager.hasProduct("Widget"));
    }

    @Test
    @DisplayName("hasProduct: returns false when not exists")
    void testHasProductFalse() {
        assertFalse(manager.hasProduct("Ghost"));
    }

    @Test
    @DisplayName("hasProduct: null returns false")
    void testHasProductNull() {
        assertFalse(manager.hasProduct(null));
    }
}
