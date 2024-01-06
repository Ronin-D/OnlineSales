package ui.online_shops

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import database.repository.dao.OnlineShopDAOImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import model.OnlineShop
import util.Screens

class OnlineShopsViewModel (
    componentContext: ComponentContext,
    val onNavigateBack:()->Unit
): ComponentContext by componentContext {

    private val onlineShopDAOImpl = OnlineShopDAOImpl()

    private val _onlineShops = MutableStateFlow(mutableStateListOf<OnlineShop>())
    val onlineShops = _onlineShops.asStateFlow()

    fun loadShops(){
        CoroutineScope(Dispatchers.IO).launch {
            onlineShopDAOImpl.getShops().collect{ onlineShop->
                _onlineShops.value.add(onlineShop)
            }
        }
    }
}