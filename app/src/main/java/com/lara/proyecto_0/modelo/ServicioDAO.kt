package com.lara.proyecto_0.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.lara.proyecto_0.entidades.Medico
import com.lara.proyecto_0.entidades.Paciente
import com.lara.proyecto_0.entidades.Servicio
import com.lara.proyecto_0.util.SQLiteHelper

class ServicioDAO (context: Context){
    private var sqLiteHelper:SQLiteHelper= SQLiteHelper(context)

    fun registrarServicio(servicio: Servicio):String{
        var respuesta =""
        val db= sqLiteHelper.writableDatabase

        try
        {
            var valores = ContentValues()
            valores.put("nombreServicio",servicio.nombreServicio)
            valores.put("precio",servicio.precio)
            var rpta = db.insert("servicios",null,valores)

            if (rpta== -1L)
            {
                respuesta="Error al insertar servicio"
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

    fun cargarServicios():ArrayList<Servicio>{
        val listaServicios:ArrayList<Servicio> = ArrayList()
        val query = "SELECT * FROM servicios"
        val db= sqLiteHelper.readableDatabase
        val cursor:Cursor

        try{
            cursor=db.rawQuery(query,null)
            cursor.moveToFirst()
            do{
                val idServicio:Int = cursor.getInt(cursor.getColumnIndexOrThrow("idServicio"))
                val nombreServicio:String = cursor.getString(cursor.getColumnIndexOrThrow("nombreServicio"))
                val precio:Double = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"))

                val oServicio= Servicio()
                oServicio.idServicio=idServicio
                oServicio.nombreServicio=nombreServicio
                oServicio.precio=precio

                listaServicios.add(oServicio)
            }while (cursor.moveToNext())
        }
        catch (e:Exception){
            Log.d("===",e.message.toString())
        }
        finally{
            db.close()
        }

        return  listaServicios
    }

}