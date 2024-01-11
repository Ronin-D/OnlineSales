package database.repository.dao.online_shop

import kotlinx.coroutines.flow.Flow
import model.OnlineShop
import java.sql.Connection

interface OnlineShopDAO {
    suspend fun addShop(shop: OnlineShop)
    suspend fun deleteShop(email:String)
    suspend fun getShops(): Flow<OnlineShop>
    suspend fun getShopsInstantly():List<OnlineShop>
    suspend fun updateShop(
        email: String,
        updatedShop: OnlineShop
    )

    suspend fun getShop(email: String,connection: Connection):OnlineShop

}