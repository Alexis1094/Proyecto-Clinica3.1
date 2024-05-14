package com.lara.proyecto_0.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){
    companion object{
        private const val DATABASE_NAME ="clinica.db"
        private const val DATABASE_VERSION=5

        private const val sql_pacientes = "CREATE TABLE IF NOT EXISTS pacientes " +
                "(idPaciente INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dni INTEGER NOT NULL," +
                "clave TEXT NOT NULL," +
                "nombrePaciente TEXT NOT NULL, " +
                "edad INTEGER NOT NULL, " +
                "correo TEXT NOT NULL);"

        private const val sql_medicos = "CREATE TABLE IF NOT EXISTS medicos " +
                "(idMedico INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombreMedico TEXT NOT NULL, " +
                "sede TEXT NOT NULL, " +
                "cmp TEXT NOT NULL);"

        private const val sql_servicios = "CREATE TABLE IF NOT EXISTS servicios " +
                "(idServicio INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombreServicio TEXT NOT NULL, " +
                "precio REAL NOT NULL);"

        private const val sql_horarios = "CREATE TABLE IF NOT EXISTS horarios " +
                "(idHorario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idMedico INT NOT NULL, " +
                "idServicio INT NOT NULL, " +
                "idPaciente INT NOT NULL, " +
                "fecha TEXT NOT NULL, " +
                "horaInicio TEXT NOT NULL, " +
                "observacion TEXT NOT NULL);"

        private const val sql_citas = "CREATE TABLE IF NOT EXISTS citas " +
                "(idCita INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idHorario INT NOT NULL, " +
                "idPaciente INT NOT NULL, " +
                "observacion TEXT NOT NULL);"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(sql_pacientes)
        db.execSQL(sql_medicos)
        db.execSQL(sql_servicios)
        db.execSQL(sql_horarios)
        db.execSQL(sql_citas)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS personas")
        db.execSQL("DROP TABLE IF EXISTS medicos")
        db.execSQL("DROP TABLE IF EXISTS servicios")
        db.execSQL("DROP TABLE IF EXISTS horarios")
        db.execSQL("DROP TABLE IF EXISTS citas")
        onCreate(db)
    }

}