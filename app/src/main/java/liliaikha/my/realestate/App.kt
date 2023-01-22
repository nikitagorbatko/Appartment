package liliaikha.my.realestate

import android.app.Application
import android.graphics.Paint
import android.widget.TextView
import liliaikha.my.realestate.database.ApartmentDatabase
//import ru.dgis.sdk.*

class App : Application() {
    lateinit var database: ApartmentDatabase
//    lateinit var sdkContext: Context
//
    override fun onCreate() {
        super.onCreate()
        database = ApartmentDatabase.getDatabase(applicationContext)
//        sdkContext = DGis.initialize(
//            this
//        )
    }
}

fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}