package ui.home

import com.arkivanov.decompose.ComponentContext
import util.Mode
import util.Screens
import util.Screens.Orders

class HomeViewModel(
    componentContext: ComponentContext,
    val onNavigate:(Screens)->Unit,
    workMode:Mode
): ComponentContext by componentContext  {
    val items = listOf(
        Screens.Orders(workMode = workMode),
        Screens.Products(workMode = workMode),
        Screens.OnlineShops(workMode = workMode),
        Screens.Deliveries(workMode = workMode),
        Screens.CompletedOrders
    )

}