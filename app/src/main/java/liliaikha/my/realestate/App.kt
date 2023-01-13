package liliaikha.my.realestate

import android.app.Application
import liliaikha.my.realestate.database.ApartmentDatabase
import ru.dgis.sdk.*

class App : Application() {
    lateinit var database: ApartmentDatabase
//    lateinit var sdkContext: Context

    override fun onCreate() {
        super.onCreate()
        database = ApartmentDatabase.getDatabase(applicationContext)

//        sdkContext = DGis.initialize(
//            this
//        )
    }
}