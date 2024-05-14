package com.lara.proyecto_0.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.lara.proyecto_0.entidades.Medico
import com.lara.proyecto_0.entidades.Paciente
import com.lara.proyecto_0.util.SQLiteHelper

class MedicoDAO (context: Context){
    private var sqLiteHelper:SQLiteHelper= SQLiteHelper(context)

    fun registrarMedico(medico: Medico):String{
        var respuesta =""
        val db= sqLiteHelper.writableDatabase

        try
        {
            var valores = ContentValues()
            valores.put("nombreMedico",medico.nombreMedico)
            valores.put("sede",medico.sede)
            valores.put("cmp",medico.cmp)
            var rpta = db.insert("medicos",null,valores)

            if (rpta== -1L)
            {
                respuesta="Error al insertar médico"
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

    fun modificarMedico(medico: Medico):String{
        var respuesta =""
        val db= sqLiteHelper.writableDatabase

        try
        {
            var valores = ContentValues()
            valores.put("nombreMedico",medico.nombreMedico)
            valores.put("sede",medico.sede)
            valores.put("cmp",medico.cmp)
            var rpta = db.update("medicos",valores,"id="+medico.idMedico,null)

            if (rpta== -1)
            {
                respuesta="Error al actualizar medico"
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

    fun eliminarMedico(idMedico:Int):String
    {
        var respuesta = ""
        val db=sqLiteHelper.writableDatabase
        try {
            val rpta = db.delete("medicos","idMedico="+idMedico,null)
            if (rpta== -1){
                respuesta="Error al eliminar el medico"
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

    fun cargarMedicos():ArrayList<Medico>{
        val listaMedicos:ArrayList<Medico> = ArrayList()
        val query = "SELECT * FROM medicos"
        val db= sqLiteHelper.readableDatabase
        val cursor:Cursor

        try{
            cursor=db.rawQuery(query,null)
            cursor.moveToFirst()
            do{
                val idMedico:Int = cursor.getInt(cursor.getColumnIndexOrThrow("idMedico"))
                val nombreMedico:String = cursor.getString(cursor.getColumnIndexOrThrow("nombreMedico"))
                val sede:String = cursor.getString(cursor.getColumnIndexOrThrow("sede"))
                val cmp:String = cursor.getString(cursor.getColumnIndexOrThrow("cmp"))

                val oMedico= Medico()
                oMedico.idMedico=idMedico
                oMedico.nombreMedico=nombreMedico
                oMedico.sede=sede
                oMedico.cmp=cmp

                listaMedicos.add(oMedico)
            }while (cursor.moveToNext())
        }
        catch (e:Exception){
            Log.d("===",e.message.toString())
        }
        finally{
            db.close()
        }

        return  listaMedicos
    }


    fun spinnerMedicos():ArrayList<Medico>{
        val listaMedicos:ArrayList<Medico> = ArrayList()
        val query = "SELECT * FROM medicos"
        val db= sqLiteHelper.readableDatabase
        val cursor:Cursor

        try{
            cursor=db.rawQuery(query,null)
            cursor.moveToFirst()
            do{
                val idMedico:Int = cursor.getInt(cursor.getColumnIndexOrThrow("idMedico"))
                val nombreMedico:String = cursor.getString(cursor.getColumnIndexOrThrow("nombreMedico"))
                val sede:String = cursor.getString(cursor.getColumnIndexOrThrow("sede"))
                val cmp:String = cursor.getString(cursor.getColumnIndexOrThrow("cmp"))

                val oMedico= Medico()
                oMedico.idMedico=idMedico
                oMedico.nombreMedico=nombreMedico
                oMedico.sede=sede
                oMedico.cmp=cmp

                listaMedicos.add(oMedico)
            }while (cursor.moveToNext())
        }
        catch (e:Exception){
            Log.d("===",e.message.toString())
        }
        finally{
            db.close()
        }

        return  listaMedicos
    }
}