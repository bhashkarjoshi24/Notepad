package viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import model.NotesEntity
import utils.NotesRepository


class NotesViewModel(application: Application) : AndroidViewModel(application) {

     var mRepository : NotesRepository
   private lateinit var notesEntitiesPagedList : LiveData<PagedList<NotesEntity>>

    init {
        mRepository = NotesRepository(application)

    }


    companion object {
        private var config : PagedList.Config = PagedList.Config.Builder()
                .setPageSize(10)
                .setInitialLoadSizeHint(10)
                .setPrefetchDistance(10)
                .build()
    }




    fun getNotesEntitiesPagedList() : LiveData<PagedList<NotesEntity>>{

        notesEntitiesPagedList = NotesRepository.getAllNotes(config)
        return notesEntitiesPagedList

    }

}