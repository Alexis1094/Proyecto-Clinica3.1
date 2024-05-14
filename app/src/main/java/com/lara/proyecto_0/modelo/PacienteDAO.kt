package com.lara.proyecto_0.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.lara.proyecto_0.entidades.Paciente
import com.lara.proyecto_0.util.SQLiteHelper

class PacienteDAO (context: Context){
    private var sqLiteHelper:SQLiteHelper= SQLiteHelper(context)

    fun registrarPaciente(paciente: Paciente):String{
        var respuesta =""
        val db= sqLiteHelper.writableDatabase

        try
        {
            var valores = ContentValues()
            valores.put("dni",paciente.dni)
            valores.put("clave",paciente.clave)
            valores.put("nombrePaciente",paciente.nombrePaciente)
            valores.put("edad",paciente.edad)
            valores.put("correo",paciente.correo)
            var rpta = db.insert("pacientes",null,valores)

            if (rpta== -1L)
            {
                respuesta="Error al insertar paciente"
            }
            else
            {
                respuesta="Se registro de manera correcta"
            }
        }
        catch (e:Exception)
        {
            Log.d("===",e.message.toString())
            respuesta = "Ocurrio un error..."
        }
        finally
        {
            db.close()
        }

        return  respuesta
    }

    fun modificarPaciente(paciente: Paciente):String{
        var respuesta =""
        val db= sqLiteHelper.writableDatabase

        try
        {
            var valores = ContentValues()
            valores.put("dni",paciente.dni)
            valores.put("clave",paciente.clave)
            valores.put("nombrePaciente",paciente.nombrePaciente)
            valores.put("edad",paciente.edad)
            valores.put("correo",paciente.correo)
            var rpta = db.update("pacientes",valores,"id="+paciente.idPaciente,null)

            if (rpta== -1)
            {
                respuesta="Error al actualizar paciente"
            }
            else
            {
                respuesta="Se modificó de manera correcta"
            }
        }
        catch (e:Exception)
        {
            Log.d("===",e.message.toString())
            respuesta = "Ocurrio un error..."
        }
        finally
        {
            db.close()
        }
        return  respuesta
    }

    fun eliminarPersona(id:Int):String
    {
        var respuesta = ""
        val db=sqLiteHelper.writableDatabase
        try {
            val rpta = db.delete("pacientes","id="+id,null)
            if (rpta== -1){
                respuesta="Error al eliminar"
            }else{
                respuesta="Se elimino correctamente"
            }
        }catch (e:Exception)
        {
            Log.d("===",e.message.toString())
            respuesta = "Ocurrio un error..."
        }
        finally
        {
            db.close()
        }
        return respuesta
    }

    fun cargarPacientes():ArrayList<Paciente>{
        val listaPacientes:ArrayList<Paciente> = ArrayList()
        val query = "SELECT * FROM pacientes"
        val db= sqLiteHelper.readableDatabase
        val cursor:Cursor

        try{
            cursor=db.rawQuery(query,null)
            cursor.moveToFirst()
            do{
                val idPaciente:Int = cursor.getInt(cursor.getColumnIndexOrThrow("idPaciente"))
                val dni:Int = cursor.getInt(cursor.getColumnIndexOrThrow("dni"))
                val clave:String = cursor.getString(cursor.getColumnIndexOrThrow("clave"))
                val nombrePaciente:String = cursor.getString(cursor.getColumnIndexOrThrow("nombrePaciente"))
                val edad:Int = cursor.getInt(cursor.getColumnIndexOrThrow("edad"))
                val correo:String = cursor.getString(cursor.getColumnIndexOrThrow("correo"))

                val oPaciente= Paciente()
                oPaciente.idPaciente=idPaciente
                oPaciente.dni=dni
                oPaciente.clave=clave
                oPaciente.nombrePaciente=nombrePaciente
                oPaciente.edad=edad
                oPaciente.correo=correo

                listaPacientes.add(oPaciente)
            }while (cursor.moveToNext())
        }
        catch (e:Exception){
            Log.d("===",e.message.toString())
        }
        finally{
            db.close()
        }

        return  listaPacientes
    }

    fun iniciarSersion(dniParametro:String,claveParametro:String):ArrayList<Paciente>{
        val listaPacientes:ArrayList<Paciente> = ArrayList()
        val query = "SELECT * FROM pacientes WHERE dni=$dniParametro AND clave=$claveParametro"
        val db= sqLiteHelper.readableDatabase
        val cursor:Cursor

        try{
            cursor=db.rawQuery(query,null)
            cursor.moveToFirst()
            do{
                val idPaciente:Int = cursor.getInt(cursor.getColumnIndexOrThrow("idPaciente"))
                val dni:Int = cursor.getInt(cursor.getColumnIndexOrThrow("dni"))
                val clave:String = cursor.getString(cursor.getColumnIndexOrThrow("clave"))
                val nombrePaciente:String = cursor.getString(cursor.getColumnIndexOrThrow("nombrePaciente"))
                val edad:Int = cursor.getInt(cursor.getColumnIndexOrThrow("edad"))
                val correo:String = cursor.getString(cursor.getColumnIndexOrThrow("correo"))

                val oPaciente= Paciente()
                oPaciente.idPaciente=idPaciente
                oPaciente.dni=dni
                oPaciente.clave=clave
                oPaciente.nombrePaciente=nombrePaciente
                oPaciente.edad=edad
                oPaciente.correo=correo

                listaPacientes.add(oPaciente)
            }while (cursor.moveToNext())
        }
        catch (e:Exception){
            Log.d("===",e.message.toString())
        }
        finally{
            db.close()
        }

        return  listaPacientes
    }

    fun validaDni(dniParametro:String):ArrayList<Paciente>{
        val listaPacientes:ArrayList<Paciente> = ArrayList()
        val query = "SELECT * FROM pacientes WHERE dni=$dniParametro"
        val db= sqLiteHelper.readableDatabase
        val cursor:Cursor

        try{
            cursor=db.rawQuery(query,null)
            cursor.moveToFirst()
            do{
                val idPaciente:Int = cursor.getInt(cursor.getColumnIndexOrThrow("idPaciente"))
                val dni:Int = cursor.getInt(cursor.getColumnIndexOrThrow("dni"))
                val clave:String = cursor.getString(cursor.getColumnIndexOrThrow("clave"))
                val nombrePaciente:String = cursor.getString(cursor.getColumnIndexOrThrow("nombrePaciente"))
                val edad:Int = cursor.getInt(cursor.getColumnIndexOrThrow("edad"))
                val correo:String = cursor.getString(cursor.getColumnIndexOrThrow("correo"))

                val oPaciente= Paciente()
                oPaciente.idPaciente=idPaciente
                oPaciente.dni=dni
                oPaciente.clave=clave
                oPaciente.nombrePaciente=nombrePaciente
                oPaciente.edad=edad
                oPaciente.correo=correo

                listaPacientes.add(oPaciente)
            }while (cursor.moveToNext())
        }
        catch (e:Exception){
            Log.d("===",e.message.toString())
        }
        finally{
            db.close()
        }

        return  listaPacientes
    }
}