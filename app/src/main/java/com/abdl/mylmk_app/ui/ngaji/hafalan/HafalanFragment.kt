package com.abdl.mylmk_app.ui.ngaji.hafalan

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdl.mylmk_app.R
import com.abdl.mylmk_app.data.SortOrder
import com.abdl.mylmk_app.data.source.local.entity.HafalanEntity
import com.abdl.mylmk_app.databinding.FragmentHafalanBinding
import com.abdl.mylmk_app.utils.OnQueryTextChanged
import com.abdl.mylmk_app.utils.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HafalanFragment : Fragment(R.layout.fragment_hafalan), HafalanAdapter.OnItemClickListener {

    @Inject
    lateinit var hafalanViewModelFactory: HafalanViewModel.HafalanViewModelFactory

    private lateinit var searchView: SearchView

    private val viewModel: HafalanViewModel by viewModels {
        HafalanViewModel.provideFactory(hafalanViewModelFactory, this, arguments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHafalanBinding.bind(view)

        val hafalanAdapter = HafalanAdapter(this)

        binding.apply {
            rvHafalan.apply {
                adapter = hafalanAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val hafalan = hafalanAdapter.currentList[viewHolder.bindingAdapterPosition]
                    viewModel.onHafalanSwiped(hafalan)
                }
            }).attachToRecyclerView(rvHafalan)

            fabAddHafalan.setOnClickListener {
                viewModel.onAddNewHafalanClick()
            }
        }

        setFragmentResultListener("add_update_request") { _, bundle ->
            val result = bundle.getInt("add_update_result")
            viewModel.onAddUpdateResult(result)
        }

        viewModel.hafalan.observe(viewLifecycleOwner) {
            hafalanAdapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.hafalanEvent.collect { event ->
                when (event) {
                    is HafalanViewModel.HafalanEvent.ShowUndoDeleteHafalanMessage -> {
                        Snackbar.make(requireView(), "Hafalan deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO") {
                                viewModel.onUndoDeleteClcik(event.hafalan)
                            }.show()
                    }
                    is HafalanViewModel.HafalanEvent.NavigateToUpdateHafalanScreen -> {
                        val action =
                            HafalanFragmentDirections.actionHafalanFragmentToHafalanAddUpdateFragment(
                                event.hafalan,
                                "Edit Hafalan"
                            )
                        findNavController().navigate(action)
                    }
                    is HafalanViewModel.HafalanEvent.NavogateToAddHafalanScreen -> {
                        val action =
                            HafalanFragmentDirections.actionHafalanFragmentToHafalanAddUpdateFragment(
                                null,
                                "Tambah Hafalan"
                            )
                        findNavController().navigate(action)
                    }
                    is HafalanViewModel.HafalanEvent.ShowHafalanSavedConfirmationMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_SHORT).show()
                    }
                    HafalanViewModel.HafalanEvent.NavigateToDeleteAllCompletedScreen -> {
                        val action =
                            HafalanFragmentDirections.actionGlobalDeleteAllCompletedDialogFragment()
                        findNavController().navigate(action)
                    }
                }.exhaustive
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onItemClick(hafalan: HafalanEntity) {
        viewModel.onHafalanSelected(hafalan)
    }

    override fun onCheckBoxClick(hafalan: HafalanEntity, isChecked: Boolean) {
        viewModel.onHafalanCheckedChanged(hafalan, isChecked)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.hafalan_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        val pendingQuery = viewModel.searchQuery.value
        if (pendingQuery != null && pendingQuery.isNotEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }

        searchView.OnQueryTextChanged {
            viewModel.searchQuery.value = it
        }

        viewLifecycleOwner.lifecycleScope.launch {
            menu.findItem(R.id.action_hide_completed_task).isChecked =
                viewModel.preferencesFlow.first().hideCompleted
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_by_name -> {
                viewModel.onSortOrderSelected(SortOrder.BY_NAME)
                true
            }
            R.id.action_sort_by_date_created -> {
                viewModel.onSortOrderSelected(SortOrder.BY_DATE)
                true
            }
            R.id.action_hide_completed_task -> {
                item.isChecked = !item.isChecked
                viewModel.onHideCompletedClick(item.isChecked)
                true
            }
            R.id.action_delete_all_completed_task -> {
                viewModel.onDeleteAllCompletedClick()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
    }
}