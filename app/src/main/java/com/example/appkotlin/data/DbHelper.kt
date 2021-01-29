package com.example.appkotlin.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appkotlin.data.model.Departamento
import com.example.appkotlin.data.model.Funcionario

class DbHelper(context: Context) : SQLiteOpenHelper(context, BDnome, null, 2) {
    companion object{
        val BDnome = "BancoDados"
        //Departamentos
        val depTable = "Departamentos"
        var idDep = "idDep"
        var nomeDep = "nomeDep"
        var siglaDep = "siglaDep"
        //Funcionarios
        val funTable = "Funcionarios"
        val idFun = "idFun"
        val nomeFun = "nomeFun"
        val idDepKey = "idDepKey"

    }
    override fun onCreate(db: SQLiteDatabase?) {

        val criarDepartamentos =
                " CREATE TABLE $depTable ( " +
                    " idDep    INTEGER  PRIMARY KEY  AUTOINCREMENT     NOT NULL, " +
                    " nomeDep  STRING   NOT NULL, " +
                    " siglaDep STRING   NOT NULL  " +
                    " ) "
        db?.execSQL(criarDepartamentos)

        val criarFuncionarios =
                "CREATE TABLE $funTable ( " +
                    " idFun     INTEGER    PRIMARY KEY  AUTOINCREMENT    NOT NULL," +
                    " nomeFun   STRING     NOT NULL," +
                    " idDepKey  INTEGER    REFERENCES Departamentos(idDep) ON DELETE CASCADE    NOT NULL" +
                    " ) "
        db?.execSQL(criarFuncionarios)
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        if (!db.isReadOnly){
            db.execSQL("PRAGMA foreign_keys= ON;")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $depTable")
        db!!.execSQL("DROP TABLE IF EXISTS $funTable")
        onCreate(db)

    }

    //Tabela departamentos - Todas as funções no banco de dados
    //Inserir Departamento
    fun inserirDep(departamento: Departamento): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        try {
            contentValues.put(nomeDep, departamento.depNome)
            contentValues.put(siglaDep, departamento.depSigla)
            db.insert(depTable,null, contentValues)
            db.close()
            return true
        }catch (ex: Exception){
            return false
        }
    }

    //Atualizar Departamento
    fun atualizarDep(departamento: Departamento): String{
        val db = this.writableDatabase
        val retorno: String = "Exito"
        try {
            val contentValues = ContentValues()
            contentValues.put(idDep, departamento.depId)
            contentValues.put(nomeDep, departamento.depNome)
            contentValues.put(siglaDep, departamento.depSigla)
            db.update(depTable, contentValues, "idDep = ?", arrayOf(departamento.depId.toString()))
            return retorno
        }catch (ex: Exception){
            return ex.message.toString()
        }finally {
         db.close()
        }
    }

    //Exluir Departamento
    fun deletarDep(id: String): String{
        val db = this.writableDatabase
        val deletado = "Deletado"
        try {
            db.delete(depTable, "idDep = ?", arrayOf(id))
            return deletado
        }catch (ex: Exception){
            return ex.toString()
        }
    }

