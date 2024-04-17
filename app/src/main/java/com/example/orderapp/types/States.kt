package com.example.orderapp.types

import com.example.orderapp.network.DbWrapper
import kotlin.random.Random

object Guest {
    var table: Int = 0
    var order: MutableList<OrderProduct> = mutableListOf()

    fun count(p: Product): Int {
        val o = order.find { o -> o.product == p} ?: return 0
        return o.quantity
    }

    fun addProduct(p: Product) {
        val o = order.find { o -> o.product == p}

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

    fun submitOrder() {
        //TODO save receipt to gallery
        //TODO order is changing in between submits
        val o = Order(Random(order.size).nextInt(), table, order)
        DbWrapper.addOrder(o)
        order = mutableListOf()
    }

    fun totalCount() = order.fold(0) { acc, op -> acc + op.quantity }

    fun totalPrice() = order.fold(0) { acc, op -> acc + (op.quantity * op.product.prize) }
}

//object Server {
//    val orders: MutableList<Order> = mutableListOf()
//
//    fun filterOrders(orders: List<Order>): MutableList<Order> {
//        //TODO apply filter
//        return orders.toMutableList()
//    }
//
//    fun finishOrder(order: Order) {
//        orders.remove(order)
//        DbWrapper.removeOrder(order)
//    }
//}
