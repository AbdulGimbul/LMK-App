package com.abdl.mylmk_app.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.abdl.mylmk_app.data.source.local.entity.HafalanEntity
import com.abdl.mylmk_app.data.source.local.entity.NoteEntity
import com.abdl.mylmk_app.data.source.local.entity.UserEntity
import com.abdl.mylmk_app.data.source.remote.model.GuruItem
import com.abdl.mylmk_app.data.source.remote.model.JadwalGuruItem
import com.abdl.mylmk_app.data.source.remote.model.JadwalUserItem
import com.abdl.mylmk_app.data.source.remote.model.ProgramItem
import com.abdl.mylmk_app.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [UserEntity::class, NoteEntity::class, GuruItem::class, HafalanEntity::class, JadwalUserItem::class, JadwalGuruItem::class, ProgramItem::class],
    version = 3
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

    class Callback @Inject constructor(
        private val database: Provider<LmkDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            //db operations
            val dao = database.get().getLmkDao()

            applicationScope.launch {
                dao.insertHafalan(HafalanEntity("Q.S An-Nas"))
                dao.insertHafalan(HafalanEntity("Q.S An-Nisa", important = true))
                dao.insertHafalan(HafalanEntity("Q.S An-Naba", completed = true))
                dao.insertHafalan(HafalanEntity("Q.S An-Nahl", important = true))
                dao.insertHafalan(HafalanEntity("Q.S An-Naml"))
            }
        }
    }
}