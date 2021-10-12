package com.abdl.mylmk_app.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abdl.mylmk_app.data.source.local.entity.CURRENT_USER_ID
import com.abdl.mylmk_app.data.source.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: UserEntity): Long

    @Query("SELECT * FROM userentity WHERE uid = $CURRENT_USER_ID")
    fun getUser(): LiveData<UserEntity>

    @Query("DELETE FROM userentity")
    fun deleteAll()
}