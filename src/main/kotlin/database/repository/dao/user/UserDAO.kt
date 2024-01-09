package database.repository.dao.user

import model.User
import java.sql.Connection

interface UserDAO {
    suspend fun getUser(id:String, connection: Connection):User
    suspend fun addUser(user: User,connection: Connection)
    suspend fun editUser(updatedUser: User, connection: Connection)
}