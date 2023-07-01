package com.example.barcodebites.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.barcodebites.core.data.entities.Preference
import com.example.barcodebites.core.data.entities.Product
import com.example.barcodebites.core.data.entities.User
import com.example.barcodebites.feature_Authentication.data.AuthenticationDao
import com.example.barcodebites.feature_History.data.HistoryDao
import com.example.barcodebites.feature_Profile.data.ProfileDao
import com.example.barcodebites.feature_Scan.data.ScanDao

@Database(//main access point from app to data
    entities = [
        User::class,
        Product::class,
        Preference::class
    ],
    version = 1 //Nötig für Production, in dev Daten clearen
)
abstract class BaBiDb : RoomDatabase() {
    abstract fun authenticationDao(): AuthenticationDao
    abstract fun scanDao(): ScanDao
    abstract fun profileDao(): ProfileDao
    abstract fun historyDao(): HistoryDao

    companion object {
        //if no instance creating one, if instance returning instance
        @Volatile//when INSTANCE changed, Change immediately visible to other threads
        private var INSTANCE: BaBiDb? = null

        fun getDatabase(context: Context): BaBiDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {//block executed only by single thread
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaBiDb::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }

        private const val DATABASE_NAME = "barcodebites_db"
    }
}