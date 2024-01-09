package model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class Delivery (
    val id:String,
    val date:LocalDate,
    val time:LocalTime,
    val courierName:String,
    val customer:User
)