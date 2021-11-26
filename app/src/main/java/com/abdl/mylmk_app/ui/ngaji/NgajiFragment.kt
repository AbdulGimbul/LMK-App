package com.abdl.mylmk_app.ui.ngaji

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abdl.mylmk_app.databinding.FragmentNgajiBinding
import com.abdl.mylmk_app.ui.ngaji.guru.MyGuruActivity
import com.abdl.mylmk_app.ui.ngaji.hafalan.HafalanActivity
import com.abdl.mylmk_app.ui.ngaji.jadwal.JadwalActivity
import com.abdl.mylmk_app.ui.ngaji.notes.NoteActivity
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity

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

        _binding?.cvCatatanNgaji?.onClick {
            startActivity<NoteActivity>()
        }

        _binding?.cvJadwalSaya?.onClick {
            startActivity<JadwalActivity>()
        }

        _binding?.cvHafalanSaya?.onClick {
            startActivity<HafalanActivity>()
        }

        _binding?.cvGuruSaya?.onClick {
            startActivity<MyGuruActivity>()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}