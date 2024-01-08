package ui.online_shops

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import database.repository.dao.online_shop.OnlineShopDAOImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import model.OnlineShop

class OnlineShopsViewModel (
    componentContext: ComponentContext,
    val onNavigateBack:()->Unit
): ComponentContext by componentContext {

    private val onlineShopDAOImpl = OnlineShopDAOImpl()

    private val _onlineShops = MutableStateFlow(mutableStateListOf<OnlineShop>())
    val onlineShops = _onlineShops.asStateFlow()
    val isAddOnlineShopDialogShown = mutableStateOf(false)
    val isEditOnlineShopDialogShown = mutableStateOf(false)
    val selectedOnlineShop = mutableStateOf<OnlineShop?>(null)

    fun loadShops(){
        CoroutineScope(Dispatchers.IO).launch {
            onlineShopDAOImpl.getShops().collect{ onlineShop->
                _onlineShops.value.add(onlineShop)
            }
        }
    }

    fun addShop(shop: OnlineShop){
        CoroutineScope(Dispatchers.IO).launch {
            onlineShopDAOImpl.addShop(shop)
            reloadShops()
        }
    }

    fun deleteShop(){
        CoroutineScope(Dispatchers.IO).launch {
            onlineShopDAOImpl.deleteShop(selectedOnlineShop.value!!.email)
            reloadShops()
        }
    }

    fun updateShop(shop: OnlineShop){
        CoroutineScope(Dispatchers.IO).launch {
            onlineShopDAOImpl.updateShop(selectedOnlineShop.value!!.email,shop)
            reloadShops()
        }
    }

    private fun reloadShops(){
        _onlineShops.value.clear()
        loadShops()
    }

}