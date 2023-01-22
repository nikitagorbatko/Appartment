package liliaikha.my.realestate.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ApartmentDao {
    @Query("SELECT * FROM apartment_info")
    suspend fun getAllApartments(): List<ApartmentInfo>

    @Query("SELECT DISTINCT region FROM apartment_info ORDER BY region ASC")
    suspend fun getRegions(): List<String>

    @Query("SELECT MAX(RoomCount) FROM apartment_info")
    suspend fun getMaxRooms(): Double

    @Query("SELECT MAX(TotalArea) FROM apartment_info")
    suspend fun getMaxArea(): Double

    @Query(
        "SELECT * FROM apartment_info WHERE RoomCount BETWEEN :minRooms AND :maxRooms" +
                " AND TotalArea BETWEEN :minArea AND :maxArea" +
                " AND Region LIKE :region" +
                " AND Price BETWEEN :minPrice AND :maxPrice" +
                " ORDER BY " +
                " CASE :sort WHEN 'RoomCount' THEN RoomCount END ASC," +
                " CASE :sort WHEN 'TotalArea' THEN TotalArea END ASC," +
                " CASE :sort WHEN 'Price' THEN Price END ASC"
    )
    suspend fun getFilteredApartmentsAsc(
        minRooms: Int,
        maxRooms: Int,
        minArea: Int,
        maxArea: Int,
        region: String,
        minPrice: Int,
        maxPrice: Int,
        sort: String,
    ): List<ApartmentInfo>

    @Query(
        "SELECT * FROM apartment_info WHERE RoomCount BETWEEN :minRooms AND :maxRooms" +
                " AND TotalArea BETWEEN :minArea AND :maxArea" +
                " AND Region LIKE :region" +
                " AND Price BETWEEN :minPrice AND :maxPrice" +
                " ORDER BY " +
                " CASE :sort WHEN 'RoomCount' THEN RoomCount END DESC," +
                " CASE :sort WHEN 'TotalArea' THEN TotalArea END DESC," +
                " CASE :sort WHEN 'Price' THEN Price END DESC"
    )
    suspend fun getFilteredApartmentsDesc(
        minRooms: Int,
        maxRooms: Int,
        minArea: Int,
        maxArea: Int,
        region: String,
        minPrice: Int,
        maxPrice: Int,
        sort: String,
    ): List<ApartmentInfo>

    @Query("SELECT * FROM dynamic_info")
    fun getDynamicInfo(): Flow<List<DynamicInfo>>

    @Query("SELECT * FROM dynamic_info WHERE City = :city AND Year = :year")
    fun getDynamicInfo(city: String, year: String): Flow<List<DynamicInfo>>

}