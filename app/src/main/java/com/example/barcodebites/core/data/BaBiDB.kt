package com.example.barcodebites.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.barcodebites.core.data.entities.Attribute
import com.example.barcodebites.core.data.entities.Preference
import com.example.barcodebites.core.data.entities.PreferenceAttributeCrossRef
import com.example.barcodebites.core.data.entities.Product
import com.example.barcodebites.core.data.entities.User
import com.example.barcodebites.core.data.entities.UserPreferenceCrossRef
import com.example.barcodebites.core.data.entities.UserWithPreferencesWithAttributes
import com.example.barcodebites.feature_Authentication.data.AuthenticationDao
import com.example.barcodebites.feature_Scan.data.ScanDao

@Database(//main access point from app to data
    entities = [
        User::class,
        Product::class,
        Preference::class,
        Attribute::class,
        PreferenceAttributeCrossRef::class,
        UserPreferenceCrossRef::class
    ],
    version = 1 //pflicht, für migrations, für uns wahrscheinlich nicht nötig
    //exportSchema?
)
public abstract class BaBiDb : RoomDatabase() {

    //abstract val oder fun?
    abstract fun authenticationDao(): AuthenticationDao
    abstract fun scanDao(): ScanDao
    //abstract fun historyDao(): HistoryDao
    /*
    abstract fun foodPreferenceDao(): FoodPreferenceDao
    abstract fun foodAttributeDao(): FoodAttributeDao
    abstract fun preferenceAttributeCrossRefDao(): PreferenceAttributeCrossRefDao*/ //nicht sicher ob gebraucht

    //TODO klären ob gebraucht, genau verstehen und erklären, kommentieren,
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

        const val DATABASE_NAME = "barcodebites_db"
    }
}