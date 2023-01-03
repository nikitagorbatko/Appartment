package liliaikha.my.realestate.ui.apartments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import liliaikha.my.realestate.database.ApartmentInfo
import liliaikha.my.realestate.databinding.ItemBinding
import java.text.DecimalFormat
import kotlin.math.roundToInt

class RecyclerViewAdapter(private val list: List<ApartmentInfo>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val formatter = DecimalFormat("#,###")

    inner class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = list[position]
        with(viewHolder) {
            val price = formatter.format(item.price)
            val main =
                "Количество комнат-${item.roomCount?.roundToInt()}\nОбщая площадь-${item.totalArea}м2\nКухонная площадь-${item.kitchenArea}\nЭтаж-${item.floor?.roundToInt()}"
            binding.textViewCity.text = item.city
            binding.textViewAddress.text = item.adress
            binding.textViewMain.text = main
            binding.textViewPrice.text = "$price руб"
//            Glide.with(binding.root)
//                .load(item.image)
//                .override(600, 300)
//                .into(binding.imageView)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = list.size
}