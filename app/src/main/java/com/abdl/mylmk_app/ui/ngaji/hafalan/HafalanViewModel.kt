package com.abdl.mylmk_app.ui.ngaji.hafalan

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.abdl.mylmk_app.data.PreferencesManager
import com.abdl.mylmk_app.data.SortOrder
import com.abdl.mylmk_app.data.source.local.entity.HafalanEntity
import com.abdl.mylmk_app.data.source.local.room.LmkDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HafalanViewModel @AssistedInject constructor(
    private val lmkDao: LmkDao,
    private val prefencesManager: PreferencesManager,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface HafalanViewModelFactory {
        fun create(handle: SavedStateHandle): HafalanViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: HafalanViewModelFactory,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return assistedFactory.create(handle) as T
                }
            }
    }

    val searchQuery = state.getLiveData("searchQuery", "")

    val preferencesFlow = prefencesManager.preferencesFlow

    private val hafalanEventChannel = Channel<HafalanEvent>()
    val hafalanEvent = hafalanEventChannel.receiveAsFlow()

    private val hafalanFlow = combine(
        searchQuery.asFlow(),
        preferencesFlow
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }.flatMapLatest { (query, filterPreferences) ->
        lmkDao.getHafalan(query, filterPreferences.sortOrder, filterPreferences.hideCompleted)
    }

    val hafalan = hafalanFlow.asLiveData()

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        prefencesManager.updateSortOrder(sortOrder)
    }

    fun onHideCompletedClick(hideCompleted: Boolean) = viewModelScope.launch {
        prefencesManager.updateHideCompleted(hideCompleted)
    }

    fun onHafalanSelected(hafalan: HafalanEntity) = viewModelScope.launch {
        hafalanEventChannel.send(HafalanEvent.NavigateToUpdateHafalanScreen(hafalan))
    }

    fun onHafalanCheckedChanged(hafalan: HafalanEntity, isChecked: Boolean) =
        viewModelScope.launch {
            lmkDao.updateHafalan(hafalan.copy(completed = isChecked))
        }

    fun onHafalanSwiped(hafalan: HafalanEntity) = viewModelScope.launch {
        lmkDao.deleteHafalan(hafalan)
        hafalanEventChannel.send(HafalanEvent.ShowUndoDeleteHafalanMessage(hafalan))
    }

    fun onUndoDeleteClcik(hafalan: HafalanEntity) = viewModelScope.launch {
        lmkDao.insertHafalan(hafalan)
    }

    fun onAddNewHafalanClick() = viewModelScope.launch {
        hafalanEventChannel.send(HafalanEvent.NavogateToAddHafalanScreen)
    }

    fun onAddUpdateResult(result: Int) {
        when (result) {
            ADD_HAFALAN_RESULT_OK -> showHafalanSavedConfirmationMessage("Hafalan added")
            UPDATE_HAFALAN_RESULT_OK -> showHafalanSavedConfirmationMessage("Hafalan updated")
        }
    }

    private fun showHafalanSavedConfirmationMessage(text: String) = viewModelScope.launch {
        hafalanEventChannel.send(HafalanEvent.ShowHafalanSavedConfirmationMessage(text))
    }

    fun onDeleteAllCompletedClick() = viewModelScope.launch {
        hafalanEventChannel.send(HafalanEvent.NavigateToDeleteAllCompletedScreen)
    }

    sealed class HafalanEvent {
        object NavogateToAddHafalanScreen : HafalanEvent()
        data class NavigateToUpdateHafalanScreen(val hafalan: HafalanEntity) : HafalanEvent()
        data class ShowUndoDeleteHafalanMessage(val hafalan: HafalanEntity) : HafalanEvent()
        data class ShowHafalanSavedConfirmationMessage(val msg: String) : HafalanEvent()
        object NavigateToDeleteAllCompletedScreen : HafalanEvent()
    }
}