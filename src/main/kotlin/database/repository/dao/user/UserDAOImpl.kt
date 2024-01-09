package database.repository.dao.user

import model.User
import java.sql.Connection
import java.util.UUID

class UserDAOImpl:UserDAO {
    override suspend fun getUser(id: String, connection: Connection): User {
        val query = "SELECT * FROM customer WHERE Id=?"
        val stmnt = connection.prepareStatement(query)
        stmnt.setString(1,id)
        val resultSet = stmnt.executeQuery()
        resultSet.next()
        return User(
            id = resultSet.getString("Id"),
            name = resultSet.getString("Name"),
            surname = resultSet.getString("Surname"),
            patronymic = resultSet.getString("Patronymic")
        )
    }

    override suspend fun addUser(user: User, connection: Connection) {
        val query = "INSERT INTO customer(Id,Name,Surname,Patronymic) VALUES (?,?,?,?)"
        val stmnt = connection.prepareStatement(query)
        stmnt.setString(1,user.id)
        stmnt.setString(2,user.name)
        stmnt.setString(3,user.surname)
        stmnt.setString(4,user.patronymic)
        stmnt.executeUpdate()
    }

    override suspend fun editUser(updatedUser: User, connection: Connection) {
        val query = "UPDATE customer SET Name=?, Surname=?, Patronymic=? WHERE Id=?"
        val stmnt = connection.prepareStatement(query)
        stmnt.setString(1,updatedUser.name)
        stmnt.setString(2,updatedUser.surname)
        stmnt.setString(3,updatedUser.patronymic)
        stmnt.setString(4,updatedUser.id)
        stmnt.executeUpdate()
    }

}