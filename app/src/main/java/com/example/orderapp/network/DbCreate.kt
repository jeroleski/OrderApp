package com.example.orderapp.network

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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
