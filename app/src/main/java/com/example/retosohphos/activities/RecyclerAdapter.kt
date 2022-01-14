package com.example.retosohphos.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.retosohphos.R

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var documentos: MutableList<DatosDocumentos>  = ArrayList()
    lateinit var context: Context

    fun RecyclerAdapter(documento : MutableList<DatosDocumentos>, context: Context){
        this.documentos = documento
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = documentos.get(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_documentos, parent, false))
    }

    override fun getItemCount(): Int {
        return documentos.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Nombre = view.findViewById(R.id.tv_NombreyApellido) as TextView
        val Fecha = view.findViewById(R.id.tv_fecha) as TextView
        val TipoAdjunto = view.findViewById(R.id.tv_tipodeAdjunto) as TextView
        val imagen = view.findViewById(R.id.img_documento) as ImageView

        fun bind(documento:DatosDocumentos, context: Context){
            Nombre.text = documento.Nombre
            Fecha.text = documento.Fecha
            TipoAdjunto.text = documento.TipoAdjunto
            imagen.setImageBitmap(documento.Adjunto)
            itemView.setOnClickListener(View.OnClickListener { Toast.makeText(context, documento.TipoAdjunto, Toast.LENGTH_SHORT).show() })
        }

    }
}