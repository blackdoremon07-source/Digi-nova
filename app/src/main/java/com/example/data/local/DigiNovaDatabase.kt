package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        ServiceEntity::class,
        OnlineToolEntity::class,
        TipEntity::class,
        UpdateEntity::class,
        NotificationEntity::class,
        EnquiryEntity::class,
        AppSettingEntity::class,
        AuditLogEntity::class,
        AdminUserEntity::class,
        UserEntity::class,
        UserDocumentEntity::class,
        ExamFormEntity::class,
        AppBannerEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class DigiNovaDatabase : RoomDatabase() {

    abstract fun digiNovaDao(): DigiNovaDao

    companion object {
        @Volatile
        private var INSTANCE: DigiNovaDatabase? = null

        fun getDatabase(context: Context): DigiNovaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DigiNovaDatabase::class.java,
                    "digi_nova_database.db"
                )
                    .addCallback(DigiNovaDatabaseCallback(context.applicationContext))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DigiNovaDatabaseCallback(
        private val context: Context
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Populate database in background thread
            CoroutineScope(Dispatchers.IO).launch {
                populateInitialData(getDatabase(context))
            }
        }

        private suspend fun populateInitialData(database: DigiNovaDatabase) {
            val dao = database.digiNovaDao()
            DefaultSeedData.seed(dao)
        }
    }
}
