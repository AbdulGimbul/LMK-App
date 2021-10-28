package com.abdl.mylmk_app.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abdl.mylmk_app.data.source.local.entity.NoteEntity
import com.abdl.mylmk_app.data.source.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, NoteEntity::class],
    version = 2
)
abstract class LmkDatabase : RoomDatabase() {
    abstract fun getLmkDao(): LmkDao

    companion object {
        @Volatile
        private var instance: LmkDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LmkDatabase::class.java,
                "MyDatabase.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}