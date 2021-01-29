package com.example.appkotlin.Departamento

import com.example.appkotlin.data.model.Departamento

interface DepartamentoListener {
    fun onDepartamentoItemClicked(departamento: Departamento)
    fun onDepartamentoItemLongClicked(departamento: Departamento)
}