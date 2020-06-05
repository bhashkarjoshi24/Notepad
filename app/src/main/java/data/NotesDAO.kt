package data

import androidx.paging.DataSource
import androidx.room.*
import model.NotesEntity


@Dao
interface NotesDAO {

    @Query("SELECT * FROM notes")
    fun getAllNotes(): DataSource.Factory<Int, NotesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(notesEntity: NotesEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNote(notesEntity: NotesEntity)

    @Delete
    fun deleteNote(notesEntity: NotesEntity)

}