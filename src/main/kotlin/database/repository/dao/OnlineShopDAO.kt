package database.repository.dao

import kotlinx.coroutines.flow.Flow
import model.OnlineShop

interface OnlineShopDAO {
    suspend fun addShop(shop: OnlineShop)
    suspend fun deleteShop(email:String)
    suspend fun getShops(): Flow<OnlineShop>
    suspend fun updateShop(
        email: String,
        updatedShop: OnlineShop
    )
}