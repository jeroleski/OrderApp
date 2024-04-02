package com.example.orderapp

//TODO maybe a data class
class Product(val name: String, val prize: Int)
class Category(val type: String, val products: List<Product>)
class Menu(val categories: List<Category>)

class OrderProduct(val product: Product) {
    var quantity: UInt = 1u
}

object Guest {
    var table: UInt = 0u
    var order: MutableList<OrderProduct> = mutableListOf()

    fun addProduct(p: Product) {
        val o = order.find {o -> o.product == p}

        if (o == null)
            order.add(OrderProduct(p))
        else
            o.quantity++
    }

    fun removeProduct(p: Product) {
        val o = order.find { o -> o.product == p} ?: return

        if (o.quantity == 1u)
            order.remove(o)
        else
            o.quantity--
    }
}
