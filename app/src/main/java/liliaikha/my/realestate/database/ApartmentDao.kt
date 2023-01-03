package liliaikha.my.realestate.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ApartmentDao {
    @Query("SELECT * FROM apartment_info")
    fun getAllApartments(): Flow<List<ApartmentInfo>>

    @Query("SELECT * FROM dynamic_info")
    fun getDynamicInfo(): Flow<List<DynamicInfo>>

    @Query("SELECT * FROM dynamic_info WHERE City = :city AND Year = :year")
    suspend fun getDynamicInfoSuspend(city: String, year: String): List<DynamicInfo>

    @Query("SELECT * FROM dynamic_info WHERE City = :city AND Year = :year")
    fun getDynamicInfo(city: String, year: String): Flow<List<DynamicInfo>>

    @Query("SELECT DISTINCT City FROM dynamic_info")
    fun getCities(): Flow<List<String>>

    @Query("SELECT DISTINCT Year FROM dynamic_info")
    fun getYears(): Flow<List<String>>
}