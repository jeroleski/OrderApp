package com.example.orderapp.network

object DbSchema {
    object Products {
        const val TABLE = "Product"
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
