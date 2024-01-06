package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable
import ui.auth.AuthViewModel
import ui.home.HomeViewModel
import ui.online_shops.OnlineShopsViewModel
import util.Screens

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Screens>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Screens.Auth, // The initial child component is List
            handleBackButton = true, // Automatically pop from the stack on back button presses
            childFactory = ::child,
        )

    private fun child(config: Screens, componentContext: ComponentContext): RootComponent.Child =
       when(config){
           Screens.Auth -> {
               RootComponent.Child.AuthChild(
                   AuthViewModel(
                       componentContext = componentContext,
                       onNavigateToHome = {
                           navigation.push(configuration = Screens.Home)
                       }
                   )
               )
           }
           Screens.CompletedOrders -> {
               RootComponent.Child.HomeChild(
                   HomeViewModel(
                       componentContext = componentContext,
                       onNavigate = { screen->
                           navigation.push(configuration = screen)
                       }
                   )
               )
           }
           Screens.Deliveries -> {
               RootComponent.Child.HomeChild(
                   HomeViewModel(
                       componentContext = componentContext,
                       onNavigate = { screen->
                           navigation.push(configuration = screen)
                       }
                   )
               )
           }
           Screens.Home -> {
               RootComponent.Child.HomeChild(
                   HomeViewModel(
                       componentContext = componentContext,
                       onNavigate = { screen->
                           navigation.push(configuration = screen)
                       }
                   )
               )
           }
           Screens.OnlineShops -> {
               RootComponent.Child.OnlineShopsChild(
                   OnlineShopsViewModel(
                       componentContext = componentContext,
                       onNavigateBack = {
                           navigation.pop()
                       }
                   )
               )
           }
           Screens.Orders -> {
               RootComponent.Child.HomeChild(
                   HomeViewModel(
                       componentContext = componentContext,
                       onNavigate = { screen->
                           navigation.push(configuration = screen)
                       }
                   )
               )
           }
           Screens.Products -> {
               RootComponent.Child.HomeChild(
                   HomeViewModel(
                       componentContext = componentContext,
                       onNavigate = { screen->
                           navigation.push(configuration = screen)
                       }
                   )
               )
           }
       }
    override fun onBackClicked(toIndex: Int) {
        navigation.pop()
    }

}