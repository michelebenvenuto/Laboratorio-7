package com.example.hp.listadecontactos3.Adapters

import android.animation.PropertyValuesHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.hp.listadecontactos3.Data.Contact
import com.example.hp.listadecontactos3.R
import kotlinx.android.synthetic.main.contact_item.view.*

class ContactAdapter:ListAdapter<Contact, ContactAdapter.ContactHolder>(DIFF_CAllBACK) {

    companion object {
        private val DIFF_CAllBACK = object : DiffUtil.ItemCallback<Contact>(){

            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.name==newItem.name && oldItem.phoneNumber==newItem.phoneNumber && oldItem.priority==newItem.priority && oldItem.eMail== newItem.eMail
            }
        }
    }
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ContactHolder{
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.contact_item,parent,false)
        return ContactHolder(itemView)
    }
    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val currentNote: Contact = getItem(position)

        holder.textViewTitle.text = currentNote.name
        holder.textViewPriority.text = currentNote.priority.toString()
        holder.textViewDescription.text = currentNote.phoneNumber
    }
    inner class ContactHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                val position= adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener?.onItemClick(getItem(position))
                }
            }
        }
        var textViewTitle: TextView = itemView.text_view_name
        var textViewPriority: TextView = itemView.text_view_priority
        var textViewDescription: TextView = itemView.text_view_number
    }
    interface OnItemClickListener{
        fun onItemClick(contact: Contact)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener=listener
    }
}