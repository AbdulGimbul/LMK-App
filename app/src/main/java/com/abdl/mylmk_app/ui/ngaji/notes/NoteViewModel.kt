package com.abdl.mylmk_app.ui.ngaji.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.abdl.mylmk_app.data.repository.NoteRepository
import com.abdl.mylmk_app.data.source.local.entity.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {

    fun insert(note: NoteEntity) = noteRepository.insert(note)

    fun update(note: NoteEntity) = noteRepository.update(note)

    fun delete(note: NoteEntity) = noteRepository.delete(note)

    fun getAllNotes(): LiveData<List<NoteEntity>> = noteRepository.getAllNotes()
}