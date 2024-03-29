package database.repository.dao.online_shop

import database.repository.dao.online_shop.OnlineShopDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.OnlineShop
import util.Constants.CONNECTION_URL
import util.Constants.DRIVER
import util.Constants.PASSWORD
import util.Constants.USERNAME
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class OnlineShopDAOImpl: OnlineShopDAO {
    override suspend fun addShop(shop: OnlineShop) {
        val query = "INSERT INTO online_shop(Email, IsDeliveryFree) VALUES(?, ?)"
        try {
            Class.forName(DRIVER)
            val connection = DriverManager.getConnection(
                CONNECTION_URL,
                USERNAME,
                PASSWORD
            )
            val stmnt = connection.prepareStatement(query)
            stmnt.setString(1,shop.email)
            stmnt.setBoolean(2,shop.isDeliveryFree)
            stmnt.executeUpdate()
        }
        catch (e:SQLException){
            //error msg
        }
    }

    override suspend fun deleteShop(email: String) {
        val query = "DELETE FROM online_shop WHERE Email=\'$email\'"
        try {
            Class.forName(DRIVER)
            val connection = DriverManager.getConnection(
                CONNECTION_URL,
                USERNAME,
                PASSWORD
            )
            val stmnt = connection.prepareStatement(query)
            stmnt.execute()
        }catch (e:SQLException){
            //error msg
        }
    }

    override suspend fun getShops(): Flow<OnlineShop> = flow {
        val query = "SELECT * FROM online_shop"
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
                val shop = OnlineShop(
                    resultSet.getString("Email"),
                    resultSet.getBoolean("IsDeliveryFree")
                )
               emit(shop)
            }
        } catch (e:SQLException){
            //error msg
        }

    }

    override suspend fun getShopsInstantly(): List<OnlineShop> {
        val query = "SELECT * FROM online_shop"
        val shops = mutableListOf<OnlineShop>()
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
                val shop = OnlineShop(
                    resultSet.getString("Email"),
                    resultSet.getBoolean("IsDeliveryFree")
                )
                shops.add(shop)
            }
        } catch (e:SQLException){
            //error msg
        }
        return shops
    }

    override suspend fun updateShop(email: String, updatedShop: OnlineShop) {
        val query = "UPDATE online_shop SET IsDeliveryFree=${updatedShop.isDeliveryFree} WHERE Email=\'$email\'"
        try {
            Class.forName(DRIVER)
            val connection = DriverManager.getConnection(
                CONNECTION_URL,
                USERNAME,
                PASSWORD
            )
            val stmnt = connection.prepareStatement(query)
            stmnt.executeUpdate()
        }catch (e:SQLException){
            //error msg
        }
    }

    override suspend fun getShop(email: String, connection: Connection):OnlineShop {
        val shopQuery = "SELECT * FROM online_shop WHERE Email=?"
        val shopStmnt = connection.prepareStatement(shopQuery)
        shopStmnt.setString(1,email)
        val shopResultSet = shopStmnt.executeQuery()
        shopResultSet.next()
        return OnlineShop(
            email = shopResultSet.getString("Email"),
            isDeliveryFree = shopResultSet.getBoolean("IsDeliveryFree")
        )
    }
}