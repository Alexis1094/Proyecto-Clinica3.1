package com.lara.proyecto_0.Adaptador

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lara.proyecto_0.Formularios.HorariosActivity
import com.lara.proyecto_0.R
import com.lara.proyecto_0.Variables.Variables
import com.lara.proyecto_0.entidades.Medico

class AdaptadorPersonalizadoMedico: RecyclerView.Adapter<AdaptadorPersonalizadoMedico.MiViewHolder>() {
    private var listaMedicos:ArrayList<Medico> = ArrayList()

    private lateinit var context:Context

    private var onClickDeleteItem:((Medico) -> Unit)?=null

    fun onClickDeleteItem(callback: (Medico) -> Unit){
        this.onClickDeleteItem=callback
    }

    fun agregarDatos(items: ArrayList<Medico>){
        this.listaMedicos = items
    }

    fun contexto(context: Context){
        this.context=context
    }

    class MiViewHolder(var view: View):RecyclerView.ViewHolder(view) {
        private var cmp=view.findViewById<TextView>(R.id.filafechaCita)
        private var nombreMedico=view.findViewById<TextView>(R.id.filaNombreMedico)
        private var sede=view.findViewById<TextView>(R.id.filaMedicoCita)
        private var imagenMedico = view.findViewById<ImageView>(R.id.imageView2)
        var filaEditarMedico=view.findViewById<ImageButton>(R.id.filaEditarMedico)

        fun bindView(medico: Medico ){
            nombreMedico.text = medico.nombreMedico.toString()
            cmp.text=medico.cmp.toString()
            sede.text=medico.sede.toString()

            val TresCaracteres = medico.nombreMedico.take(3)
            val imagenResource = if (TresCaracteres.equals("DRA", ignoreCase = true)) R.drawable.doctora else R.drawable.doctor
            imagenMedico.setImageResource(imagenResource)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MiViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.fila_medico, parent, false)
    )

    override fun onBindViewHolder(holder: MiViewHolder, position: Int) {
        val medicoItem=listaMedicos[position]
        holder.bindView(medicoItem)


        holder.filaEditarMedico.setOnClickListener{
            val intent = Intent(context, HorariosActivity::class.java)
            intent.putExtra("var_idMedico",listaMedicos[position].idMedico)
            intent.putExtra("var_nombreMedico",listaMedicos[position].nombreMedico)
            intent.putExtra("var_cmp",listaMedicos[position].cmp)
            intent.putExtra("var_sede",listaMedicos[position].sede)

            //Limpiar varibales globales
            Variables.Servicio.idServicioVal=0
            Variables.Medico.idMedicoVal=0

            Variables.Medico.idMedicoVal = listaMedicos[position].idMedico
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return listaMedicos.size
    }

}