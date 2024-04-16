package com.example.orderapp.types

//TODO maybe a data class
class Product(val id: Int, val name: String, val prize: Int)
class Category(val id: Int, val name: String, val products: List<Product>)
class Menu(val categories: List<Category>)

class OrderProduct(val product: Product) {
    var quantity: Int = 1

    fun totalPrize(): Int {
        return quantity * product.prize
    }
}

class Order(val id: Int, val tableNumber: Int) {
    val products: MutableList<OrderProduct> = mutableListOf()
}
