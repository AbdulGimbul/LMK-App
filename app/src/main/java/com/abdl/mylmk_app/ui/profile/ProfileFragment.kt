package com.abdl.mylmk_app.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.abdl.mylmk_app.R
import com.abdl.mylmk_app.data.source.local.entity.UserEntity
import com.abdl.mylmk_app.data.source.remote.model.UploadRequestBody
import com.abdl.mylmk_app.data.source.remote.model.UploadResponse
import com.abdl.mylmk_app.data.source.remote.services.RetrofitInstance
import com.abdl.mylmk_app.databinding.FragmentProfileBinding
import com.abdl.mylmk_app.utils.getFileName
import com.abdl.mylmk_app.utils.snackbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile), UploadRequestBody.UploadCallback {

    private var selectedImage: Uri? = null

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

        profileViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            getUser(user)

            binding.imgAvatar.setOnClickListener {
                openImageChooser()
            }

            binding.fabAddPhoto.setOnClickListener {
                uploadImage(user.id_user)
            }
        })

        binding.fabEdit.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToProfileUpdateFragment()
            findNavController().navigate(action)
        }
    }

    private fun getUser(user: UserEntity?) {
        with(binding) {
            tvName.text = user?.nama
            tvAddress.text = user?.alamat
            tvUsername.text = user?.username
            tvPhoneNumber.text = user?.nohp.toString()
            tvGender.text = user?.jk
            Glide.with(requireContext())
                .load(user?.avatar)
                .apply(RequestOptions().override(350, 350))
                .into(imgAvatar)
        }
    }

    private fun uploadImage(idUser: Int?) {
        if (selectedImage == null) {
            binding.containerProfileFragment.snackbar("Pilih gambar terlebih dahulu")
            return
        }

        val parcelFileDescriptor =
            activity?.contentResolver?.openFileDescriptor(selectedImage!!, "r", null)
                ?: return
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file =
            File(activity?.cacheDir, requireActivity().contentResolver.getFileName(selectedImage!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        val body = UploadRequestBody(file, "image", this)

        RetrofitInstance.api.uploadImage(
            idUser,
            MultipartBody.Part.createFormData("image", file.name, body),
            RequestBody.create("text/plain".toMediaTypeOrNull(), "PUT")
        ).enqueue(object : retrofit2.Callback<UploadResponse> {
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                binding.containerProfileFragment.snackbar(t.message!!)
            }

            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                binding.containerProfileFragment.snackbar(response.body()?.message.toString())
            }
        })
    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            getResult.launch(it)
        }
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            selectedImage = it.data?.data
            if (selectedImage != null) {
                Glide.with(requireContext())
                    .load(selectedImage)
                    .apply(RequestOptions().override(350, 350))
                    .into(binding.imgAvatar)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProgressUpdate(percentage: Int) {

    }

    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
    }
}