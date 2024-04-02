package com.example.orderapp

object MenuDb {
    var menu: Menu = menuMockData() //TODO fetch the menu from the db
}

object OrderDb {
    fun submitOrder() {
        //TODO submit the order to the db
        Guest.order.clear()
    }
}

fun menuMockData(): Menu {
    val p1 = Product("Pizza Margarita", 51)
    val p2 = Product("Pizza Pepperoni ", 52)
    val p3 = Product("Pizza Hawaii", 53)
    val p4 = Product("Pizza Parma", 54)
    val p5 = Product("Pizza Potato", 55)
    val pizza = Category("Pizza", listOf(p1, p2, p3, p4, p5))

    val d1 = Product("Tiramisu", 40)
    val d2 = Product("Apple Pie", 50)
    val d3 = Product("Banana Split", 60)
    val d4 = Product("Neapolitan Ice Cream", 70)
    val d5 = Product("Cheese Slate", 80)
    val dessert = Category("Dessert", listOf(d1, d2, d3, d4, d5))

    val s1 = Product("Coca Cola", 20)
    val s2 = Product("Sprite", 22)
    val s3 = Product("Orange Juice", 20)
    val s4 = Product("Iced Tea", 20)
    val s5 = Product("Pina Colada", 20)
    val drink = Category("Drinks", listOf(s1, s2, s3, s4, s5))

    return Menu(listOf(pizza, dessert, drink))
}
