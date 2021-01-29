package com.example.appkotlin.Funcionario

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appkotlin.R
import com.example.appkotlin.data.DbHelper
import com.example.appkotlin.data.model.Funcionario

class FuncionarioActivity : AppCompatActivity(), FuncionarioListener {

    var linearLayoutManager: LinearLayoutManager? = null
    var funcionarioLista = ArrayList<Funcionario>()
    var dbHelper = DbHelper(this)
    var funLista: RecyclerView? = null
    var funListAdapter: FunListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fun)

        funLista = findViewById(R.id.funLista)
        linearLayoutManager = LinearLayoutManager(this)

        findViewById<FloatingActionButton>(R.id.addFunBtn).setOnClickListener { view ->
            val addFun = Intent(this, FuncionarioEditActivity::class.java)
            startActivity(addFun)
        }

        initView()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {
        funcionarioLista = dbHelper.funcionario()
        if (funcionarioLista.count() > 0){
            funListAdapter = FunListAdapter(funcionarioLista, this)
            funLista?.layoutManager = linearLayoutManager
            funLista?.adapter = funListAdapter
        }else{
            Toast.makeText(this, "Lista Vazia", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onFuncionarioItemClicked(funcionario: Funcionario) {
        val funId = funcionario.funId.toString()
        val funEdit = Intent(this, FuncionarioEditActivity::class.java)
        funEdit.putExtra("idFun", funId)
        funEdit.putExtra("nomeFun", funcionario.funNome)
        this.startActivity(funEdit)
    }

    override fun onFuncionarioItemLongClicked(funcionario: Funcionario) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Deletar " + funcionario.funNome)
        builder.setMessage("Deseja deletar o funcionario " + funcionario.funNome + "?")
        builder.setPositiveButton(android.R.string.yes){dialog, which ->
            deletarFuncionario(funcionario.funId.toString())
        }
    }

    private fun deletarFuncionario(funId: String) {
        val deletar = dbHelper.deletarFun(funId)
        if (deletar.equals("Deletado")){
            Toast.makeText(this, "Deletado com Sucesso", Toast.LENGTH_SHORT).show()
            onResume()
        }else{
            Toast.makeText(this, "Erro ao deletar" + deletar, Toast.LENGTH_SHORT).show()
        }
    }
}