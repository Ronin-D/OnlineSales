package model
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import model.product.Product

data class Order(
    val id:String,
    val orderDate:LocalDate,
    val orderTime:LocalTime,
    val count:Int,
    val phoneNumber:String,
    val isAccepted:Boolean,
    val customer:User,
    val shopId: String,
    val productId: String
)
