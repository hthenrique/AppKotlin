package com.example.appkotlin.Funcionario

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appkotlin.R
import com.example.appkotlin.data.model.Funcionario

class FunListAdapter(funLista: List<Funcionario>, val funcionarioListener: FuncionarioListener): RecyclerView.Adapter<FunListAdapter.ViewHolder>() {

    internal var funLista: List<Funcionario> = ArrayList()
    init {
        this.funLista = funLista
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_list_funcionario, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val funcionario = funLista[position]

        holder.idFuncionario.text = "ID  " + funcionario.funId
        holder.nomeFuncionario.text = funcionario.funNome
        holder.funDepartamento.text = funcionario.depIdKey

        holder.itemView.setOnClickListener{
            funcionarioListener.onFuncionarioItemClicked(funcionario)
        }
        holder.itemView.setOnLongClickListener {
            funcionarioListener.onFuncionarioItemLongClicked(funcionario)
            true
        }
    }

    override fun getItemCount(): Int{
        return funLista.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var idFuncionario: TextView = view.findViewById(R.id.itemIdFun)
        var nomeFuncionario: TextView = view.findViewById(R.id.itemNomeFun)
        var funDepartamento: TextView = view.findViewById(R.id.itemFunDep)
    }
}