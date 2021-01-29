package com.example.appkotlin.Departamento

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appkotlin.R
import com.example.appkotlin.data.DbHelper
import com.example.appkotlin.data.model.Departamento
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DepartamentoActivity : AppCompatActivity(), DepartamentoListener {

    var depListAdapter: DepListAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var departamentoLista = ArrayList<Departamento>()
    var dbHelper = DbHelper(this)

    var depLista: RecyclerView? = null
    lateinit var addDepBtn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dep)

        depLista = findViewById(R.id.depLista)
        linearLayoutManager = LinearLayoutManager(this)

        addDepBtn = findViewById(R.id.addDepBtn)

        addDepBtn.setOnClickListener {
            val addDep = Intent(this@DepartamentoActivity,DepartamentoEditActivity::class.java)
            startActivity(addDep)
        }

        initView()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {
        departamentoLista = dbHelper.departamentos()
        if (departamentoLista.count() > 0){
            depListAdapter = DepListAdapter(departamentoLista, this)
            depLista?.layoutManager = linearLayoutManager
            depLista?.adapter = depListAdapter
        }else{
            Toast.makeText(this,"Lista Vazia",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDepartamentoItemClicked(departamento: Departamento) {
        val depId = departamento.depId.toString()
        val depEdit = Intent(this, DepartamentoEditActivity::class.java)
        depEdit.putExtra("idDep", depId)
        depEdit.putExtra("nomeDep", departamento.depNome)
        depEdit.putExtra("siglaDep", departamento.depSigla)
        this.startActivity(depEdit)
    }

    override fun onDepartamentoItemLongClicked(departamento: Departamento) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Deletar " + departamento.depNome)
        builder.setMessage("Deseja deletar o departamento " + departamento.depNome + "?")
        builder.setPositiveButton(android.R.string.yes){dialog, which ->
            deletarDepartamento(departamento.depId.toString())
        }
        builder.setNegativeButton(android.R.string.no){dialog, which ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun deletarDepartamento(idDep: String) {
        val deletar = dbHelper.deletarDep(idDep)
        if (deletar.equals("Deletado")){
            Toast.makeText(this, "Deletado com Sucesso", Toast.LENGTH_SHORT).show()
            onResume()
        }else{
            Toast.makeText(this, "Erro ao deletar" + deletar, Toast.LENGTH_SHORT).show()
        }
    }

}
