package com.abdl.mylmk_app.ui.ngaji.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdl.mylmk_app.data.repository.NoteRepository

class NoteViewModelFactory constructor(
    private val repository: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: NoteViewModelFactory? = null

        @JvmStatic
        fun getInstance(repository: NoteRepository): NoteViewModelFactory {
            if (INSTANCE == null) {
                synchronized(NoteViewModelFactory::class.java) {
                    INSTANCE = NoteViewModelFactory(repository)
                }
            }
            return INSTANCE as NoteViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(NoteViewModel::class.java) -> {
                return NoteViewModel(repository) as T
            }
            else -> throw Throwable("Unknown ViewModel class:" + modelClass.name)
        }
    }
}