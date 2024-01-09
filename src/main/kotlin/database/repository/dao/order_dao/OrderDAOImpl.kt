package database.repository.dao.order_dao

import database.repository.dao.online_shop.OnlineShopDAOImpl
import database.repository.dao.product.ProductDAOImpl
import database.repository.dao.user.UserDAOImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalTime
import model.Order
import model.User
import util.Constants.CONNECTION_URL
import util.Constants.DRIVER
import util.Constants.PASSWORD
import util.Constants.USERNAME
import java.sql.Connection
import java.sql.Date
import java.sql.DriverManager
import java.sql.Time

class OrderDAOImpl:OrderDAO {

    private val shopDAOImpl = OnlineShopDAOImpl()
    private val userDAOImpl = UserDAOImpl()
    private val productDAOImpl = ProductDAOImpl()
    override suspend fun addOrder(order: Order) {
        val query = "INSERT INTO `order`(Id, OrderDate, order_time, Count, PhoneNumber, IsAccepted, CustomerId, ShopId, ProductId) VALUES(?,?,?,?,?,?,?,?,?)"
        try {
            Class.forName(DRIVER)
            val connection = DriverManager.getConnection(
                CONNECTION_URL,
                USERNAME,
                PASSWORD
            )
            val stmnt = connection.prepareStatement(query)
            userDAOImpl.addUser(order.customer,connection)
            stmnt.setString(1,order.id)
            stmnt.setDate(2, Date.valueOf(order.orderDate.toJavaLocalDate()))
            stmnt.setTime(3, Time.valueOf(order.orderTime.toJavaLocalTime()))
            stmnt.setInt(4,order.count)
            stmnt.setString(5,order.phoneNumber)
            stmnt.setBoolean(6,order.isAccepted)
            stmnt.setString(7,order.customer.id)
            stmnt.setString(8,order.shopId)
            stmnt.setString(9,order.productId)
            stmnt.executeUpdate()
        } catch (e:Exception) {
          throw RuntimeException(e)
        }
    }

    override suspend fun deleteOrder(orderId: String) {
        val query = "DELETE FROM `order` WHERE Id=?"
        try {
            Class.forName(DRIVER)
            val connection = DriverManager.getConnection(
                CONNECTION_URL,
                USERNAME,
                PASSWORD
            )
            val stmnt = connection.prepareStatement(query)
            stmnt.setString(1,orderId)
            stmnt.execute()
        }catch (e:Exception){
            throw RuntimeException(e)
        }
    }

    override suspend fun editOrder(updatedOrder: Order) {
        val query = "UPDATE `order` SET " +
                "OrderDate=?, order_time=?, Count=?, PhoneNumber=?, IsAccepted=? WHERE Id=?"
        try {
            Class.forName(DRIVER)
            val connection = DriverManager.getConnection(
                CONNECTION_URL,
                USERNAME,
                PASSWORD
            )
            userDAOImpl.editUser(updatedOrder.customer,connection)
            val stmnt = connection.prepareStatement(query)
            stmnt.setDate(1,Date.valueOf(updatedOrder.orderDate.toJavaLocalDate()))
            stmnt.setTime(2,Time.valueOf(updatedOrder.orderTime.toJavaLocalTime()))
            stmnt.setInt(3,updatedOrder.count)
            stmnt.setString(4,updatedOrder.phoneNumber)
            stmnt.setBoolean(5,updatedOrder.isAccepted)
            stmnt.setString(6,updatedOrder.id)
            stmnt.executeUpdate()
        }catch (e:Exception){
            throw RuntimeException(e)
        }
    }

    override suspend fun getOrders(): Flow<Order> = flow{
        val query = "SELECT * FROM `order`"
        try {
            Class.forName(DRIVER)
            val connection = DriverManager.getConnection(
                CONNECTION_URL,
                USERNAME,
                PASSWORD
            )
            val stmnt = connection.prepareStatement(query)
            val resultSet = stmnt.executeQuery()
            while (resultSet.next()){
               val shop = shopDAOImpl.getShop(
                   email = resultSet.getString("ShopId"),
                   connection = connection
               )
                val product = productDAOImpl.getProduct(
                    resultSet.getString("ProductId"),
                    connection
                )
                val user = userDAOImpl.getUser(
                    resultSet.getString("CustomerId"),
                    connection
                )

                emit(
                    Order(
                        id = resultSet.getString("Id"),
                        productId = product.id,
                        shopId = shop.email,
                        customer = user,
                        count = resultSet.getInt("Count"),
                        orderDate = resultSet.getDate("OrderDate").toLocalDate().toKotlinLocalDate(),
                        orderTime = resultSet.getTime("order_time").toLocalTime().toKotlinLocalTime(),
                        isAccepted = resultSet.getBoolean("IsAccepted"),
                        phoneNumber = resultSet.getString("PhoneNumber")
                    )
                )
            }
        }catch (e:Exception){
        throw RuntimeException(e)
    }
    }

    override suspend fun getCustomerFromOrder(id: String, connection: Connection): User {
        val query = "SELECT * FROM `order` WHERE Id=?"
        val stmnt = connection.prepareStatement(query)
        stmnt.setString(1,id)
        val resultSet =stmnt.executeQuery()
        resultSet.next()
        return userDAOImpl.getUser(
            resultSet.getString("CustomerId"),
            connection
        )
    }
}