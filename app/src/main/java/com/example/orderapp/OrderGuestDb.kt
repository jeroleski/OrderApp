package com.example.orderapp

import com.example.orderapp.types.Category
import com.example.orderapp.types.Guest
import com.example.orderapp.types.Menu
import com.example.orderapp.types.Order
import com.example.orderapp.types.Product
import com.example.orderapp.types.Server

object MenuDb {
    var menu: Menu = menuMockData() //TODO fetch the menu from the db

}

object OrderDb {
    fun submitOrder() {
        //TODO submit the order to the db
        //TODO set id

        val o = Order(0, Guest.table)
        o.products.addAll(Guest.order)
        Server.orders.add(o)
        Guest.order.clear()
    }
}

fun menuMockData(): Menu {
    val p1 = Product(0, "Pizza Margarita", 51)
    val p2 = Product(1, "Pizza Pepperoni ", 52)
    val p3 = Product(2, "Pizza Hawaii", 53)
    val p4 = Product(3, "Pizza Parma", 54)
    val p5 = Product(4, "Pizza Potato", 55)
    val pizza = Category(0, "Pizza", listOf(p1, p2, p3, p4, p5))

    val d1 = Product(10, "Tiramisu", 40)
    val d2 = Product(11, "Apple Pie", 50)
    val d3 = Product(12, "Banana Split", 60)
    val d4 = Product(12, "Neapolitan Ice Cream", 70)
    val d5 = Product(14, "Cheese Slate", 80)
    val dessert = Category(1, "Dessert", listOf(d1, d2, d3, d4, d5))

    val s1 = Product(20, "Coca Cola", 20)
    val s2 = Product(21, "Sprite", 22)
    val s3 = Product(22, "Orange Juice", 20)
    val s4 = Product(23, "Iced Tea", 20)
    val s5 = Product(24, "Pina Colada", 20)
    val drink = Category(2, "Drinks", listOf(s1, s2, s3, s4, s5))

    return Menu(listOf(pizza, dessert, drink))
}
