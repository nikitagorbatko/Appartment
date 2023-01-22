package liliaikha.my.realestate.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apartment_info", primaryKeys = ["IdPoryadk", "Id"])
data class ApartmentInfo(
    @ColumnInfo(name = "IdPoryadk") val idPoryadk: Int,
    @ColumnInfo(name = "Id") val id: Int,
    @ColumnInfo(name = "Object") val obj: String?,
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
    @ColumnInfo(name = "Bathroom") val bathroom: String? = null,
    @ColumnInfo(name = "BalconyType") val balconyType: String? = null,
    @ColumnInfo(name = "Repair") val repair: String? = null,
    @ColumnInfo(name = "WindowView") val windowView: String? = null,
    @ColumnInfo(name = "Layout") val layout: String? = null,
    @ColumnInfo(name = "LinkSellerId") val linkSellerId: String,
    //Description field was removed from database file due to the big size of it.
)

@Entity(tableName = "dynamic_info")
data class DynamicInfo(
    @PrimaryKey
    @ColumnInfo(name = "Id") val id: Int,
    @ColumnInfo(name = "Month") val month: String,
    @ColumnInfo(name = "Year") val year: String,
    @ColumnInfo(name = "City") val city: String,
    @ColumnInfo(name = "PrimaryPrice") val primaryPrice: Int,
    @ColumnInfo(
        name = "NewBuildingOfferCount",
        defaultValue = "NULL"
    ) val newBuildingOfferCount: Int?,
    @ColumnInfo(name = "SecondPrice", defaultValue = "NULL") val secondPrice: Int?,
    @ColumnInfo(name = "SecondOfferCount", defaultValue = "NULL") val secondOfferCount: Int?,
)

