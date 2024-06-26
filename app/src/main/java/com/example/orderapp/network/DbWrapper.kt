package com.example.orderapp.network

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.ViewModel
import com.example.orderapp.network.DbMigrator.assertMigrated
import com.example.orderapp.network.DbMigrator.inboxMockData
import com.example.orderapp.network.DbMigrator.menuMockData
import com.example.orderapp.types.Category
import com.example.orderapp.types.Inbox
import com.example.orderapp.types.Menu
import com.example.orderapp.types.Order
import com.example.orderapp.types.OrderProduct
import com.example.orderapp.types.Product

object DbWrapper : ViewModel() {
    private lateinit var db: SQLiteDatabase
    var menu = menuMockData()
    var inbox = inboxMockData()

    fun initialize(context: Context) {
        db = DbCreate(context.applicationContext).writableDatabase

        menu = readMenu()
        inbox = readInbox()
        assertMigrated()
    }

    fun clear(context: Context) {
        val dbCreate = DbCreate(context.applicationContext)
        dbCreate.onUpgrade(db, DbCreate.VERSION, DbCreate.VERSION)
        db = dbCreate.writableDatabase
    }

    // HELPERS
    private fun readAllMenuProducts() = sequence {
        val cursor = ProductCursorWrapper(db.query(DbSchema.Products.TABLE, null, null, null, null, null, null))
        while (cursor.moveToNext())
            yield(cursor.product)
        cursor.close()
    }

    private fun readAllOrderProduct() = sequence {
        val cursor = OrderProductCursorWrapper(db.query(DbSchema.OrderProducts.TABLE, null, null, null, null, null, null))
        while (cursor.moveToNext())
            yield(cursor.orderProduct)
        cursor.close()
    }

    private fun readAllOrdersOrder() = sequence {
        val cursor = OrderCursorWrapper(db.query(DbSchema.Orders.TABLE, null, null, null, null, null, null))
        while (cursor.moveToNext())
            yield(cursor.order)
        cursor.close()
    }

    // GETTERS
    fun readMenu(): Menu {
        val categoryMap = mutableMapOf<String, MutableList<Product>>()
        for (product in readAllMenuProducts()) {
            val category = categoryMap.getOrPut(product.category) { mutableListOf() }
            category.add(product.toProduct())
        }

        var id = 0
        val menuCategories = categoryMap.map { (category, products) -> id++; Category(category, products, id.toString()) }
        return Menu(menuCategories)
    }

    fun readInbox(): Inbox {
        val allProducts = readAllMenuProducts()

        val orderMap = mutableMapOf<String, MutableList<OrderProduct>>()
        for (orderProduct in readAllOrderProduct()) {
            val product = allProducts.find { p -> p.id == orderProduct.productId } ?: continue

            val order = orderMap.getOrPut(orderProduct.orderId) { mutableListOf() }
            val op = orderProduct.toOrderProduct(product.toProduct())
            order.add(op)
        }

        val orders = mutableListOf<Order>()
        for (order in readAllOrdersOrder()) {
            val products = orderMap[order.id] ?: continue
            orders.add(order.toOrder(products))
        }
        return Inbox(orders)
    }

    // SETTERS
    fun addMenuProduct(product: Product, category: Category) {
        val values = ContentValues()
        values.put(DbSchema.Products.ID, product.documentId)
        values.put(DbSchema.Products.NAME, product.name)
        values.put(DbSchema.Products.PRIZE, product.prize)
        values.put(DbSchema.Products.CATEGORY, category.name)
        db.insert(DbSchema.Products.TABLE, null, values)
    }

    private fun addOrderProduct(orderProduct: OrderProduct, order: Order) {
        val values = ContentValues()
        values.put(DbSchema.OrderProducts.PRODUCT_ID, orderProduct.product.documentId)
        values.put(DbSchema.OrderProducts.ORDER_ID, order.documentId)
        values.put(DbSchema.OrderProducts.QUANTITY, orderProduct.quantity)
        db.insert(DbSchema.OrderProducts.TABLE, null, values)
    }

    fun addOrder(order: Order) {
        val values = ContentValues()
        values.put(DbSchema.Orders.ID, order.documentId)
        values.put(DbSchema.Orders.TABLE_NUMBER, order.tableNumber)
        db.insert(DbSchema.Orders.TABLE, null, values)

        for (orderProduct in order.orderProducts)
            addOrderProduct(orderProduct, order)
    }

    fun removeOrder(order: Order) {
        db.delete(DbSchema.Orders.TABLE, "${DbSchema.Orders.ID} = ?", arrayOf(order.documentId))
        db.delete(DbSchema.OrderProducts.TABLE, "${DbSchema.OrderProducts.ORDER_ID} = ?", arrayOf(order.documentId))
    }
}
