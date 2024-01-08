package navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ui.auth.AuthViewModel
import ui.home.HomeViewModel
import ui.online_shops.OnlineShopsViewModel
import ui.products.ProductsViewModel

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    // It's possible to pop multiple screens at a time on iOS
    fun onBackClicked(toIndex: Int)

    // Defines all possible child components
    sealed class Child {
        class AuthChild(val component:AuthViewModel ):Child()
        class HomeChild(val component:HomeViewModel ):Child()
        class OnlineShopsChild(val component: OnlineShopsViewModel):Child()
        class ProductChild(val component: ProductsViewModel):Child()
    }
}