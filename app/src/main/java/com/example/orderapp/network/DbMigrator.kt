package com.example.orderapp.network

import android.content.Context
import com.example.orderapp.types.Category
import com.example.orderapp.types.Inbox
import com.example.orderapp.types.Menu
import com.example.orderapp.types.Order
import com.example.orderapp.types.OrderProduct
import com.example.orderapp.types.Product

object DbMigrator {
    fun assertMigrated() {
        if (DbWrapper.menu.categories.isEmpty()) {
            migrateMenu()
            DbWrapper.menu = DbWrapper.readMenu()
        }

        if (DbWrapper.inbox.orders.isEmpty()) {
            migrateOrders()
            DbWrapper.inbox = DbWrapper.readInbox()
        }
    }

    fun resetMigration(context: Context) {
        DbWrapper.clear(context)
        migrateMenu()
        migrateOrders()
        DbWrapper.initialize(context)
    }

    private fun migrateMenu() {
        menuMockData().categories.forEach {category ->
            category.products.forEach {product ->
                DbWrapper.addMenuProduct(product, category)
            }}
    }

    private fun migrateOrders() {
        inboxMockData().orders.forEach { order ->
            DbWrapper.addOrder(order)
        }
    }

    fun menuMockData(): Menu {
        val p1 = Product("Pizza Margarita", 51, "10")
        val p2 = Product("Pizza Pepperoni", 52, "11")
        val p3 = Product("Pizza Hawaii", 53, "12")
        val p4 = Product("Pizza Parma", 54, "13")
        val p5 = Product("Pizza Potato", 55, "14")
        val pizza = Category("Pizza", listOf(p1, p2, p3, p4, p5), "1")

        val d1 = Product("Tiramisu", 40, "20")
        val d2 = Product("Apple Pie", 50, "21")
        val d3 = Product("Banana Split", 60, "22")
        val d4 = Product("Neapolitan Ice Cream", 70, "23")
        val d5 = Product("Cheese Slate", 80, "24")
        val dessert = Category("Dessert", listOf(d1, d2, d3, d4, d5), "2")

        val s1 = Product("Coca Cola", 20, "30")
        val s2 = Product("Sprite", 22, "31")
        val s3 = Product("Orange Juice", 20, "32")
        val s4 = Product("Iced Tea", 20, "33")
        val s5 = Product("Pina Colada", 20, "34")
        val drink = Category("Drinks", listOf(s1, s2, s3, s4, s5), "3")

        return Menu(listOf(pizza, dessert, drink), "0")
    }

    fun inboxMockData(): Inbox {
        val menu = menuMockData()

        val o1 = Order(1, listOf(
            OrderProduct(menu.categories[0].products[1], documentId = "1000"),
            OrderProduct(menu.categories[1].products[2], documentId = "1001"),
            OrderProduct(menu.categories[2].products[3], documentId = "1002")
        ), "101")

        val o2 = Order(47, listOf(
            OrderProduct(menu.categories[1].products[1], documentId = "1003"),
            OrderProduct(menu.categories[2].products[1], documentId = "1004"),
        ), "102")

        val o3 = Order(29, listOf(
            OrderProduct(menu.categories[0].products[1], documentId = "1005"),
            OrderProduct(menu.categories[2].products[2], documentId = "1006"),
            OrderProduct(menu.categories[1].products[3], documentId = "1007")
        ), "103")

        return Inbox(mutableListOf(o1, o2, o3), "100")
    }
}
