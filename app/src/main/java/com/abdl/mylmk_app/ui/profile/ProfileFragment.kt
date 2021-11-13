package com.abdl.mylmk_app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.abdl.mylmk_app.data.source.local.entity.UserEntity
import com.abdl.mylmk_app.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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