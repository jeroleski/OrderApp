package com.example.orderapp.network

import com.example.orderapp.types.Category
import com.example.orderapp.types.Menu
import com.example.orderapp.types.Order
import com.example.orderapp.types.OrderProduct
import com.example.orderapp.types.Product

object MenuDb {
    var menu: Menu = menuMockData() //TODO fetch the menu from the db
}

object OrderDb {
    var orders: MutableList<Order> = ordersMockData() //TODO fetch the orders from the db

    fun getFilteredOrders(): MutableList<Order> {
        //TODO apply filter
        return orders
    }

    fun finishOrder(order: Order) {
        //TODO use id to remove
        orders.remove(order)
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

fun ordersMockData(): MutableList<Order> {
    val menu = menuMockData()

    val o1 = Order(1, 1)
    o1.products.add(OrderProduct(menu.categories[0].products[1]))
    o1.products.add(OrderProduct(menu.categories[1].products[2]))
    o1.products.add(OrderProduct(menu.categories[2].products[3]))

    val o2 = Order(2, 47)
    o2.products.add(OrderProduct(menu.categories[1].products[1]))
    o2.products.add(OrderProduct(menu.categories[2].products[1]))

    val o3 = Order(3, 29)
    o3.products.add(OrderProduct(menu.categories[0].products[1]))
    o3.products.add(OrderProduct(menu.categories[2].products[2]))
    o3.products.add(OrderProduct(menu.categories[1].products[3]))

    return mutableListOf(o1, o2, o3)
}
