package com.abdl.mylmk_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abdl.mylmk_app.databinding.FragmentHomeBinding
import com.abdl.mylmk_app.ui.home.guru.GuruActivity
import com.abdl.mylmk_app.ui.home.pendaftaran.DaftarActivity
import com.abdl.mylmk_app.ui.home.program.ProgramActivity
import com.abdl.mylmk_app.ui.home.tentang.AboutUsActivity
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    //    private lateinit var programViewModel: ProgramViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.cvListGuru?.onClick {
            startActivity<GuruActivity>()
        }
        _binding?.cvProgram?.onClick {
            startActivity<ProgramActivity>()
        }
        _binding?.cvDaftarMurid?.onClick {
            startActivity<DaftarActivity>()
        }
        _binding?.cvTentangLmk?.onClick {
            startActivity<AboutUsActivity>()
        }

//        val factory =
//            ViewModelFactory(MainRepository.getInstance(RemoteDataSource(RetrofitInstance.api)))
//        programViewModel = ViewModelProvider(this, factory)[ProgramViewModel::class.java]
//
//        programViewModel.getAllProgram()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}