package com.abdl.mylmk_app.ui.ngaji.notes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdl.mylmk_app.R
import com.abdl.mylmk_app.data.source.local.entity.NoteEntity
import com.abdl.mylmk_app.databinding.ActivityNoteBinding
import com.google.android.material.snackbar.Snackbar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class NoteActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory: NoteViewModelFactory by instance()

    private var _activityNoteBinding: ActivityNoteBinding? = null
    private val binding get() = _activityNoteBinding

    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityNoteBinding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]
        noteViewModel.getAllNotes().observe(this, noteObserver)

        adapter = NoteAdapter(this)

        binding?.rvNotes?.layoutManager = LinearLayoutManager(this)
        binding?.rvNotes?.setHasFixedSize(true)
        binding?.rvNotes?.adapter = adapter

        binding?.fabAdd?.setOnClickListener { view ->
            if (view.id == R.id.fab_add) {
                val intent = Intent(this, NoteAddUpdateActivity::class.java)
                getResult.launch(intent)
            }
        }
    }

    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null) {
                if (it.resultCode == NoteAddUpdateActivity.RESULT_ADD) {
                    showSnackbarMessage(getString(R.string.added))
                } else if (it.resultCode == NoteAddUpdateActivity.RESULT_UPDATE) {
                    showSnackbarMessage(getString(R.string.changed))
                } else if (it.resultCode == NoteAddUpdateActivity.RESULT_DELETE) {
                    showSnackbarMessage(getString(R.string.deleted))
                }
            }
        }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding?.root as View, message, Snackbar.LENGTH_SHORT).show()
    }

    private val noteObserver = Observer<List<NoteEntity>> { noteList ->
        if (noteList != null) {
            adapter.setListNotes(noteList)
        }
    }
}