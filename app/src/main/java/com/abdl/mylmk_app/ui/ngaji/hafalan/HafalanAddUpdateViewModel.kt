package com.abdl.mylmk_app.ui.ngaji.hafalan

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.abdl.mylmk_app.data.source.local.entity.HafalanEntity
import com.abdl.mylmk_app.data.source.local.room.LmkDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HafalanAddUpdateViewModel @AssistedInject constructor(
    private val lmkDao: LmkDao,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface HafalanAddUpdateViewModelFactory {
        fun create(handle: SavedStateHandle): HafalanAddUpdateViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: HafalanAddUpdateViewModelFactory,
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

    val hafalan = state.get<HafalanEntity>("hafalan")

    var hafalanName = state.get<String>("hafalanName") ?: hafalan?.name ?: ""
        set(value) {
            field = value
            state.set("hafalanName", value)
        }

    var hafalanImportance = state.get<Boolean>("hafalanImportance") ?: hafalan?.important ?: false
        set(value) {
            field = value
            state.set("hafalanImportance", value)
        }

    private val addUpdateHafalanEventChannel = Channel<AddUpdateHafalanEvent>()
    val addUpdateHafalanEvent = addUpdateHafalanEventChannel.receiveAsFlow()

    fun onSaveClick() {
        if (hafalanName.isBlank()) {
            showInvalidInputMessage("Name cannot be empty")
            return
        }

        if (hafalan != null) {
            val updatedHafalan = hafalan.copy(name = hafalanName, important = hafalanImportance)
            updateHafalan(updatedHafalan)
        } else {
            val newHafalan = HafalanEntity(name = hafalanName, important = hafalanImportance)
            createHafalan(newHafalan)
        }
    }

    private fun createHafalan(hafalanEntity: HafalanEntity) = viewModelScope.launch {
        lmkDao.insertHafalan(hafalanEntity)
        addUpdateHafalanEventChannel.send(
            AddUpdateHafalanEvent.NavigateBackWithResult(
                ADD_HAFALAN_RESULT_OK
            )
        )
    }

    private fun updateHafalan(hafalanEntity: HafalanEntity) = viewModelScope.launch {
        lmkDao.updateHafalan(hafalanEntity)
        addUpdateHafalanEventChannel.send(
            AddUpdateHafalanEvent.NavigateBackWithResult(
                UPDATE_HAFALAN_RESULT_OK
            )
        )
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        addUpdateHafalanEventChannel.send(AddUpdateHafalanEvent.ShowInvalidInputMessage(text))
    }

    sealed class AddUpdateHafalanEvent {
        data class ShowInvalidInputMessage(val msg: String) : AddUpdateHafalanEvent()
        data class NavigateBackWithResult(val result: Int) : AddUpdateHafalanEvent()
    }
}