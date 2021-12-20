package com.abdl.mylmk_app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.abdl.mylmk_app.R
import com.abdl.mylmk_app.data.source.local.entity.UserEntity
import com.abdl.mylmk_app.databinding.FragmentProfileUpdateBinding
import com.abdl.mylmk_app.ui.profile.presenter.UpdatePresenter
import com.abdl.mylmk_app.ui.profile.presenter.UpdateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileUpdateFragment : Fragment(R.layout.fragment_profile_update), UpdateView {

    private val profileViewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileUpdateBinding? = null

    private val binding get() = _binding!!

    private lateinit var updatePresenter: UpdatePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileUpdateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        updatePresenter = UpdatePresenter(this)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.user.observe(viewLifecycleOwner, { user ->
            getUser(user)

            binding.apply {
                btnUpdate.setOnClickListener {
                    updateUser(user.id_user)
                }
            }
        })
    }

    private fun updateUser(idUser: Int?) {
        binding.apply {
            val nama = edtNama.text.toString()
            val username = edtUsername.text.toString()
            val nohp = edtPhoneNumber.text.toString()
            val alamat = edtAlamat.text.toString()

            var isEmptyFields = false

            if (nama.isEmpty()) {
                isEmptyFields = true
                edtNama.error = "Nama tidak boleh kosong"
            }

            if (alamat.isEmpty()) {
                isEmptyFields = true
                edtAlamat.error = "Alamat tidak boleh kosong"
            }

            if (nohp.isEmpty()) {
                isEmptyFields = true
                edtPhoneNumber.error = "Nomor HP tidak boleh kosong"
            }

            if (username.isEmpty()) {
                isEmptyFields = true
                edtUsername.error = "Username tidak boleh kosong"
            }

            if (!isEmptyFields) {
                updatePresenter.updateProfile(
                    idUser,
                    nama,
                    username,
                    alamat,
                    nohp
                )
            }
        }
    }

    private fun getUser(user: UserEntity?) {
        with(binding) {
            user?.id_user
            edtNama.setText(user?.nama)
            edtUsername.setText(user?.username)
            edtPhoneNumber.setText(user?.nohp.toString())
            edtAlamat.setText(user?.alamat)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSuccess(msg: String?) {
        Toast.makeText(
            context,
            "Data berhasil diubah, silahkan login ulang untuk melihat perubahan!",
            Toast.LENGTH_LONG
        ).show()
        val action =
            ProfileUpdateFragmentDirections.actionProfileUpdateFragmentToNavigationProfile()
        findNavController().navigate(action)
    }

    override fun onFailed(msg: String?) {
    }
}