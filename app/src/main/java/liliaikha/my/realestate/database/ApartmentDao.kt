package liliaikha.my.realestate.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ApartmentDao {

    @Query("SELECT * FROM apartment_info")
    fun getAllWords(): Flow<List<ApartmentInfo>>
}