package database.repository.dao.product

import kotlinx.coroutines.flow.Flow
import model.product.Product

interface ProductDAO {
    suspend fun addProduct(product: Product)
    suspend fun deleteProduct(productId: String)
    suspend fun editProduct(id:String,updatedProduct: Product)
    suspend fun getProducts():Flow<Product>
}