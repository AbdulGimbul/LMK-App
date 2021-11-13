package com.abdl.mylmk_app.data.repository

import androidx.lifecycle.LiveData
import com.abdl.mylmk_app.data.source.local.entity.NoteEntity
import com.abdl.mylmk_app.data.source.local.room.LmkDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class NoteRepository @Inject constructor(private val db: LmkDatabase) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getAllNotes(): LiveData<List<NoteEntity>> = db.getLmkDao().getAllNotes()

    fun insert(note: NoteEntity) {
        executorService.execute { db.getLmkDao().insertNote(note) }
    }

    fun delete(note: NoteEntity) {
        executorService.execute { db.getLmkDao().deleteNote(note) }
    }

    fun update(note: NoteEntity) {
        executorService.execute { db.getLmkDao().updateNote(note) }
    }
}