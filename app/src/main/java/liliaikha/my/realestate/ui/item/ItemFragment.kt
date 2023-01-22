package liliaikha.my.realestate.ui.item

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import liliaikha.my.realestate.R
import liliaikha.my.realestate.database.ApartmentInfo
import liliaikha.my.realestate.databinding.FragmentItemBinding
import liliaikha.my.realestate.underline
import java.text.DecimalFormat


class ItemFragment(private val item: ApartmentInfo) : Fragment() {
    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var NO_INFO: String
    private val formatter = DecimalFormat("#,###")

    companion object {
        fun getInstance(apartmentInfo: ApartmentInfo): ItemFragment =
            ItemFragment(apartmentInfo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemBinding.inflate(inflater, container, false)
        NO_INFO = resources.getString(R.string.no_info_string)
        Glide.with(binding.root)
            .load(item.image)
            .placeholder(R.drawable.home_work_default)
            .error(R.drawable.home_work_default)
            .into(binding.image)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            textViewObject.text = item.obj ?: NO_INFO
            textViewAddress.text = item.adress ?: NO_INFO
            textViewRooms.text =
                if (item.roomCount == null || item.roomCount == 0.0) NO_INFO else item.roomCount.toInt()
                    .toString()
            textViewFloor.text =
                if (item.floor == null || item.floor == 0.0) NO_INFO else item.floor.toInt()
                    .toString()
            textViewArea.text =
                if (item.totalArea == null || item.totalArea == 0.0) NO_INFO else item.totalArea.toInt()
                    .toString()
            textViewLivingArea.text =
                if (item.livingArea == null || item.livingArea == 0.0) NO_INFO else item.livingArea.toInt()
                    .toString()
            textViewKitchenArea.text =
                if (item.kitchenArea == null || item.kitchenArea == 0.0) NO_INFO else item.kitchenArea.toInt()
                    .toString()
            textViewHeight.text =
                if (item.ceilingHeight == null || item.ceilingHeight == 0.0) NO_INFO else item.ceilingHeight.toInt()
                    .toString()
            textViewBathroom.text = item.bathroom ?: NO_INFO
            textViewBalconyType.text = item.balconyType ?: NO_INFO
            textViewRepair.text = item.repair ?: NO_INFO
            textViewWindowView.text = item.windowView ?: NO_INFO
            textViewPhoneNumber.text =
                if (item.phoneNumber == null) NO_INFO else item.phoneNumber.toString()
                    .replace(".", "").replace("E10","")
            textViewPrice.text =
                if (item.price == null) NO_INFO else formatter.format(item.price)
                    .toString() + "руб."

//            textViewAddress.movementMethod = LinkMovementMethod.getInstance()
//            textViewAddress.setLinkTextColor(Color.BLUE)
            textViewAddress.underline()
            textViewAddress.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("geo:0,0?q=${item.adress}")
                )
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}