package database.repository.dao.characteristic

import model.product.Characteristic
import java.sql.Connection

class CharacteristicDAOImpl:CharacteristicDAO {
    override suspend fun getCharacteristic(id: String, connection: Connection):Characteristic {
        val characteristicsQuery = "SELECT * FROM characteristic WHERE Id = ?"
        val characteristicsStmnt = connection.prepareStatement(characteristicsQuery)
        characteristicsStmnt.setString(1,id)
        val characteristicsResultSet = characteristicsStmnt.executeQuery()
        characteristicsResultSet.next()
        return Characteristic(
            id = characteristicsResultSet.getString("Id"),
            length = characteristicsResultSet.getInt("Length"),
            width = characteristicsResultSet.getInt("Width"),
            height = characteristicsResultSet.getInt("Height")
        )
    }
}