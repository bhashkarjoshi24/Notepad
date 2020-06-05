package ui.activity

import adapter.NotesAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codelabs.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import model.NotesEntity
import viewmodel.NotesViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var mAdapter: NotesAdapter
    private lateinit var addNoteFab: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addNoteFab = findViewById(R.id.add_note_fab)
        addNoteFab.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)

        }

        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        mAdapter = NotesAdapter(this)

        recyclerView.adapter = mAdapter

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)

        notesViewModel.getNotesEntitiesPagedList()
            .observe(this, Observer<PagedList<NotesEntity>> { notesEntities ->
                if (notesEntities == null || notesEntities.size == 0) {
                    notesViewModel.getNotesEntitiesPagedList()
                }

                if (notesEntities.size == 0) {
                    relView.visibility = View.VISIBLE
                } else {
                    relView.visibility = View.INVISIBLE
                }

                mAdapter.submitList(notesEntities)
            })
    }
}