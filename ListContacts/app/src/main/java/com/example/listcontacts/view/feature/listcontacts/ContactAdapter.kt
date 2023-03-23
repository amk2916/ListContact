package com.example.listcontacts.view.feature.listcontacts

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.listcontacts.R
import com.example.listcontacts.domain.Contact
import com.jakewharton.rxrelay3.PublishRelay


class ContactAdapter : RecyclerView.Adapter<ContactAdapter.Holder>() {

    val contactClisks = PublishRelay.create<Long>()

    var contacts: List<Contact> = emptyList()

    val removeSwipe = PublishRelay.create<Long>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        Log.e("onCreateViewHolder", "я тут")

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_contact, parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
       // Log.e("onBindViewHolder", "я тут")
        val contact: Contact = contacts[position]
        holder.itemView.setOnClickListener{
            contactClisks.accept(contact.id)
        }
        contacts.getOrNull(position % contacts.size)?.let {
            holder.title.text = it.name
        }
    }
    override fun getItemCount() = contacts.size


    class Holder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {
        val title by lazy { view.findViewById<TextView>(R.id.contact_title) }
    }
//dependency injection - внедрение зависимостей

    val liveData = MutableLiveData<String>()
    fun removeAt(index: Int) {
        if (contacts.isNotEmpty()) {
            removeSwipe.accept(contacts[index].id)
            contacts = contacts
                .toMutableList()
                .apply { this.removeAt(index) }
                .toList()
        }
    }
}



