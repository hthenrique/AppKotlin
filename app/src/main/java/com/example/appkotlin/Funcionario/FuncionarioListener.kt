package com.example.appkotlin.Funcionario

import com.example.appkotlin.data.model.Funcionario

interface FuncionarioListener {
    fun onFuncionarioItemClicked(funcionario: Funcionario)
    fun onFuncionarioItemLongClicked(funcionario: Funcionario)
}