    //Lista de Departamentos
    fun departamentos(): ArrayList<Departamento>{
        val departamentoLista = ArrayList<Departamento>()
        val db = this.readableDatabase
        val select = "SELECT * FROM $depTable"
        val cursor: Cursor = db.rawQuery(select, null)

        if (cursor.count > 0){
            cursor.moveToFirst()
            do {
                val departamento = Departamento()
                departamento.depId = cursor.getInt(cursor.getColumnIndex(idDep))
                departamento.depNome = cursor.getString(cursor.getColumnIndex(nomeDep))
                departamento.depSigla = cursor.getString(cursor.getColumnIndex(siglaDep))
                departamentoLista.add(departamento)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return departamentoLista
    }

    fun departamentosSpinner(): ArrayList<String> {
        val departamentoLista = ArrayList<Departamento>()
        val db = this.readableDatabase
        val select = "SELECT * FROM $depTable"
        val cursor: Cursor = db.rawQuery(select, null)
        val deps: ArrayList<String> = ArrayList()
        if (cursor.count > 0){
            cursor.moveToFirst()
            do {
                val departamento = Departamento()
                departamento.depId = cursor.getInt(cursor.getColumnIndex(idDep))
                departamento.depNome = cursor.getString(cursor.getColumnIndex(nomeDep))
                deps.add(departamento.depNome)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return deps
    }

    fun departamentoRow(nomeDep: String): String{
        var departamentoid: String = " "
        val db = this.readableDatabase
        val select = "SELECT $idDep FROM $depTable WHERE nomeDep = '$nomeDep'"
        val cursor: Cursor = db.rawQuery(select,null)
        if (cursor.count > 0){
            cursor.moveToFirst()
            try {
                val departamento = Departamento()
                departamento.depId = cursor.getInt(cursor.getColumnIndex(idDep))
                departamentoid = departamento.depId.toString()
                return departamentoid
            }catch (ex: Exception){
                return ex.toString()
            }
        }
        return departamentoid
    }

    //Tabela Funcionario - Todas as funções no banco de dados
    //Inserir Funcionario
    fun inserirFun(funcionario: Funcionario): Boolean{
        val db = this.writableDatabase
        try {
            val contentValues = ContentValues()
            contentValues.put(nomeFun, funcionario.funNome)
            contentValues.put(idDepKey, funcionario.depIdKey)
            db.insert(funTable, null, contentValues)
            db.close()
            return true
        }catch (ex: Exception){
            return false
        }
    }

    fun atualizarFun(funcionario: Funcionario): String{
        val db = this.writableDatabase
        val exito = "Exito"
        try {
            val contentValues = ContentValues()
            contentValues.put(nomeFun, funcionario.funNome)
            contentValues.put(idDepKey, funcionario.depIdKey)
            db.update(funTable, contentValues, "$idFun = ?", arrayOf(funcionario.funId.toString()))
            db.close()
            return exito
        }catch (ex: Exception){
            return ex.toString()
        }
    }

    fun deletarFun(id: String): String{
        val db = this.writableDatabase
        val deletado = "Deletado"
        try {
            db.delete(funTable, "idFun = ?", arrayOf(id))
            return deletado
        }catch (ex: Exception){
            return ex.toString()
        }
    }

    fun pegarFun(id: Int): Funcionario{
        val funcionario = Funcionario()
        val db = this.writableDatabase
        val select = "SELECT * FROM  $funTable WHERE $idFun = $id"
        val cursor = db.rawQuery(select, null)
        cursor?.moveToFirst()
        funcionario.funId = cursor.getInt(cursor.getColumnIndex(idFun))
        funcionario.funNome = cursor.getString(cursor.getColumnIndex(nomeFun))
        funcionario.depIdKey = cursor.getString(cursor.getColumnIndex(idDepKey))
        cursor.close()
        return funcionario
    }

    fun funcionario(): ArrayList<Funcionario>{
        val funcionarioLista = ArrayList<Funcionario>()
        val db = this.readableDatabase
        val select = "SELECT $funTable.idFun, $funTable.nomeFun, $depTable.nomeDep FROM  $funTable INNER JOIN $depTable ON $funTable.idDepKey = $depTable.idDep;"
        val cursor: Cursor = db.rawQuery(select, null)

        if (cursor.count > 0){
            cursor.moveToFirst()
            do {
                val funcionario = Funcionario()
                funcionario.funId = cursor.getInt(cursor.getColumnIndex(idFun))
                funcionario.funNome = cursor.getString(cursor.getColumnIndex(nomeFun))
                funcionario.depIdKey = cursor.getString(cursor.getColumnIndex(idDepKey))
                funcionarioLista.add(funcionario)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return funcionarioLista
    }
}