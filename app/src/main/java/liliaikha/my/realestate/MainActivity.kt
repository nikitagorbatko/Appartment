package liliaikha.my.realestate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import liliaikha.my.realestate.databinding.ActivityMainBinding
import liliaikha.my.realestate.ui.main.MainFragment
//import ru.dgis.sdk.DGis

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            //val sdkContext = DGis.initialize(applicationContext)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            //.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
            .replace(R.id.container, MainFragment.getInstance(binding, application))
            .commit()
    }
}