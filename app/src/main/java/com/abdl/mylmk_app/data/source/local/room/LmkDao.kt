package com.abdl.mylmk_app.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.abdl.mylmk_app.data.source.local.entity.CURRENT_USER_ID
import com.abdl.mylmk_app.data.source.local.entity.NoteEntity
import com.abdl.mylmk_app.data.source.local.entity.UserEntity

@Dao
interface LmkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: UserEntity): Long

    @Query("SELECT * FROM userentity WHERE uid = $CURRENT_USER_ID")
    fun getUser(): LiveData<UserEntity>

    @Query("DELETE FROM userentity")
    fun deleteAll()

    //DAO Note
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNote(note: NoteEntity)

    @Update
    fun updateNote(note: NoteEntity)

    @Delete
    fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM noteentity ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllNotes(list: List<NoteEntity>)
}