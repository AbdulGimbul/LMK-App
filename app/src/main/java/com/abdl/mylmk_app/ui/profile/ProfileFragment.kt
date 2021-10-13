package com.abdl.mylmk_app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abdl.mylmk_app.data.source.local.entity.UserEntity
import com.abdl.mylmk_app.databinding.FragmentProfileBinding
import com.abdl.mylmk_app.ui.auth.AuthViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var profileViewModel: ProfileViewModel
    private val factory: AuthViewModelFactory by instance()
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.user.observe(viewLifecycleOwner, Observer {
            getUser(it)
        })
    }

    private fun getUser(user: UserEntity?) {
        with(binding) {
            tvName.text = user?.nama
            tvAddress.text = user?.alamat
            tvUsername.text = user?.username
            tvPhoneNumber.text = user?.nohp.toString()
            tvGender.text = user?.jk
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}