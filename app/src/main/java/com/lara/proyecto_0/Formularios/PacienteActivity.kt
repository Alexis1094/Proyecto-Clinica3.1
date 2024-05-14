package com.lara.proyecto_0.Formularios

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lara.proyecto_0.R
import com.lara.proyecto_0.entidades.Paciente
import com.lara.proyecto_0.modelo.PacienteDAO

class PacienteActivity : AppCompatActivity() {
    private lateinit var txtDni: EditText
    private lateinit var txtNombres: EditText
    private lateinit var txtClave: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var txtEdad: EditText

    private lateinit var btnRegistrar: Button
    private lateinit var btnCancelar: Button

    private lateinit var oPacienteDao: PacienteDAO
    private var modificar = false
    private var id:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_paciente)

        asignarReferencias()
        //recuperarDatos()
    }

    fun asignarReferencias()
    {
        txtDni=findViewById(R.id.txtDni)
        txtNombres=findViewById(R.id.txtNombres)
        txtCorreo=findViewById(R.id.txtCorreo)
        txtEdad=findViewById(R.id.txtEdad)
        txtClave=findViewById(R.id.txtClave)
        oPacienteDao = PacienteDAO(this)
        btnCancelar=findViewById(R.id.btnCancelar)
        btnCancelar.setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        btnRegistrar=findViewById(R.id.btnSalir)
        btnRegistrar.setOnClickListener{
            capturarDatos()
        }
    }

    fun capturarDatos()
    {
        val dni=txtDni.text.toString()
        val nombres=txtNombres.text.toString()
        val correo=txtCorreo.text.toString()
        val edad=txtEdad.text.toString()
        val clave=txtClave.text.toString()

        var valida = true

        if (dni.isEmpty()){
            valida =false
            txtDni.setError("DNI es obligatorio")
        }
        if (dni.length != 8){
            valida =false
            txtDni.setError("El DNI debe ser 8 dígitos")
        }
        if (nombres.isEmpty()){
            valida =false
            txtNombres.setError("El nombres es obligatorio")
        }
        if (correo.isEmpty()){
            valida =false
            txtCorreo.setError("El correo es obligatorio")
        }
        if (edad.isEmpty()){
            valida =false
            txtEdad.setError("La edad es obligatorio")
        }
        if (clave.isEmpty()){
            valida =false
            txtClave.setError("La clave es obligatorio")
        }
        if (clave.length < 4){
            valida =false
            txtClave.setError("La clave debe tener minimo 4 dígitos")
        }
        var listaPaciente = oPacienteDao.validaDni(dni)
        if (listaPaciente.isNotEmpty()) {
            valida =false
            mostrarAlerta("El DNI Registrado ya existe")
        }

        if (valida)
        {
            val paciente=Paciente()
            paciente.dni=dni.toInt()
            paciente.nombrePaciente = nombres
            paciente.correo = correo
            paciente.edad = edad.toInt()
            paciente.clave = clave

            registrar(paciente)
        }
    }

    fun registrar(paciente: Paciente){
        val pacienteDao = PacienteDAO(this)
        val mensaje = pacienteDao.registrarPaciente(paciente)

        mostrarmMensaje(mensaje)
    }

    fun mostrarmMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Mensaje informativo")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", { dialogInterface: DialogInterface, i:Int->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
        ventana.create().show()
    }

    fun mostrarAlerta(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Mensaje informativo")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", null)
        ventana.create().show()
    }
}