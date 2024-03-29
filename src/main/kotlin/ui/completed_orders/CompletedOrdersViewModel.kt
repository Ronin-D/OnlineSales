package ui.completed_orders

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import database.repository.dao.CompletedOrdersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import model.CompletedOrder
import model.product.Product

class CompletedOrdersViewModel (
    componentContext: ComponentContext,
    val onNavigateBack:()->Unit
): ComponentContext by componentContext {
    val date = mutableStateOf<LocalDate?>(null)

    private val completedOrdersRepository = CompletedOrdersRepository()
    val completedOrders = mutableStateOf<List<CompletedOrder>>(emptyList())

    private val usedProducts = mutableSetOf<String>()

    fun loadCompletedOrders(date: LocalDate){
        CoroutineScope(Dispatchers.IO).launch {
            completedOrders.value = completedOrdersRepository.getCompletedOrders(date)
        }
    }

    fun getSummary(completedOrders: List<CompletedOrder>):Int{
        var sum = 0
        completedOrders.forEach {
            sum+=it.product.price*it.order.count
        }
        return sum
    }

    fun getAllCompletedOrdersByProduct(product: Product):List<CompletedOrder>{
        if(!usedProducts.contains(getMapKey(product))){
            val items =  completedOrders.value.filter {
                it.product.model==product.model && it.product.name==product.name
                        && it.product.manufacturer==product.manufacturer
            }
            usedProducts.add(getMapKey(product))
            return items
        }
        else{
            return emptyList()
        }

    }

    private fun getMapKey(product: Product):String{
        return "name:${product.name} manufacturer:${product.manufacturer} model:${product.model}"
    }
}