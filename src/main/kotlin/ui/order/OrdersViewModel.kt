package ui.order

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import database.repository.dao.delivery.DeliveryDAOImpl
import database.repository.dao.order_dao.OrderDAOImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.Delivery
import model.Order
import util.Mode

class OrdersViewModel(
    componentContext: ComponentContext,
    val onNavigateBack:()->Unit,
    val workMode: Mode
): ComponentContext by componentContext {

    private val orderDAOImpl = OrderDAOImpl()
    private val _orders = MutableStateFlow(mutableStateListOf<Order>())
    val orders = _orders.asStateFlow()
    val selectedOrder = mutableStateOf<Order?>(null)
    private val deliveryDAOImpl = DeliveryDAOImpl()
    val isAddOrderDialogVisible = mutableStateOf(false)
    val isEditOrderDialogVisible = mutableStateOf(false)
    val isDeliveryAccepted = mutableStateOf(false)
    val orderForDelivery = mutableStateOf<Order?>(null)
    fun loadOrders(){
        CoroutineScope(Dispatchers.IO).launch {
            orderDAOImpl.getOrders().collect{
                _orders.value.add(it)
            }
        }
    }
    fun editOrder(
        updatedOrder: Order,
        delivery: Delivery? = null
    ){
        CoroutineScope(Dispatchers.IO).launch {
            orderDAOImpl.editOrder(updatedOrder)
            delivery?.let {
                deliveryDAOImpl.addDelivery(it)
            }
            reloadOrders()
        }
    }
    fun deleteOrder(){
        CoroutineScope(Dispatchers.IO).launch {
            orderDAOImpl.deleteOrder(selectedOrder.value!!.id)
            reloadOrders()
        }
    }
    fun addOrder(
        order: Order,
        delivery: Delivery?=null
        ){
        CoroutineScope(Dispatchers.IO).launch {
            orderDAOImpl.addOrder(order)
            delivery?.let {
                deliveryDAOImpl.addDelivery(it)
            }
            reloadOrders()
        }
    }
    private fun reloadOrders(){
        _orders.value.clear()
        loadOrders()
    }
}