package data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import model.NotesEntity

@Database(entities = [NotesEntity::class], exportSchema = false, version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDAO

    companion object {
        private var sInstance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {

                synchronized(AppDatabase::class) {

                    if (sInstance == null) {

                        sInstance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "notesDB"
                        ).build()

                    }

                }
            }

            return sInstance!!
        }
    }
}