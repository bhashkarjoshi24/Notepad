package model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
class NotesEntity(note: String, category: String, currentDate: String) {


    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var note: String
    var category: String
    var currentDate: String

    init {

        this.note = note
        this.category = category
        this.currentDate = currentDate

    }

    @Ignore
    constructor(id: Int, note: String, category: String, currentDate: String) : this(
        note,
        category,
        currentDate
    ) {

        this.id = id
        this.note = note
        this.category = category
        this.currentDate = currentDate

    }


}