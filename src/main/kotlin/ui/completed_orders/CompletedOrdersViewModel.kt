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
    val completedOrders = mutableStateListOf<CompletedOrder>()

    private val usedProducts = mutableSetOf<String>()

    fun loadCompletedOrders(date: LocalDate){
        reloadCompletedOrders()
        CoroutineScope(Dispatchers.IO).launch {
            completedOrders.addAll(completedOrdersRepository.getCompletedOrders(date))
        }
    }

    fun getSummary(completedOrders: List<CompletedOrder>):Int{
        var sum = 0
        completedOrders.forEach {
            for (i in 1..it.order.count){
                sum+=it.product.price
            }

        }
        return sum
    }

    fun getAllCompletedOrdersByProduct(product: Product):List<CompletedOrder>{
        if(!usedProducts.contains(getMapKey(product))){
            val items =  completedOrders.filter {
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
    private fun reloadCompletedOrders(){
        completedOrders.clear()
    }

}