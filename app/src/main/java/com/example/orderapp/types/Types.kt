package com.example.orderapp

//TODO maybe a data class
class Product(val id: Int, val name: String, val prize: Int)
class Category(val id : Int, val name: String, val products: List<Product>)
class Menu(val categories: List<Category>)

class OrderProduct(val product: Product) {
    var quantity: Int = 1
}

object Guest {
    var table: Int = 0
    var order: MutableList<OrderProduct> = mutableListOf()

    fun count(p: Product): Int {
        val o = order.find {o -> o.product == p} ?: return 0
        return o.quantity
    }

    fun addProduct(p: Product) {
        val o = order.find {o -> o.product == p}

        if (o == null)
            order.add(OrderProduct(p))
        else
            o.quantity++
    }

    fun removeProduct(p: Product) {
        val o = order.find { o -> o.product == p} ?: return

        if (o.quantity <= 1)
            order.remove(o)
        else
            o.quantity--
    }


    fun totalCount() = order.fold(0) { acc, op -> acc + op.quantity }

    fun totalPrice() = order.fold(0) { acc, op -> acc + (op.quantity * op.product.prize) }
}
