package liliaikha.my.realestate.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(version = 1, entities = [ApartmentInfo::class, DynamicInfo::class])
abstract class ApartmentDatabase : RoomDatabase() {
    abstract fun getDao(): ApartmentDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ApartmentDatabase? = null

        fun getDatabase(context: Context): ApartmentDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApartmentDatabase::class.java,
                    "apartment.db"
                ).createFromAsset("apartment.db")
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

