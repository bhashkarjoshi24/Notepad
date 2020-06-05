package utils

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import data.AppDatabase
import data.NotesDAO
import model.NotesEntity

class NotesRepository(application: Application) {

    private var mDb: AppDatabase = AppDatabase.getInstance(application.applicationContext)

    companion object {
        lateinit var notesDao: NotesDAO
        fun insertNoteInDb(category: String, note: String, currentDate: String) {
            val notesEntity = NotesEntity(note, category, currentDate)
            InsertAsyncTask(notesDao).execute(notesEntity)

        }

        fun updateNoteInDb(id: Int, category: String, note: String, currentDate: String) {
            val notesEntity = NotesEntity(id, note, category, currentDate)
            UpdateAsyncTask(notesDao).execute(notesEntity)

        }

        fun deleteNoteInDb(id: Int, category: String, note: String, currentDate: String) {
            val notesEntity = NotesEntity(id, note, category, currentDate)
            DeleteAsyncTask(notesDao).execute(notesEntity)


        }

        fun getAllNotes(config: PagedList.Config): LiveData<PagedList<NotesEntity>> {
            val factory: DataSource.Factory<Int, NotesEntity> = notesDao.getAllNotes()

            return LivePagedListBuilder(factory, config).build()
        }
    }


    init {
        notesDao = mDb.notesDao()
    }

    class InsertAsyncTask(notesDAO: NotesDAO) : AsyncTask<NotesEntity, Unit, Unit>() {

        private var mNotesDAO: NotesDAO = notesDAO


        override fun doInBackground(vararg p0: NotesEntity) {
            mNotesDAO.insertNote(p0[0])
        }


    }

    class UpdateAsyncTask(notesDAO: NotesDAO) : AsyncTask<NotesEntity, Unit, Unit>() {

        private var mNotesDAO: NotesDAO = notesDAO


        override fun doInBackground(vararg p0: NotesEntity) {
            mNotesDAO.updateNote(p0[0])
        }


    }

    class DeleteAsyncTask(notesDAO: NotesDAO) : AsyncTask<NotesEntity, Unit, Unit>() {

        private var mNotesDAO: NotesDAO = notesDAO


        override fun doInBackground(vararg p0: NotesEntity) {
            mNotesDAO.deleteNote(p0[0])
        }


    }


}