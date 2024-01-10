package ui.products

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import database.repository.dao.product.ProductDAOImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.product.Product
import util.Mode
import util.Screens

class ProductsViewModel(
    componentContext: ComponentContext,
    val onNavigateBack:()->Unit,
    val workMode: Mode
): ComponentContext by componentContext {

    private val productDAOImpl = ProductDAOImpl()

    private val _products = MutableStateFlow(mutableStateListOf<Product>())
    val products = _products.asStateFlow()
    val isAddProductDialogVisible = mutableStateOf(false)
    val isEditProductDialogVisible = mutableStateOf(false)
    val selectedProduct = mutableStateOf<Product?>(null)

    fun loadProducts(){
        CoroutineScope(Dispatchers.IO).launch{
           productDAOImpl.getProducts().collect{
               _products.value.add(it)
           }
        }
    }

    fun addProduct(product: Product){
        CoroutineScope(Dispatchers.IO).launch {
            productDAOImpl.addProduct(product)
            reloadProducts()
        }
    }

    fun updateProduct(id:String, updatedProduct: Product){
        CoroutineScope(Dispatchers.IO).launch {
            productDAOImpl.editProduct(id,updatedProduct)
            reloadProducts()
        }
    }

    fun deleteProduct(){
        CoroutineScope(Dispatchers.IO).launch {
            productDAOImpl.deleteProduct(selectedProduct.value!!.id)
            reloadProducts()
        }
    }

    private fun reloadProducts(){
        _products.value.clear()
        loadProducts()
    }
}