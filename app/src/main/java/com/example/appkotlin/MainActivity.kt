package com.example.appkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.example.appkotlin.Departamento.DepartamentoActivity
import com.example.appkotlin.Funcionario.FuncionarioActivity
import com.example.appkotlin.data.DbHelper

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var btnDep: ImageButton
    lateinit var btnFun: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDep = findViewById(R.id.btnDep)
        btnFun = findViewById(R.id.btnFun)
        btnDep.setOnClickListener(this)
        btnFun.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v){
            btnDep -> {
                val departamento = Intent(this@MainActivity, DepartamentoActivity::class.java)
                startActivity(departamento)
            }
            btnFun -> {
                val funcionario = Intent(this@MainActivity, FuncionarioActivity::class.java)
                startActivity(funcionario)
            }
        }
    }
}