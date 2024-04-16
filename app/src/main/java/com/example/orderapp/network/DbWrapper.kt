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
    private fun getAllMenuProducts(): List<ProductDTO> {
        val products = mutableListOf<ProductDTO>()
        val cursor = ProductCursorWrapper(db.query(DbSchema.Products.TABLE, null, null, null, null, null, null))
        cursor.moveToFirst()
        while (! cursor.isAfterLast) {
            products.add(cursor.product)
            cursor.moveToNext()
        }
        cursor.close()
        return products
    }

    private fun getAllOrderProduct(): List<OrderProductDTO> {
        val orderProducts = mutableListOf<OrderProductDTO>()
        val cursor = OrderProductCursorWrapper(db.query(DbSchema.OrderProducts.TABLE, null, null, null, null, null, null))
        cursor.moveToFirst()
        while (! cursor.isAfterLast) {
            orderProducts.add(cursor.orderProduct)
            cursor.moveToNext()
        }
        cursor.close()
        return orderProducts
    }

    private fun getAllOrdersOrder(): List<OrderDTO> {
        val orders = mutableListOf<OrderDTO>()
        val cursor = OrderCursorWrapper(db.query(DbSchema.Orders.TABLE, null, null, null, null, null, null))
        cursor.moveToFirst()
        while (! cursor.isAfterLast) {
            orders.add(cursor.order)
            cursor.moveToNext()
        }
        cursor.close()
        return orders
    }

    // GETTERS
    fun getMenu(): Menu {
        val allProducts = getAllMenuProducts()
        val categoryMap = mutableMapOf<String, MutableList<Product>>().withDefault { _ -> mutableListOf() }
        for (p in allProducts) {
            categoryMap.getValue(p.category).add(p.toProduct())
        }

        val menuCategories = mutableListOf<Category>()
        var id = 0
        for ((c, ps) in categoryMap) {
            menuCategories.add(Category(id, c, ps))
            id++
        }

        return Menu(menuCategories)
    }

    fun getOrders(): MutableList<Order> {
        val allProducts = getAllMenuProducts()
        val allOrderProducts = getAllOrderProduct()
        val orderMap = mutableMapOf<String, MutableList<OrderProduct>>().withDefault { _ -> mutableListOf() }
        for (orderProduct in allOrderProducts) {
            val p = allProducts.find { p -> p.id == orderProduct.productId } ?: continue
            val op = orderProduct.toOrderProduct(p.toProduct())
            orderMap.getValue(orderProduct.orderId).add(op)
        }

        val allOrders = getAllOrdersOrder()
        val orders = mutableListOf<Order>()
        for (order in allOrders) {
            val products = orderMap[order.id] ?: continue
            orders.add(order.toOrder(products))
        }

        return orders
    }

    // SETTERS
    fun addMenuProduct(p: Product, c: String) {
        val values = ContentValues()
        values.put(DbSchema.Products.ID, p.id)
        values.put(DbSchema.Products.NAME, p.name)
        values.put(DbSchema.Products.PRIZE, p.prize)
        values.put(DbSchema.Products.CATEGORY, c)
        db.insert(DbSchema.Products.TABLE, null, values)
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

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
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
