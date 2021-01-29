package com.example.appkotlin.Funcionario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.appkotlin.R
import com.example.appkotlin.data.DbHelper
import com.example.appkotlin.data.model.Funcionario

class FuncionarioEditActivity : AppCompatActivity() {

    lateinit var cancalarBtn: Button
    lateinit var salvarBtn: Button
    lateinit var deleteBtn: Button
    var nomeFunEditText: EditText? = null
    lateinit var spinnerDep: Spinner
    lateinit var dbHelper: DbHelper
    lateinit var spinnerLista: ArrayList<String>
    lateinit var itemId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funcionario_edit)
        supportActionBar?.title = "Adicionar Funcionario"

        dbHelper = DbHelper(this)

        cancalarBtn = findViewById(R.id.cancelar)
        salvarBtn = findViewById(R.id.salvar)
        deleteBtn = findViewById(R.id.deletar)
        nomeFunEditText = findViewById(R.id.nomeFunEditText)
        spinnerDep = findViewById(R.id.spinnerDepartamentos)

        spinnerAdapter()
        editVerificar()

    }

    private fun spinnerAdapter() {
        val departamentos = dbHelper.departamentosSpinner()
        spinnerLista = departamentos
        val depAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerLista)
        depAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDep.adapter = depAdapter
        spinnerDep.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val depPosition = parent?.getItemAtPosition(position)
                val nomeDep = depPosition.toString()
                val retorno = dbHelper.departamentoRow(nomeDep)
                itemId = retorno
            }

        }

    }

    private fun editVerificar() {
        if (intent.extras != null){
            supportActionBar?.title = "Editar Funcionario"
            deleteBtn.visibility = View.VISIBLE
            val idFun = intent.getStringExtra("idFun")
            val nomeFun = intent.getStringExtra("nomeFun")

            nomeFunEditText?.setText(nomeFun.toString())

            cancalarBtn.setOnClickListener { voltar() }
            salvarBtn.setOnClickListener { editarFun() }
            deleteBtn.setOnClickListener { deletarFun(idFun.toString()) }
        }else{
            cancalarBtn.setOnClickListener { voltar() }
            salvarBtn.setOnClickListener { salvarFun() }
        }
    }

    fun salvarFun(){
        val funNome = nomeFunEditText?.text.toString()
        var valido = true
        val addFuncionario = Funcionario(null, funNome, itemId)

        if (funNome.equals("")){
            Toast.makeText(this, "Nome Vazio", Toast.LENGTH_SHORT).show()
            valido = false
        }
        if (valido){
            val funcionario = dbHelper.inserirFun(addFuncionario)
            if (funcionario==true){
                voltar()
                Toast.makeText(this, "Funcionario Salvo", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Erro Ao Salvar", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun editarFun(){
        val idFun = intent.getStringExtra("idFun")
        val nomeFun = nomeFunEditText?.text.toString()

        val editFuncionario = Funcionario(idFun?.toInt(), nomeFun, itemId)

        var valido = true
        if (nomeFun.equals("")){
            Toast.makeText(this, "Nome Vazio", Toast.LENGTH_SHORT).show()
            valido = false
        }
        if (valido){
            val funcionario = dbHelper.atualizarFun(editFuncionario)
            if (funcionario.equals("Exito")){
                voltar()
                Toast.makeText(this, "Funcionario Salvo", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Erro Ao Salvar: " + funcionario, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deletarFun(idFun: String){

    }

    private fun voltar() {
        onBackPressed()
        finish()
    }
}