package navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import ui.auth.AuthScreen
import ui.auth.AuthViewModel
import ui.home.HomeScreen
import ui.online_shops.OnlineShopsScreen
import ui.products.ProductsScreen

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.AuthChild -> {
                AuthScreen(
                    viewModel = child.component
                )
            }
            is RootComponent.Child.HomeChild -> {
                HomeScreen(
                    viewModel = child.component
                )
            }

            is RootComponent.Child.OnlineShopsChild -> {
                OnlineShopsScreen(
                    viewModel = child.component
                )
            }

            is RootComponent.Child.ProductChild -> {
                ProductsScreen(
                    viewModel = child.component
                )
            }
        }
    }
}