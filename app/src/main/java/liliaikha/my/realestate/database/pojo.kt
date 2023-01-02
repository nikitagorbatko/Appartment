package liliaikha.my.realestate.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "apartment_info", primaryKeys = ["IdPoryadk", "Id"])
data class ApartmentInfo(
    @ColumnInfo(name = "IdPoryadk") val idPoryadk: Int,
    @ColumnInfo(name = "Id") val id: Int,
    @ColumnInfo(name = "Image") val image: String?,
    @ColumnInfo(name = "Region") val region: String?,
    @ColumnInfo(name = "City") val city: String?,
    @ColumnInfo(name = "Adress") val adress: String?,
    @ColumnInfo(name = "RoomCount", defaultValue = "NULL") val roomCount: Double? = null,
    @ColumnInfo(name = "TotalArea", defaultValue = "NULL") val totalArea: Double? = null,
    @ColumnInfo(name = "LivingArea", defaultValue = "NULL") val livingArea: Double? = null,
    @ColumnInfo(name = "KitchenArea", defaultValue = "NULL") val kitchenArea: Double? = null,
    @ColumnInfo(name = "Floor", defaultValue = "NULL") val floor: Double? = null,
    @ColumnInfo(name = "CeilingHeight", defaultValue = "NULL") val ceilingHeight: Double? = null,
    @ColumnInfo(name = "PhoneNumber", defaultValue = "NULL") val phoneNumber: Double? = null,
    @ColumnInfo(name = "Price") val price: Int? = null,
    @ColumnInfo(name = "LinkSellerId") val linkSellerId: String,
)

