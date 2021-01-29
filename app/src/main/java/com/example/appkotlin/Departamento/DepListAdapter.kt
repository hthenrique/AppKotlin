package com.example.appkotlin.Departamento

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appkotlin.R
import com.example.appkotlin.data.model.Departamento

class DepListAdapter(depLista: List<Departamento>, val departamentoListener: DepartamentoListener): RecyclerView.Adapter<DepListAdapter.ViewHolder>() {

    internal var depLista: List<Departamento> = ArrayList<Departamento>()
    init {
        this.depLista = depLista
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_list_departamento, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val departamento = depLista[position]

        holder.idDepart.text = "ID  " + departamento.depId
        holder.nomeDepart.text = departamento.depNome
        holder.siglaDepart.text = departamento.depSigla

        holder.itemView.setOnClickListener {
           departamentoListener.onDepartamentoItemClicked(departamento)
        }
        holder.itemView.setOnLongClickListener {
            departamentoListener.onDepartamentoItemLongClicked(departamento)
            true
        }
    }

    override fun getItemCount(): Int {
        return depLista.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        var idDepart: TextView = view.findViewById(R.id.itemIdDep)
        var nomeDepart: TextView = view.findViewById(R.id.itemNomeDep)
        var siglaDepart: TextView = view.findViewById(R.id.itemSiglaDep)

    }

}
