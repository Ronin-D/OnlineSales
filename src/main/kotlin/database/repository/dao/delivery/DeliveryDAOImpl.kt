package database.repository.dao.delivery

import database.repository.dao.order_dao.OrderDAOImpl
import database.repository.dao.user.UserDAOImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalTime
import model.Delivery
import util.Constants
import util.Constants.CONNECTION_URL
import util.Constants.DRIVER
import util.Constants.PASSWORD
import util.Constants.USERNAME
import java.sql.Date
import java.sql.DriverManager
import java.sql.Time
import kotlin.math.truncate

class DeliveryDAOImpl:DeliveryDAO {

    val userDAOImpl = UserDAOImpl()
    val orderDAOImpl = OrderDAOImpl()
    override suspend fun addDelivery(delivery: Delivery) {
        val query = "INSERT INTO delivery(Id,Date,Time,CourierName,CustomerId) VALUES(?,?,?,?,?)"
        try {
            Class.forName(DRIVER)
            val connection = DriverManager.getConnection(
                CONNECTION_URL,
                USERNAME,
                PASSWORD
            )
            val customer = orderDAOImpl.getCustomerFromOrder(delivery.id,connection)
            val stmnt = connection.prepareStatement(query)
            stmnt.setString(1,delivery.id)
            stmnt.setDate(2,Date.valueOf(delivery.date.toJavaLocalDate()))
            stmnt.setTime(3,Time.valueOf(delivery.time.toJavaLocalTime()))
            stmnt.setString(4,delivery.courierName)
            stmnt.setString(5,customer.id)
            stmnt.executeUpdate()
        } catch (e:Exception){
            throw RuntimeException(e)
        }
    }

    override suspend fun getDeliveries(): Flow<Delivery> = flow{
        val query = "SELECT * FROM delivery"
        try {
            Class.forName(DRIVER)
            val connection = DriverManager.getConnection(
                CONNECTION_URL,
                USERNAME,
                PASSWORD
            )
            val stmnt = connection.prepareStatement(query)
            val resultSet= stmnt.executeQuery()
            while (resultSet.next()){
                val user = userDAOImpl.getUser(
                    resultSet.getString("CustomerId"),
                    connection
                )
                emit(
                    Delivery(
                        id = resultSet.getString("Id"),
                        customer = user,
                        date = resultSet.getDate("Date").toLocalDate().toKotlinLocalDate(),
                        time = resultSet.getTime("Time").toLocalTime().toKotlinLocalTime(),
                        courierName = resultSet.getString("CourierName")
                    )
                )
            }
        } catch (e:Exception){
            throw RuntimeException(e)
        }
    }

    override suspend fun editDelivery(updatedDelivery: Delivery) {
        val query = "UPDATE delivery SET Date=?,Time=?,CourierName=? " +
                "WHERE Id=?"
        try {
            Class.forName(DRIVER)
            val connection = DriverManager.getConnection(
                CONNECTION_URL,
                USERNAME,
                PASSWORD
            )
            val stmnt = connection.prepareStatement(query)
            stmnt.setDate(1,Date.valueOf(updatedDelivery.date.toJavaLocalDate()))
            stmnt.setTime(2,Time.valueOf(updatedDelivery.time.toJavaLocalTime()))
            stmnt.setString(3,updatedDelivery.courierName)
            stmnt.setString(4,updatedDelivery.id)
            stmnt.executeUpdate()
        } catch (e:Exception){
            throw RuntimeException(e)
        }
    }

    override suspend fun deleteDelivery(delivery: Delivery) {
        val query = "DELETE FROM delivery WHERE Id=?"
        try {
            Class.forName(DRIVER)
            val connection = DriverManager.getConnection(
                CONNECTION_URL,
                USERNAME,
                PASSWORD
            )
            val stmnt = connection.prepareStatement(query)
            stmnt.setString(1,delivery.id)
            stmnt.execute()
        } catch (e:Exception){
            throw RuntimeException(e)
        }
    }
}