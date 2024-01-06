package database.repository

import util.Constants.CONNECTION_URL
import util.Constants.DRIVER
import util.Constants.PASSWORD
import util.Constants.USERNAME
import util.Mode
import java.sql.DriverManager

class UsersDatabaseRepository {

    suspend fun signIn(
        login:String,
        password:String,
        mode:String
    ):Boolean{
        val dbName = if (mode== Mode.Admin.name){
            "admin"
        }
        else{
            "data_analyst"
        }
        val query = "SELECT * FROM $dbName WHERE Login = ? AND Password = ?"
        try{
            Class.forName(DRIVER)
            val connection  = DriverManager.getConnection(
                CONNECTION_URL,
                USERNAME,
                PASSWORD
            )
            val stmnt = connection.prepareStatement(query)
            stmnt.setString(1,login)
            stmnt.setString(2,password)
            val resultSet = stmnt.executeQuery()
            resultSet.next()
            resultSet.getString("Login")
            resultSet.getString("Password")
            return true
        }
        catch (e:Exception){
            return false
        }
    }

}