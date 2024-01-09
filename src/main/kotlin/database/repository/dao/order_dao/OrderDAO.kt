package database.repository.dao.order_dao

import kotlinx.coroutines.flow.Flow
import model.Order

interface OrderDAO {
    suspend fun addOrder(order: Order)
    suspend fun deleteOrder(orderId:String)
    suspend fun editOrder(updatedOrder: Order)
    suspend fun getOrders():Flow<Order>
}