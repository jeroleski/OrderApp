package com.example.orderapp.types

class Product {
    var documentId: String = ""
    lateinit var name: String
    var prize: Int = 0

    constructor(name: String, prize: Int, documentId: String = "") {
        this.documentId = documentId
        this.name = name
        this.prize = prize
    }

    constructor()
}

class Category {
    var documentId: String = ""
    lateinit var name: String
    lateinit var products: List<Product>

    constructor(name: String, products: List<Product>, documentId: String = "") {
        this.documentId = documentId
        this.name = name
        this.products = products
    }

    constructor()
}

class Menu {
    var documentId: String = ""
    var categories: List<Category> = listOf()

    constructor(categories: List<Category>, documentId: String = "") {
        this.documentId = documentId
        this.categories = categories
    }

    constructor()
}

class OrderProduct {
    var documentId: String = ""
    lateinit var product: Product
    var quantity: Int = 1

    constructor(product: Product, documentId: String = "") {
        this.documentId = documentId
        this.product = product
    }

    constructor()

    fun totalPrize(): Int {
        return quantity * product.prize
    }
}

class Order {
    var documentId: String = ""
    var tableNumber: Int = 0
    var orderProducts: List<OrderProduct> = listOf()

    constructor(tableNumber: Int, orderProducts: List<OrderProduct>, documentId: String = "") {
        this.documentId = documentId
        this.tableNumber = tableNumber
        this.orderProducts = orderProducts
    }

    constructor()
}

class Inbox {
    var documentId: String = ""
    var orders: MutableList<Order> = mutableListOf()

    constructor(orders: MutableList<Order>, documentId: String = "") {
        this.documentId = documentId
        this.orders = orders
    }

    constructor()
}
