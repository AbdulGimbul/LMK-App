package com.abdl.mylmk_app.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.abdl.mylmk_app.data.SortOrder
import com.abdl.mylmk_app.data.source.local.entity.CURRENT_USER_ID
import com.abdl.mylmk_app.data.source.local.entity.HafalanEntity
import com.abdl.mylmk_app.data.source.local.entity.NoteEntity
import com.abdl.mylmk_app.data.source.local.entity.UserEntity
import com.abdl.mylmk_app.data.source.remote.model.GuruItem
import com.abdl.mylmk_app.data.source.remote.model.JadwalGuruItem
import com.abdl.mylmk_app.data.source.remote.model.JadwalUserItem
import kotlinx.coroutines.flow.Flow

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

    //DAO Guru
    @Query("SELECT * FROM tbl_guru")
    fun getAllGuru(): Flow<List<GuruItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuru(guru: List<GuruItem>)

    @Query("DELETE FROM tbl_guru")
    suspend fun deleteAllGuru()

    //DAO Jadwal Guru
    @Query("SELECT * FROM tbl_jadwal_guru")
    fun getJadwalGuru(): Flow<List<JadwalGuruItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJadwalGuru(jadwal: List<JadwalGuruItem>)

    @Query("DELETE FROM tbl_jadwal_guru")
    suspend fun deleteJadwalGuru()

    //DAO Jadwal
    @Query("SELECT * FROM tbl_jadwal")
    fun getJadwal(): Flow<List<JadwalUserItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJadwal(jadwal: List<JadwalUserItem>)

    @Query("DELETE FROM tbl_jadwal")
    suspend fun deleteJadwal()

    //DAO Hafalan
    fun getHafalan(
        query: String,
        sortOrder: SortOrder,
        hideCompleted: Boolean
    ): Flow<List<HafalanEntity>> =
        when (sortOrder) {
            SortOrder.BY_NAME -> getHafalanSortedByName(query, hideCompleted)
            SortOrder.BY_DATE -> getHafalanSortedByDateCreated(query, hideCompleted)
        }

    @Query("SELECT * FROM tbl_hafalan WHERE (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, name")
    fun getHafalanSortedByName(
        searchQuery: String,
        hideCompleted: Boolean
    ): Flow<List<HafalanEntity>>

    @Query("SELECT * FROM tbl_hafalan WHERE (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, created")
    fun getHafalanSortedByDateCreated(
        searchQuery: String,
        hideCompleted: Boolean
    ): Flow<List<HafalanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHafalan(hafalanEntity: HafalanEntity)

    @Update
    suspend fun updateHafalan(hafalanEntity: HafalanEntity)

    @Delete
    suspend fun deleteHafalan(hafalanEntity: HafalanEntity)

    @Query("DELETE FROM tbl_hafalan WHERE completed = 1")
    suspend fun deleteCompletedHafalan()
}