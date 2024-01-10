package navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ui.auth.AuthViewModel
import ui.completed_orders.CompletedOrdersViewModel
import ui.delivery.DeliveryViewModel
import ui.home.HomeViewModel
import ui.online_shops.OnlineShopsViewModel
import ui.order.OrdersViewModel
import ui.products.ProductsViewModel

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>
    fun onBackClicked(toIndex: Int)
    sealed class Child {
        class AuthChild(val component:AuthViewModel ):Child()
        class HomeChild(val component:HomeViewModel ):Child()
        class OnlineShopsChild(val component: OnlineShopsViewModel):Child()
        class ProductChild(val component: ProductsViewModel):Child()
        class OrdersChild(val component: OrdersViewModel):Child()
        class DeliveriesChild(val component:DeliveryViewModel):Child()
        class CompletedOrdersChild(val component:CompletedOrdersViewModel):Child()
    }
}