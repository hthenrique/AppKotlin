package com.example.appkotlin.Departamento

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.appkotlin.R
import com.example.appkotlin.data.DbHelper
import com.example.appkotlin.data.model.Departamento

class DepartamentoEditActivity : AppCompatActivity() {

    lateinit var cancalarBtn: Button
    lateinit var salvarBtn: Button
    lateinit var deleteBtn: Button
    var nomeDepEditText: EditText? = null
    var siglaDepEditText: EditText? = null
    lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departamento_edit)
        supportActionBar?.title = "Adicionar Departamento"

        dbHelper = DbHelper(this)

        cancalarBtn = findViewById(R.id.cancelar)
        salvarBtn = findViewById(R.id.salvar)
        deleteBtn = findViewById(R.id.deletar)
        nomeDepEditText = findViewById(R.id.nomeDepEditText)
        siglaDepEditText = findViewById(R.id.siglaDepEditText)

        editVerificar()
    }

    private fun editVerificar() {

        if (intent.extras != null){
            supportActionBar?.title = "Editar Departamento"
            deleteBtn.visibility = View.VISIBLE
            val idDep = intent.getStringExtra("idDep")
            val nomeDep = intent.getStringExtra("nomeDep")
            val siglaDep = intent.getStringExtra("siglaDep")

            nomeDepEditText?.setText(nomeDep.toString())
            siglaDepEditText?.setText(siglaDep.toString())

            cancalarBtn.setOnClickListener{voltar()}
            salvarBtn.setOnClickListener{editarDep()}
            deleteBtn.setOnClickListener{deletarDepartamento(idDep.toString())}
        }else{
            cancalarBtn.setOnClickListener{voltar()}
            salvarBtn.setOnClickListener{salvarDep()}
        }
    }

    fun salvarDep() {
        val depNome = nomeDepEditText?.text.toString()
        val depSigla = siglaDepEditText?.text.toString()

        var valido = true
        val addDepartamento = Departamento(null, depNome, depSigla)

        if (depNome.equals("")){
            Toast.makeText(this, "Nome Vazio", Toast.LENGTH_SHORT).show()
            valido = false
        }else{
            if (depSigla.equals("")){
                Toast.makeText(this, "Sigla Vazia", Toast.LENGTH_SHORT).show()
                valido = false
            }
        }
        if (valido){
            val departamento = dbHelper.inserirDep(addDepartamento)
            if (departamento){
                Toast.makeText(this,"Salvo com Sucesso", Toast.LENGTH_SHORT).show()
                voltar()
            }else{
                Toast.makeText(this,"Erro ao inserir", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun editarDep(){
        val idDep = intent.getStringExtra("idDep")
        val depNome = nomeDepEditText?.text.toString()
        val depSigla = siglaDepEditText?.text.toString()

        val addParameters = Departamento(idDep?.toInt(), depNome, depSigla)

        var valido = true
        if (depNome.equals("")){
            Toast.makeText(this, "Nome Vazio", Toast.LENGTH_SHORT).show()
            valido = false
        }else{
            if (depSigla.equals("")){
                Toast.makeText(this, "Sigla Vazia", Toast.LENGTH_SHORT).show()
                valido = false
            }
        }
        if (valido){
            val addDep = dbHelper.atualizarDep(addParameters)
            if (addDep.equals("Exito")){
                Toast.makeText(this,"Salvo com Sucesso", Toast.LENGTH_SHORT).show()
                voltar()
            }else{
                Toast.makeText(this, addDep, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deletarDepartamento(idDep: String) {
        val deletar = dbHelper.deletarDep(idDep)
        if (deletar == "Deletado"){
            Toast.makeText(this, "Deletado com Sucesso", Toast.LENGTH_SHORT).show()
            voltar()
        }else{
            Toast.makeText(this, "Erro ao deletar" + deletar, Toast.LENGTH_SHORT).show()
        }
    }

    fun voltar(){
        onBackPressed()
        finish()
    }
}