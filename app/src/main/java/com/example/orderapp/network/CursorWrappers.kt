package com.example.orderapp.network

import android.database.Cursor
import android.database.CursorWrapper
import com.example.orderapp.types.Order
import com.example.orderapp.types.OrderProduct
import com.example.orderapp.types.Product

class ProductDTO(val id: String, val name: String, private val prize: String, val category: String) {
    fun toProduct() = Product(id.toInt(), name, prize.toInt())
}

class OrderProductDTO(val productId: String, val orderId: String, private val quantity: String) {
    fun toOrderProduct(product: Product): OrderProduct {
        val orderProduct = OrderProduct(product)
        orderProduct.quantity = quantity.toInt()
        return orderProduct
    }
}

class OrderDTO(val id: String, private val tableNumber: String) {
    fun toOrder(products: List<OrderProduct>): Order {
        val order = Order(id.toInt(), tableNumber.toInt())
        order.products.addAll(products)
        return order
    }
}

class ProductCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    val product: ProductDTO
        get() {
            val id = getString(getColumnIndex(DbSchema.Products.ID))
            val name = getString(getColumnIndex(DbSchema.Products.NAME))
            val prize = getString(getColumnIndex(DbSchema.Products.PRIZE))
            val category = getString(getColumnIndex(DbSchema.Products.CATEGORY))
            return ProductDTO(id, name, prize, category)
        }
}

class OrderProductCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    val orderProduct: OrderProductDTO
        get() {
            val productId = getString(getColumnIndex(DbSchema.OrderProducts.PRODUCT_ID))
            val orderId = getString(getColumnIndex(DbSchema.OrderProducts.ORDER_ID))
            val quantity = getString(getColumnIndex(DbSchema.OrderProducts.QUANTITY))
            return OrderProductDTO(productId, orderId, quantity)
        }
}

class OrderCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    val order: OrderDTO
        get() {
            val id = getString(getColumnIndex(DbSchema.Orders.ID))
            val tableNumber = getString(getColumnIndex(DbSchema.Orders.TABLE_NUMBER))
            return OrderDTO(id, tableNumber)
        }
}
