package ui.home

import com.arkivanov.decompose.ComponentContext
import util.Screens

class HomeViewModel(
    componentContext: ComponentContext,
    val onNavigate:(Screens)->Unit
): ComponentContext by componentContext  {
    val items = listOf(
        Screens.Orders,
        Screens.Products,
        Screens.OnlineShops,
        Screens.Deliveries,
        Screens.CompletedOrders
    )

}