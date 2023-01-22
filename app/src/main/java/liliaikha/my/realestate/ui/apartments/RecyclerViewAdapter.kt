package liliaikha.my.realestate.ui.apartments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import liliaikha.my.realestate.R
import liliaikha.my.realestate.database.ApartmentInfo
import liliaikha.my.realestate.databinding.ItemBinding
import liliaikha.my.realestate.ui.item.ItemFragment
import liliaikha.my.realestate.ui.main.MainFragmentViewModel
import java.text.DecimalFormat
import kotlin.math.roundToInt

class RecyclerViewAdapter(
    private var apartmentsList: List<ApartmentInfo>,
    private val fragmentManager: FragmentManager,
    private val viewModel: MainFragmentViewModel
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val formatter = DecimalFormat("#,###")

    inner class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = apartmentsList[position]
        with(viewHolder) {
            val price = formatter.format(item.price) + "руб"
            val infoText = """
                Количество комнат-${item.roomCount?.roundToInt()}
                Общая площадь-${item.totalArea}м2
                Кухонная площадь-${
                    if (item.kitchenArea != null && item.kitchenArea == 0.0) 
                        "не указана" 
                    else 
                        "${item.kitchenArea}м2"
                }
                Этаж-${item.floor?.roundToInt()}
            """.trimIndent()

            with(binding) {
                textViewCity.text = item.city
                textViewAddress.text = item.adress
                textViewMain.text = infoText
                textViewPrice.text = price
                root.setOnClickListener {
                    fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, ItemFragment.getInstance(item))
                        .addToBackStack(ItemFragment.toString())
                        .commit()
                    viewModel.setFlatState()
                }
            }
        }
    }

    override fun getItemCount() = apartmentsList.size
}