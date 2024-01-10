package util

import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.serialization.Serializable

@Serializable
sealed class Screens (
    val name:String
) : Parcelable {
    @Serializable
    data object Auth:Screens("authentication")
    @Serializable
    data class Home(val workMode: Mode):Screens("home")
    @Serializable
    data class OnlineShops(val workMode: Mode):Screens("Online shops")
    @Serializable
    data class Orders(val workMode: Mode):Screens("Order list")
    @Serializable
    data class Deliveries(val workMode: Mode):Screens("Deliveries")
    @Serializable
    data class Products(val workMode: Mode):Screens("Products")
    @Serializable
    data object CompletedOrders:Screens("Completed orders")

}