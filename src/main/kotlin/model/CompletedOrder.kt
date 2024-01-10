package model

import model.product.Product

data class CompletedOrder (
    val onlineShop: OnlineShop,
    val product: Product,
    val order: Order
)