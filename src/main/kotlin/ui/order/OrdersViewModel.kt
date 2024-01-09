package ui.order

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import database.repository.dao.order_dao.OrderDAOImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Order

class OrdersViewModel(
    componentContext: ComponentContext,
    val onNavigateBack:()->Unit
): ComponentContext by componentContext {

    private val orderDAOImpl = OrderDAOImpl()
    private val _orders = MutableStateFlow(mutableStateListOf<Order>())
    val orders = _orders.asStateFlow()
    val selectedOrder = mutableStateOf<Order?>(null)
    val isAddOrderDialogVisible = mutableStateOf(false)
    val isEditOrderDialogVisible = mutableStateOf(false)
    fun loadOrders(){
        CoroutineScope(Dispatchers.IO).launch {
            orderDAOImpl.getOrders().collect{
                _orders.value.add(it)
            }
        }
    }
    fun editOrder(updatedOrder: Order){
        CoroutineScope(Dispatchers.IO).launch {
            orderDAOImpl.editOrder(updatedOrder)
            reloadOrders()
        }
    }
    fun deleteOrder(){
        CoroutineScope(Dispatchers.IO).launch {
            orderDAOImpl.deleteOrder(selectedOrder.value!!.id)
            reloadOrders()
        }
    }
    fun addOrder(order: Order){
        CoroutineScope(Dispatchers.IO).launch {
            orderDAOImpl.addOrder(order)
            reloadOrders()
        }
    }
    private fun reloadOrders(){
        _orders.value.clear()
        loadOrders()
    }


}