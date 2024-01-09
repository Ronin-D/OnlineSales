package database.repository.dao.product

import database.repository.dao.characteristic.CharacteristicDAOImpl
import database.repository.dao.online_shop.OnlineShopDAOImpl
import database.repository.dao.user.UserDAOImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import model.product.Characteristic
import model.product.Product
import util.Constants.CONNECTION_URL
import util.Constants.DRIVER
import util.Constants.PASSWORD
import util.Constants.USERNAME
import java.sql.Connection
import java.sql.Date
import java.sql.DriverManager

class ProductDAOImpl:ProductDAO {

    val characteristicDAOImpl = CharacteristicDAOImpl()
    override suspend fun addProduct(product: Product) {
        val query = "INSERT INTO product(product_id, ProductName, ProductModel, WarrantyDate, Price, Manufacturer, " +
                "Image, CharacteristicId) VALUES(?,?,?,?,?,?,?,?)"
        val characteristicsQuery = "INSERT INTO characteristic(Id,Length,Width,Height) VALUES(?,?,?,?)"
        try {
            val connection = DriverManager.getConnection(
                CONNECTION_URL,
                USERNAME,
                PASSWORD
            )
            val characteristicsStmnt = connection.prepareStatement(characteristicsQuery)
            characteristicsStmnt.setString(1,product.characteristic.id)
            characteristicsStmnt.setInt(2,product.characteristic.length)
            characteristicsStmnt.setInt(3,product.characteristic.width)
            characteristicsStmnt.setInt(4,product.characteristic.height)
            characteristicsStmnt.executeUpdate()
            val stmnt = connection.prepareStatement(query)
            stmnt.setString(1,product.id)
            stmnt.setString(2,product.name)
            stmnt.setString(3,product.model)
            stmnt.setDate(4,Date.valueOf(product.warrantyDate.toJavaLocalDate()))
            stmnt.setInt(5,product.price)
            stmnt.setString(6,product.manufacturer)
            stmnt.setString(7,product.image)
            stmnt.setString(8,product.characteristic.id)
            stmnt.executeUpdate()
        }catch (e:Exception){
            //error msg
            throw RuntimeException(e)
        }
    }

    override suspend fun deleteProduct(productId: String) {
        val query = "DELETE FROM product WHERE product_id=\'$productId\'"
        try {
            Class.forName(DRIVER)
            val connection = DriverManager.getConnection(
                CONNECTION_URL,
                USERNAME,
                PASSWORD
            )
            val stmnt = connection.prepareStatement(query)
            stmnt.execute()
        }catch (e:Exception){
            //error msg
            throw RuntimeException(e)
        }

    }

    override suspend fun editProduct(id: String, updatedProduct: Product) {
        val query = "UPDATE product SET ProductName = ?, " +
                "ProductModel = ?, " +
                "WarrantyDate = ?, " +
                "Price = ?, " +
                "Manufacturer = ?, " +
                "Image = ? " +
                "WHERE product_id=?"
        val characteristicsQuery = "UPDATE characteristic SET Length=?," +
                " Width=?, Height=? WHERE Id=?"
        try {
            Class.forName(DRIVER)
            val connection = DriverManager.getConnection(
                CONNECTION_URL,
                USERNAME,
                PASSWORD
            )
            val characteristicStmnt = connection.prepareStatement(characteristicsQuery)
            characteristicStmnt.setInt(1,updatedProduct.characteristic.length)
            characteristicStmnt.setInt(2,updatedProduct.characteristic.width)
            characteristicStmnt.setInt(3,updatedProduct.characteristic.height)
            characteristicStmnt.setString(4,updatedProduct.characteristic.id)
            characteristicStmnt.executeUpdate()

            val stmnt = connection.prepareStatement(query)
            stmnt.setString(1,updatedProduct.name)
            stmnt.setString(2,updatedProduct.model)
            stmnt.setDate(3,Date.valueOf(updatedProduct.warrantyDate.toJavaLocalDate()))
            stmnt.setInt(4,updatedProduct.price)
            stmnt.setString(5,updatedProduct.manufacturer)
            stmnt.setString(6,updatedProduct.image)
            stmnt.setString(7,id)
            stmnt.executeUpdate()
        } catch (e:Exception){
            //error msg
            throw RuntimeException(e)
        }
    }

    override suspend fun getProducts(): Flow<Product> = flow {
        val query = "SELECT * FROM product"
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
                val characteristic = characteristicDAOImpl.getCharacteristic(
                    id = resultSet.getString("CharacteristicId"),
                    connection=connection
                )
                emit(
                    Product(
                        id = resultSet.getString("product_id"),
                        name = resultSet.getString("ProductName"),
                        manufacturer = resultSet.getString("Manufacturer"),
                        model = resultSet.getString("ProductModel"),
                        warrantyDate = resultSet.getDate("WarrantyDate").toLocalDate().toKotlinLocalDate(),
                        price = resultSet.getInt("Price"),
                        image = resultSet.getString("Image"),
                        characteristic = characteristic
                    )
                )
            }
        }
        catch (e:Exception){
            //error msg
            throw RuntimeException(e)
        }
    }

    override suspend fun getProduct(id: String, connection: Connection): Product {
        val query = "SELECT * FROM product WHERE product_id=?"
        val stmnt = connection.prepareStatement(query)
        stmnt.setString(1,id)
        val resultSet = stmnt.executeQuery()
        resultSet.next()
        return Product(
            id = resultSet.getString("product_id"),
            name = resultSet.getString("ProductName"),
            manufacturer = resultSet.getString("Manufacturer"),
            model = resultSet.getString("ProductModel"),
            warrantyDate = resultSet.getDate("WarrantyDate").toLocalDate().toKotlinLocalDate(),
            price = resultSet.getInt("Price"),
            image = resultSet.getString("Image"),
            characteristic = characteristicDAOImpl.getCharacteristic(
                resultSet.getString("CharacteristicId"),
                connection = connection
            )
        )
    }
}