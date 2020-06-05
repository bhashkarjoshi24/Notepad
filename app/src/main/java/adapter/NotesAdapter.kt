package adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.codelabs.R
import model.NotesEntity
import ui.activity.AddNoteActivity
import utils.NotesRepository


class NotesAdapter(private var context: Context) :
    PagedListAdapter<NotesEntity, NotesAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false)
        return MyViewHolder(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val notesEntity: NotesEntity = getItem(position)!!
        holder.categoryTextView.text = notesEntity.category
        Log.d("check", notesEntity.note)
        holder.noteTextView.text = notesEntity.note
        holder.noteCreateDate.text =
            context.getString(R.string.created_date) + " " + notesEntity.currentDate

        holder.imgEdit.setOnClickListener {
            Log.d("check", notesEntity.id.toString())
            val intent = Intent(context, AddNoteActivity::class.java)
            intent.putExtra("notesTitle", notesEntity.note)
            intent.putExtra("notesCategory", notesEntity.category)
            intent.putExtra("id", notesEntity.id)
            context.startActivity(intent)

        }

        holder.imgDelete.setOnClickListener {
            NotesRepository.deleteNoteInDb(
                notesEntity.id,
                notesEntity.category,
                notesEntity.note,
                notesEntity.currentDate
            )
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var categoryTextView: TextView = itemView.findViewById(R.id.category_textView)
        var noteTextView: TextView = itemView.findViewById(R.id.note_textView)
        var imgEdit: ImageView = itemView.findViewById(R.id.imgEdit)
        var imgDelete: ImageView = itemView.findViewById(R.id.imgDelete)
        var noteCreateDate: TextView = itemView.findViewById(R.id.noteCreateDate)
    }

    companion object {

        private var DIFF_CALLBACK: DiffUtil.ItemCallback<NotesEntity> =
            object : DiffUtil.ItemCallback<NotesEntity>() {

                override fun areItemsTheSame(oldItem: NotesEntity, newItem: NotesEntity): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: NotesEntity,
                    newItem: NotesEntity
                ): Boolean {
                    return oldItem.id == newItem.id && oldItem.note == newItem.note && oldItem.category == newItem.category
                }


            }
    }


}