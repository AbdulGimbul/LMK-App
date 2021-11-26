package com.abdl.mylmk_app.ui.ngaji.hafalan

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.abdl.mylmk_app.R
import com.abdl.mylmk_app.databinding.FragmentAddUpdateHafalanBinding
import com.abdl.mylmk_app.utils.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class HafalanAddUpdateFragment : Fragment(R.layout.fragment_add_update_hafalan) {

    @Inject
    lateinit var hafalanAddUpdateViewModelFactory: HafalanAddUpdateViewModel.HafalanAddUpdateViewModelFactory

    private val viewModel: HafalanAddUpdateViewModel by viewModels() {
        HafalanAddUpdateViewModel.provideFactory(hafalanAddUpdateViewModelFactory, this, arguments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddUpdateHafalanBinding.bind(view)

        binding.apply {
            edtHafalan.setText(viewModel.hafalanName)
            checkboxImportant.isChecked = viewModel.hafalanImportance
            tvHafalanCreated.isVisible = viewModel.hafalan != null
            tvHafalanCreated.text = "Created: ${viewModel.hafalan?.createdDateFormatted}"

            edtHafalan.addTextChangedListener {
                viewModel.hafalanName = it.toString()
            }

            checkboxImportant.setOnCheckedChangeListener { _, isChecked ->
                viewModel.hafalanImportance = isChecked
            }

            fabSaveHafalan.setOnClickListener {
                viewModel.onSaveClick()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addUpdateHafalanEvent.collect { event ->
                when (event) {
                    is HafalanAddUpdateViewModel.AddUpdateHafalanEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    is HafalanAddUpdateViewModel.AddUpdateHafalanEvent.NavigateBackWithResult -> {
                        binding.edtHafalan.clearFocus()
                        setFragmentResult(
                            "add_update_request",
                            bundleOf("add_update_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                }.exhaustive
            }
        }
    }
}