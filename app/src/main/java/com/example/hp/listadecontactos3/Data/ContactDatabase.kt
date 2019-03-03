package com.example.hp.listadecontactos3.Data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {

    abstract fun contactDao():ContactDao

    companion object {
        private var instace:ContactDatabase? = null

        fun getInstace(context: Context):ContactDatabase?{
            if (instace==null){
                synchronized(ContactDatabase::class){
                    instace = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java,"contacts_database"
                    ).fallbackToDestructiveMigration().addCallback(roomCallback).build()
                }
            }
            return instace
        }
        fun destroyInstace(){
            instace=null
        }
        private val roomCallback = object : RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instace).execute()
            }
        }
    }
    class PopulateDbAsyncTask(db: ContactDatabase?): AsyncTask<Unit,Unit,Unit>(){
        private val contactDao=db?.contactDao()
        override fun doInBackground(vararg params: Unit?) {
            contactDao?.insert(Contact("name 1","phone1","eMail 1",1))
            contactDao?.insert(Contact("name 2","phone2","eMail 2",2))
        }
    }
}