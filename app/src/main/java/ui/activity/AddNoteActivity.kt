package ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.codelabs.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_add_note.*
import utils.NotesRepository
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private lateinit var editTextCategory: EditText
    private lateinit var editTextNote: EditText
    private lateinit var fabSave: FloatingActionButton
    private lateinit var notesCategory: String
    private lateinit var notesText: String
    private var getNotesTitle:String = ""
    private var getNotesCategory:String = ""
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        editTextCategory = findViewById(R.id.edit_text_category)
        editTextNote = findViewById(R.id.edit_text_note)
        fabSave = findViewById(R.id.fab_save)
        checkIntentAndSetData()
        editTextListener()
        getCurrentDateAndTime()

        fabSave.setOnClickListener {

            if (!TextUtils.isEmpty(editTextCategory.text.toString()) && !TextUtils.isEmpty(
                    editTextNote.text.toString()
                )
            ) {
                !tilEnterNote.isErrorEnabled
                !tilEnterCategory.isErrorEnabled
                notesCategory = editTextCategory.text.toString()
                notesText = editTextNote.text.toString()


                if(id==0)
                {
                    NotesRepository.insertNoteInDb(notesCategory, notesText,getCurrentDateAndTime())
                }
                else{
                    NotesRepository.updateNoteInDb(id,notesCategory, notesText,getCurrentDateAndTime())

                }
                finish()
            }
        }

    }

    private fun editTextListener() {
        editTextCategory.addTextChangedListener(textWatcher)
        editTextNote.addTextChangedListener(textWatcher)
    }

    var textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            //Not required to check here
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            //Not required to check here
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validation()
        }
    }


    private fun validation() {
        if (TextUtils.isEmpty(editTextCategory.text.toString())) {
            tilEnterCategory.isErrorEnabled
            tilEnterCategory.error = getString(R.string.please_enter_category)

        } else {
            !tilEnterCategory.isErrorEnabled
            tilEnterCategory.error = ""
        }
        if (TextUtils.isEmpty(editTextNote.text.toString())) {
            tilEnterNote.isErrorEnabled
            tilEnterNote.error = getString(R.string.please_enter_notes)

        } else {
            !tilEnterNote.isErrorEnabled
            tilEnterNote.error = ""
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDateAndTime(): String {
        var answer=""
        answer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")
            current.format(formatter)
        } else {
            val date = Date()
            val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
            formatter.format(date)
        }
        return answer
    }

    private fun checkIntentAndSetData()
    {
        if(!TextUtils.isEmpty(intent.getStringExtra("notesTitle"))) {
            getNotesTitle = intent.getStringExtra("notesTitle")

        }
        if(!TextUtils.isEmpty(intent.getStringExtra("notesCategory"))) {
            getNotesCategory = intent.getStringExtra("notesCategory")
        }
        id = intent.getIntExtra("id",0)






        if(!TextUtils.isEmpty(getNotesTitle)){
            editTextNote.setText(getNotesTitle)
            editTextCategory.setText(getNotesCategory)
        }

    }
}