package database.repository.dao

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.OnlineShop
import util.Constants
import util.Constants.CONNECTION_URL
import util.Constants.DRIVER
import util.Constants.PASSWORD
import util.Constants.USERNAME
import java.sql.DriverManager
import java.sql.SQLException

class OnlineShopDAOImpl:OnlineShopDAO {
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
}