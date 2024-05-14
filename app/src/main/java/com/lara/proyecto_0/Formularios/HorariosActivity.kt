package com.lara.proyecto_0.Formularios

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lara.proyecto_0.R
import com.lara.proyecto_0.Variables.Variables
import com.lara.proyecto_0.entidades.Horario
import com.lara.proyecto_0.entidades.Paciente
import com.lara.proyecto_0.modelo.HorarioDAO
import com.lara.proyecto_0.modelo.MedicoDAO
import com.lara.proyecto_0.modelo.PacienteDAO
import com.lara.proyecto_0.modelo.ServicioDAO

class HorariosActivity : AppCompatActivity() {
    private lateinit var sp_medicos: Spinner
    private lateinit var sp_servicios: Spinner
    private lateinit var oMedicoDao: MedicoDAO
    private lateinit var oServicioDao: ServicioDAO
    private lateinit var datePicker: DatePicker
    private lateinit var tmHoraCita: TimePicker
    private lateinit var btnGuardarHorario: Button
    private var idServicio:Int = 0
    private var idMedico:Int = 0
    private var hora:String = ""
    private var fecha:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_horarios)
        val timePicker = findViewById<TimePicker>(R.id.tmHoraCita)
        timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            hora=hourOfDay.toString()+":"+minute.toString()
        }
        asignarReferencias()
    }

    fun asignarReferencias()
    {
        sp_medicos=findViewById(R.id.sp_medicos)
        sp_servicios=findViewById(R.id.sp_servicios)
        datePicker=findViewById(R.id.datePicker)
        tmHoraCita=findViewById(R.id.tmHoraCita)
        btnGuardarHorario=findViewById(R.id.btnGuardarHorario)
        oMedicoDao = MedicoDAO(this)
        oServicioDao = ServicioDAO(this)

        btnGuardarHorario.setOnClickListener{
            capturarDatos()
        }

        cargarComboMedico()
        cargarComboServicio()
        recuperarDatos()
    }

    fun recuperarDatos()
    {
        val idServicio = Variables.Servicio.idServicioVal
        sp_servicios.setSelection((idServicio-1))

        val idMedico = Variables.Medico.idMedicoVal
        sp_medicos.setSelection((idMedico-1))
    }

    fun capturarDatos()
    {
        // Obtener la fecha seleccionada
        val year = datePicker.year
        val month = datePicker.month // El mes está basado en cero, es decir, enero es 0
        val dayOfMonth = datePicker.dayOfMonth
        val pacienteIdLogueado = Variables.PacienteIdManager.idPaciente

        val horario= Horario()
        horario.idServicio=idServicio
        horario.idMedico = idMedico
        horario.idPaciente = pacienteIdLogueado.toInt()
        horario.fecha=dayOfMonth.toString()+"/"+(month + 1).toString()+"/"+year.toString()//"08/05/2024"
        horario.horaInicio = hora
        horario.observacion = ""

        registrar(horario)
    }

    fun registrar(horario: Horario){
        val horarioDao = HorarioDAO(this)
        val mensaje = horarioDao.registrarHorario(horario)

        mostrarmMensaje(mensaje)
    }

    fun mostrarmMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Mensaje informativo")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", { dialogInterface: DialogInterface, i:Int->
            val intent = Intent(this, HistorialCitaActivity::class.java)
            startActivity(intent)
        })
        ventana.create().show()
    }

    fun mostrarAlerta(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Mensaje informativo")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", { dialogInterface: DialogInterface, i:Int->
            //val intent = Intent(this, HistorialCitaActivity::class.java)
            //startActivity(intent)
        })
        ventana.create().show()
    }

    fun cargarComboMedico(){
        var listaMedicos = oMedicoDao.cargarMedicos()
        val listaMedicosConId = listaMedicos.map { medico -> Pair(medico.idMedico, medico.nombreMedico) }
        val adaptador = ArrayAdapter<Pair<Int, String>>(this, android.R.layout.simple_spinner_item, listaMedicosConId)
        sp_medicos.adapter = adaptador

        sp_medicos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val (idMedicoVal, nombreMedicoVal) = listaMedicosConId[position]
                //Log.d("SpinnerSelection", "ID Médico seleccionado: $idMedico, Nombre: $nombreMedico")
                idMedico = idMedicoVal
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Manejar caso donde no se ha seleccionado nada
            }
        }
    }

    fun cargarComboServicio(){
        var listaServicios = oServicioDao.cargarServicios()
        val listaServiciosConId = listaServicios.map { servicio -> Pair(servicio.idServicio, servicio.nombreServicio) }
        val adaptador = ArrayAdapter<Pair<Int, String>>(this, android.R.layout.simple_spinner_item, listaServiciosConId)
        sp_servicios.adapter = adaptador

        sp_servicios.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val (idServicioVal, nombreServicioVal) = listaServiciosConId[position]
                idServicio =idServicioVal
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Manejar caso donde no se ha seleccionado nada
            }
        }
    }


}