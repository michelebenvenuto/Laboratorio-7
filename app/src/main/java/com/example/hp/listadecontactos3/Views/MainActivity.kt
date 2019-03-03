package com.example.hp.listadecontactos3.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hp.listadecontactos3.Adapters.ContactAdapter
import com.example.hp.listadecontactos3.Data.Contact
import com.example.hp.listadecontactos3.Data.ContactViewModel
import com.example.hp.listadecontactos3.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_CONTACT_REQUEST = 1
        const val EDIT_NOTE_REQUEST=2
    }

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddContact.setOnClickListener{
            startActivityForResult(
                Intent(this,NewContactActivity::class.java ),
                ADD_CONTACT_REQUEST
            )
        }
        recycler_view.layoutManager=LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        var adapter= ContactAdapter()

        recycler_view.adapter=adapter

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        contactViewModel.getAllContacts().observe(this, Observer<List<Contact>>{
            adapter.submitList(it)
        } )

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                contactViewModel.delete(adapter.getContactAt(viewHolder.adapterPosition)!!)
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : ContactAdapter.OnItemClickListener{
            override fun onItemClick(contact: Contact) {
                var intent = Intent(baseContext, ContactInfo::class.java)
                intent.putExtra("name",contact.name)
                intent.putExtra("number", contact.phoneNumber)
                intent.putExtra("mail", contact.eMail)

                startActivityForResult(intent, EDIT_NOTE_REQUEST)
            }
        })
    }

}
