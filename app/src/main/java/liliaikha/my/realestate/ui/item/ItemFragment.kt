package liliaikha.my.realestate.ui.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import liliaikha.my.realestate.database.ApartmentInfo
import liliaikha.my.realestate.databinding.FragmentItemBinding

class ItemFragment(private val apartmentInfo: ApartmentInfo) : Fragment() {
    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!

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

        //            Glide.with(binding.root)
//                .load(item.image)
//                .override(600, 300)
//                .into(binding.imageView)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}