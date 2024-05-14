package com.lara.proyecto_0.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.lara.proyecto_0.entidades.Horario
import com.lara.proyecto_0.util.SQLiteHelper

class HorarioDAO (context: Context){
    private var sqLiteHelper:SQLiteHelper= SQLiteHelper(context)

    fun registrarHorario(horario: Horario):String{
        var respuesta =""
        val db= sqLiteHelper.writableDatabase

        try
        {
            var valores = ContentValues()
            valores.put("idMedico",horario.idMedico)
            valores.put("idServicio",horario.idServicio)
            valores.put("idPaciente",horario.idPaciente)
            valores.put("fecha",horario.fecha)
            valores.put("horaInicio",horario.horaInicio)
            valores.put("observacion",horario.observacion)
            var rpta = db.insert("horarios",null,valores)

            if (rpta== -1L)
            {
                respuesta="Error al insertar el horario"
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

    fun eliminarHorario(idHorario:Int):String
    {
        var respuesta = ""
        val db=sqLiteHelper.writableDatabase
        try {
            val rpta = db.delete("horarios","idHorario="+idHorario,null)
            if (rpta== -1){
                respuesta="Error al eliminar el horario"
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

    fun cargarHorarios():ArrayList<Horario>{
        val listaHorarios:ArrayList<Horario> = ArrayList()
        val query = "SELECT * FROM horarios"
        val db= sqLiteHelper.readableDatabase
        val cursor:Cursor

        try{
            cursor=db.rawQuery(query,null)
            cursor.moveToFirst()
            do{
                val idHorario:Int = cursor.getInt(cursor.getColumnIndexOrThrow("idHorario"))
                val idMedico:Int = cursor.getInt(cursor.getColumnIndexOrThrow("idMedico"))
                val idServicio:Int = cursor.getInt(cursor.getColumnIndexOrThrow("idServicio"))
                val idPaciente:Int = cursor.getInt(cursor.getColumnIndexOrThrow("idPaciente"))
                val fecha:String = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
                val horaInicio:String = cursor.getString(cursor.getColumnIndexOrThrow("horaInicio"))
                val observacion:String = cursor.getString(cursor.getColumnIndexOrThrow("observacion"))

                val oHorario= Horario()
                oHorario.idHorario=idHorario
                oHorario.idMedico=idMedico
                oHorario.idServicio=idServicio
                oHorario.idPaciente=idPaciente
                oHorario.fecha=fecha
                oHorario.horaInicio=horaInicio
                oHorario.observacion=observacion

                listaHorarios.add(oHorario)
            }while (cursor.moveToNext())
        }
        catch (e:Exception){
            Log.d("===",e.message.toString())
        }
        finally{
            db.close()
        }

        return  listaHorarios
    }

    fun cargarHorariosPersonalizado(idPaciente:Int):ArrayList<Horario.Personalizado>{
        val listaHorarios:ArrayList<Horario.Personalizado> = ArrayList()
        val query = "SELECT h.idHorario,m.nombreMedico,s.nombreServicio,h.fecha,h.horaInicio,h.observacion FROM horarios h INNER JOIN medicos m ON h.idMedico=m.idMedico INNER JOIN servicios s ON h.idServicio=s.idServicio WHERE h.idPaciente="+idPaciente
        val db= sqLiteHelper.readableDatabase
        val cursor:Cursor

        try{
            cursor=db.rawQuery(query,null)
            cursor.moveToFirst()
            do{
                val nombreMedico:String = cursor.getString(cursor.getColumnIndexOrThrow("nombreMedico"))
                val nombreServicio:String = cursor.getString(cursor.getColumnIndexOrThrow("nombreServicio"))
                val fecha:String = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
                val horaInicio:String = cursor.getString(cursor.getColumnIndexOrThrow("horaInicio"))
                val observacion:String = cursor.getString(cursor.getColumnIndexOrThrow("observacion"))

                val oHorario= Horario.Personalizado()
                oHorario.nombreMedico=nombreMedico
                oHorario.nombreServicio=nombreServicio
                oHorario.fecha=fecha
                oHorario.horaInicio=horaInicio
                oHorario.observacion=observacion

                listaHorarios.add(oHorario)
            }while (cursor.moveToNext())
        }
        catch (e:Exception){
            Log.d("===",e.message.toString())
        }
        finally{
            db.close()
        }

        return  listaHorarios
    }
}