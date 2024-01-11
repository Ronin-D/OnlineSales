package database.repository.dao.product

import kotlinx.coroutines.flow.Flow
import model.product.Product
import java.sql.Connection

interface ProductDAO {
    suspend fun addProduct(product: Product)
    suspend fun deleteProduct(productId: String)
    suspend fun editProduct(id:String,updatedProduct: Product)
    suspend fun getProducts():Flow<Product>
    suspend fun getProductsInstantly():List<Product>
    suspend fun getProduct(id:String, connection: Connection):Product
}