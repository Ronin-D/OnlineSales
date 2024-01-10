package ui.delivery

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import database.repository.dao.delivery.DeliveryDAOImpl
import database.repository.dao.user.UserDAOImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.Delivery
import model.User
import util.Constants
import util.Mode
import java.sql.DriverManager

class DeliveryViewModel(
    componentContext: ComponentContext,
    val onNavigateBack:()->Unit,
    val workMode: Mode
): ComponentContext by componentContext {
    private val deliveryDAOImpl = DeliveryDAOImpl()
    private val _deliveries = MutableStateFlow(mutableStateListOf<Delivery>())
    val deliveries = _deliveries.asStateFlow()
    val selectedDelivery = mutableStateOf<Delivery?>(null)
    val isEditDeliveryDialogVisible = mutableStateOf(false)
    val customer = mutableStateOf<User?>(null)
    fun loadDeliveries(){
        CoroutineScope(Dispatchers.IO).launch {
            deliveryDAOImpl.getDeliveries().collect{
                _deliveries.value.add(it)
            }
        }
    }
    fun editDelivery(updatedDelivery: Delivery){
        CoroutineScope(Dispatchers.IO).launch {
            deliveryDAOImpl.editDelivery(updatedDelivery)
            reloadOrders()
        }
    }
    fun deleteDelivery(){
        CoroutineScope(Dispatchers.IO).launch {
            deliveryDAOImpl.deleteDelivery(selectedDelivery.value!!)
            reloadOrders()
        }
    }
    private fun reloadOrders(){
        _deliveries.value.clear()
        loadDeliveries()
    }
}