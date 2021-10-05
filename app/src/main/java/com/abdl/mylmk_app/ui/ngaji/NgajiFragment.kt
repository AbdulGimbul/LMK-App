package com.abdl.mylmk_app.ui.ngaji

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abdl.mylmk_app.databinding.FragmentNgajiBinding

class NgajiFragment : Fragment() {

    private lateinit var ngajiViewModel: NgajiViewModel
    private var _binding: FragmentNgajiBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ngajiViewModel =
            ViewModelProvider(this).get(NgajiViewModel::class.java)

        _binding = FragmentNgajiBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}