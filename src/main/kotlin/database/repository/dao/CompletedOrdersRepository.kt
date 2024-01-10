package database.repository.dao

import database.repository.dao.delivery.DeliveryDAOImpl
import database.repository.dao.order_dao.OrderDAOImpl
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDate
import model.CompletedOrder

class CompletedOrdersRepository {
    private val deliveryDAOImpl = DeliveryDAOImpl()
    private val orderDAOImpl = OrderDAOImpl()
    suspend fun getCompletedOrders(
        date: LocalDate
    ): List<CompletedOrder>{
        val completedOrders = mutableListOf<CompletedOrder>()
        deliveryDAOImpl.getAllDeliveriesByDate(date).collect { delivery ->
            completedOrders.add(orderDAOImpl.getCompletedOrder(delivery.id))
        }
        return completedOrders
    }
}