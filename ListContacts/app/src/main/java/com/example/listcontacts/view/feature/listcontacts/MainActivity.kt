package com.example.listcontacts.view.feature.listcontacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listcontacts.*
import com.example.listcontacts.data.server.RetrClient
import com.example.listcontacts.data.server.ServerResponse
import com.example.listcontacts.domain.Contact
import com.example.listcontacts.view.exstension.getSdk
import com.example.listcontacts.view.feature.details.ContentActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    private val recycler by lazy { findViewById<RecyclerView>(R.id.recycler) }
    private val fab by lazy { findViewById<FloatingActionButton>(R.id.Fab) }
    private val repository by lazy { getSdk().contactRepository }
    private val adapter = ContactAdapter()

    private val myCallBack = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            Collections.swap(adapter.contacts, viewHolder.adapterPosition, target.adapterPosition)
            adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            return false
        }

        override fun onSwiped(
            viewHolder: RecyclerView.ViewHolder,
            direction: Int
        ) = with(adapter) {
            removeAt(viewHolder.adapterPosition)
            notifyItemRemoved(viewHolder.adapterPosition)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        RetrClient.charactersService.getCharacters().enqueue(
            object : Callback<ServerResponse> {
                override fun onResponse(
                    call: Call<ServerResponse>,
                    response: Response<ServerResponse>
                ) {
                    Log.e("***", "seccuss ${response.body()?.results}")
                }

                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    Log.e("***", "fail t")
                }
            }
        )

        // Log.e("onCreate", "я тут")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //adapter.liveData.value = "sdfasdfas"

/*        printl(adapter.liveData.value)

        adapter.liveData.observe(this) {
            textVirew.text = it
        }*/


        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, ContentActivity::class.java)
            startActivityForResult(intent, 1)
        }


        with(adapter) {
            removeSwipe.subscribe {
                repository.remove(it)
            }
            contactClisks
                .subscribe { id ->
                    val intent = Intent(this@MainActivity, ContentActivity::class.java)
                    intent.putExtra("idDataBase", id)
                    startActivity(intent)
                }

        }


        repository.updates()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { contact ->
                updateList(
                    adapter.contacts
                        .toMutableList()
                        .apply { add(contact) }
                        .toList()
                )
            }

        repository.getAll()
            //.delay(5000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::updateList)

        recycler.layoutManager = LinearLayoutManager(baseContext)
        recycler.adapter = adapter
        ItemTouchHelper(myCallBack).attachToRecyclerView(recycler)
    }


    private fun updateList(contacts: List<Contact>) = with(adapter) {
        this.contacts = contacts
        notifyDataSetChanged()
    }
}
