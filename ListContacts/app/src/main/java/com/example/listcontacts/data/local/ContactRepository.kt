package com.example.listcontacts

import com.example.listcontacts.data.local.ContactDao
import com.example.listcontacts.data.local.DatabaseContact
import com.example.listcontacts.domain.Contact
import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

interface ContactRepository {
    fun add(contact: Contact)
    fun getAll(): Observable<List<Contact>>
    fun updates(): Observable<Contact>
    fun getContactOnId(idDataBase: Long): Contact
    fun remove(id: Long)
    fun update(contact: Contact)

}

class ContactRepositoryImpl @Inject constructor(
    private val dao: ContactDao
) : ContactRepository {

    private val updates = PublishRelay.create<Contact>()

    override fun getContactOnId(idDataBase: Long): Contact {
        val dbContact = dao.selectContact(idDataBase)
        val contact = Contact(
            id = dbContact.id!!,
            name = dbContact.firstName,
            secondName = dbContact.secondName,
            numPhone = dbContact.nPhone
        )
        return contact
    }

    override fun remove(id: Long) {
        dao.deleteContact(id)
    }

    override fun update(contact: Contact) {
        // dao.updateContact(contact.toDatabase())
        updates.accept(contact)
    }

    override fun getAll() = Observable.fromCallable {
        dao.selectAll().map { it.toLocal() }
    }

    override fun updates(): Observable<Contact> = updates

    override fun add(contact: Contact) {
        val idDataBase: Long =
            dao.insert(contact.toDatabase())
        val newContact = Contact(
            id = idDataBase,
            name = contact.name,
            secondName = contact.secondName,
            numPhone = contact.numPhone
        ).also { updates.accept(it) }
    }

    private fun DatabaseContact.toLocal() = Contact(
        id = id ?: -1,
        name = firstName,
        secondName = secondName,
        numPhone = nPhone
    )

    private fun Contact.toDatabase() = DatabaseContact(
        id = if (id == -1L) null else id,
        firstName = name,
        secondName = secondName,
        nPhone = numPhone
    )

}