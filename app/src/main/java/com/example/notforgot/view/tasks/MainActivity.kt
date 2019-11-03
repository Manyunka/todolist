package com.example.notforgot.view.tasks

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notforgot.R
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.graphics.Color
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notforgot.presenter.RecyclerViewAdapter
import com.example.notforgot.presenter.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.content_main.*
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.notforgot.model.PreferencesHandler
import com.example.notforgot.model.tasks.Task
import com.example.notforgot.presenter.tasks.MainPresenter
import com.example.notforgot.view.user.LoginActivity
import com.google.android.material.snackbar.Snackbar



class MainActivity : AppCompatActivity(), MainView {

    private lateinit var presenter: MainPresenter
    private lateinit var adapter: RecyclerViewAdapter

    override fun loadData(tasks: List<Task>) {
        val data = MutableList(tasks.size) {
                i -> tasks[i] }
        adapter = RecyclerViewAdapter(data)
        recyclerView.adapter = adapter
    }

    override fun dataExisted() {
        placeholderView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun logout() {
        val preferencesHandler = PreferencesHandler(applicationContext)
        preferencesHandler.deleteToken()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        presenter = MainPresenter(this, applicationContext)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        enableSwipeToDeleteAndUndo()

        if (presenter.internetCheck())
            presenter.loadDataBase()

        fab.setOnClickListener {
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            startActivityForResult(Intent(this, CreateTaskActivity::class.java), 0)
        }


    }

    override fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                val position = viewHolder.adapterPosition
                val item = adapter.getData()[position]

                adapter.removeItem(position)

                val snackbar = Snackbar
                    .make(
                        view,
                        "Дело удалено",
                        Snackbar.LENGTH_LONG
                    )
                snackbar.setAction("Отмена") {
                    adapter.restoreItem(item, position)
                    recyclerView.scrollToPosition(position)
                }

                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            Toast.makeText(applicationContext, "Задача успешно дабавлена", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_logout -> {
            logout()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        presenter.onDestroyActivity()
        super.onDestroy()
    }
}
