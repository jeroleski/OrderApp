package com.example.orderapp.network

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.lifecycle.ViewModel
import com.example.orderapp.types.Category
import com.example.orderapp.types.Menu
import com.example.orderapp.types.Order
import com.example.orderapp.types.OrderProduct
import com.example.orderapp.types.Product

class DbWrapper : ViewModel() {
    private lateinit var db: SQLiteDatabase

    fun initialize(context: Context) {
        db = DbCreate(context.applicationContext).writableDatabase
    }

    // HELPERS
    private fun getAllMenuProducts() = sequence {
        val cursor = ProductCursorWrapper(db.query(DbSchema.Products.TABLE, null, null, null, null, null, null))
        while (cursor.moveToNext())
            yield(cursor.product)
        cursor.close()
    }

    private fun getAllOrderProduct() = sequence {
        val cursor = OrderProductCursorWrapper(db.query(DbSchema.OrderProducts.TABLE, null, null, null, null, null, null))
        while (cursor.moveToNext())
            yield(cursor.orderProduct)
        cursor.close()
    }

    private fun getAllOrdersOrder() = sequence {
        val cursor = OrderCursorWrapper(db.query(DbSchema.Orders.TABLE, null, null, null, null, null, null))
        while (cursor.moveToNext())
            yield(cursor.order)
        cursor.close()
    }

    // GETTERS
    fun getMenu(): Menu {
        val categoryMap = mutableMapOf<String, MutableList<Product>>()
        for (product in getAllMenuProducts()) {
            val category = categoryMap.getOrPut(product.category) { mutableListOf() }
            category.add(product.toProduct())
        }

        var id = 0
        val menuCategories = categoryMap.map { (category, products) -> id++; Category(id, category, products) }
        return Menu(menuCategories)
    }

    fun getOrders(): MutableList<Order> {
        val allProducts = getAllMenuProducts()

        val orderMap = mutableMapOf<String, MutableList<OrderProduct>>()
        for (orderProduct in getAllOrderProduct()) {
            val product = allProducts.find { p -> p.id == orderProduct.productId } ?: continue

            val order = orderMap.getOrPut(orderProduct.orderId) { mutableListOf() }
            val op = orderProduct.toOrderProduct(product.toProduct())
            order.add(op)
        }

        val orders = mutableListOf<Order>()
        for (order in getAllOrdersOrder()) {
            val products = orderMap[order.id] ?: continue
            orders.add(order.toOrder(products))
        }
        return orders
    }

    // SETTERS
    fun addMenuProduct(product: Product, category: Category) {
        val values = ContentValues()
        values.put(DbSchema.Products.ID, product.id)
        values.put(DbSchema.Products.NAME, product.name)
        values.put(DbSchema.Products.PRIZE, product.prize)
        values.put(DbSchema.Products.CATEGORY, category.name)
        db.insert(DbSchema.Products.TABLE, null, values)
    }

    private fun addOrderProduct(orderProduct: OrderProduct, orderId: Int) {
        val values = ContentValues()
        values.put(DbSchema.OrderProducts.PRODUCT_ID, orderProduct.product.id)
        values.put(DbSchema.OrderProducts.ORDER_ID, orderId)
        values.put(DbSchema.OrderProducts.QUANTITY, orderProduct.quantity)
        db.insert(DbSchema.OrderProducts.TABLE, null, values)
    }

    fun addOrder(order: Order) {
        val values = ContentValues()
        values.put(DbSchema.Orders.ID, order.id)
        values.put(DbSchema.Orders.TABLE_NUMBER, order.tableNumber)
        db.insert(DbSchema.Orders.TABLE, null, values)

        for (orderProduct in order.products)
            addOrderProduct(orderProduct, order.id)
    }
}

class DbCreate(context: Context): SQLiteOpenHelper(context, NAME, null, VERSION) {
    companion object {
        const val NAME = "order_app.db"
        const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table ${DbSchema.Products.TABLE}(${DbSchema.Products.ID}, ${DbSchema.Products.NAME}, ${DbSchema.Products.PRIZE}, ${DbSchema.Products.CATEGORY})")
        db.execSQL("create table ${DbSchema.OrderProducts.TABLE}(${DbSchema.OrderProducts.PRODUCT_ID}, ${DbSchema.OrderProducts.ORDER_ID}, ${DbSchema.OrderProducts.QUANTITY})")
        db.execSQL("create table ${DbSchema.Orders.TABLE}(${DbSchema.Orders.ID}, ${DbSchema.Orders.TABLE_NUMBER})")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists ${DbSchema.Products.TABLE}")
        db.execSQL("drop table if exists ${DbSchema.OrderProducts.TABLE}")
        db.execSQL("drop table if exists ${DbSchema.Orders.TABLE}")
        onCreate(db)
    }
}

object DbSchema {
    object Products {
        const val TABLE = "Products"
        const val ID = "id"
        const val NAME = "name"
        const val PRIZE = "prize"
        const val CATEGORY = "category"
    }

    object OrderProducts {
        const val TABLE = "OrderProduct"
        const val PRODUCT_ID = "product_id"
        const val ORDER_ID = "order_id"
        const val QUANTITY = "quantity"
    }

    object Orders {
        const val TABLE = "Orders"
        const val ID = "id"
        const val TABLE_NUMBER = "table_number"
    }
}
