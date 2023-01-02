package liliaikha.my.realestate

import android.app.Application
import androidx.room.Room
import liliaikha.my.realestate.database.ApartmentDatabase

class App: Application() {
    lateinit var database: ApartmentDatabase

    override fun onCreate() {
        super.onCreate()
        database = ApartmentDatabase.getDatabase(applicationContext)
    }
}