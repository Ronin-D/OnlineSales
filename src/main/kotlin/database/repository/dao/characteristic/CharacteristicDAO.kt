package database.repository.dao.characteristic

import model.product.Characteristic
import java.sql.Connection

interface CharacteristicDAO {
    suspend fun getCharacteristic(id:String, connection:Connection):Characteristic
}