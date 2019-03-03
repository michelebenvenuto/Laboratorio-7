package com.example.hp.listadecontactos3.Data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ContactRepository(application: Application) {
    private var contactDao:ContactDao
    private var allContact: LiveData<List<Contact>>

    init {
        val database:ContactDatabase = ContactDatabase.getInstace(application.applicationContext)!!
        contactDao = database.contactDao()
        allContact= contactDao.getAllNotes()
    }

    fun insert(contact: Contact){
        val insertContactAsyncTask = InsertContactAsyncTask(contactDao).execute(contact)
    }
    fun update(contact: Contact){
        val insertContactAsyncTask = UpdateContactAsyncTask(contactDao).execute(contact)
    }
    fun delete(contact: Contact){
        val insertContactAsyncTask = DeleteContactAsyncTask(contactDao).execute(contact)
    }
    fun deleteAllContacts(){
        val insertContactAsyncTask = deleteAllContactAsyncTask(contactDao).execute()
    }

    fun getAllContacts():LiveData<List<Contact>>{
        return allContact
    }
    companion object {
        private class InsertContactAsyncTask(contactDao: ContactDao):AsyncTask<Contact,Unit,Unit>(){
            val contactDao = contactDao
            override fun doInBackground(vararg p0: Contact?) {
                contactDao.insert(p0[0]!!)
            }
        }
        private class UpdateContactAsyncTask(contactDao: ContactDao):AsyncTask<Contact,Unit,Unit>(){
            val contactDao = contactDao

            override fun doInBackground(vararg params: Contact?) {
                contactDao.update(params[0]!!)
            }
        }
        private class DeleteContactAsyncTask(contactDao: ContactDao): AsyncTask<Contact,Unit,Unit>(){
            val contactDao= contactDao

            override fun doInBackground(vararg params: Contact?) {
                contactDao.delete(params[0]!!)
            }
        }
        private class deleteAllContactAsyncTask(contactDao: ContactDao): AsyncTask<Unit,Unit,Unit>(){
            val contactDao = contactDao

            override fun doInBackground(vararg params: Unit?) {
                contactDao.deleteAllNotes()
            }
        }
    }
}