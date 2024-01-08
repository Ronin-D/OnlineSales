package model.product

import kotlinx.datetime.LocalDate
import java.sql.Date
data class Product (
    val id:String,
    val name:String,
    val model:String,
    val warrantyDate: LocalDate,
    val price:Int,
    val manufacturer: String,
    val image:String,
    val characteristics: Characteristics
)