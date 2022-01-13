package com.example.retosohphos.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retosohphos.R

class DocumentosAdapter():RecyclerView.Adapter<DocumentosAdapter.DocumentosHolder>() {

    class DocumentosHolder(val view: View):RecyclerView.ViewHolder(view){

        fun render(){
            view
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentosHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        return DocumentosHolder(layoutInflater.inflate(R.layout.item_documentos,parent,false))
    }

    override fun onBindViewHolder(holder: DocumentosHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}