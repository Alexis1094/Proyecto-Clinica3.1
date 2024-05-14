package com.lara.proyecto_0.entidades

class Horario {
    var idHorario:Int=0
    var idMedico:Int=0
    var idServicio:Int=0
    var idPaciente:Int=0
    lateinit var fecha:String
    lateinit var horaInicio:String
    lateinit var observacion:String

    class Personalizado{
        lateinit var nombreMedico:String
        lateinit var nombreServicio:String
        lateinit var fecha:String
        lateinit var horaInicio:String
        lateinit var observacion:String

    }
}