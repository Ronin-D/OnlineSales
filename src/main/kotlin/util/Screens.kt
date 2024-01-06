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
    data object Home:Screens("home")
    @Serializable
    data object OnlineShops:Screens("Online shops")
    @Serializable
    data object Orders:Screens("Order list")
    @Serializable
    data object Deliveries:Screens("Deliveries")
    @Serializable
    data object Products:Screens("Products")
    @Serializable
    data object CompletedOrders:Screens("Completed orders")

}