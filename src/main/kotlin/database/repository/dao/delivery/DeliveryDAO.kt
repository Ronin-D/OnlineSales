package database.repository.dao.delivery

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import model.Delivery
interface DeliveryDAO {
    suspend fun addDelivery(delivery: Delivery)
    suspend fun getDeliveries(): Flow<Delivery>
    suspend fun editDelivery(updatedDelivery: Delivery)
    suspend fun deleteDelivery(delivery: Delivery)
    suspend fun getAllDeliveriesByDate(date: LocalDate):Flow<Delivery>
}