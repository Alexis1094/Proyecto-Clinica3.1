package com.lara.proyecto_0.Adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lara.proyecto_0.R
import com.lara.proyecto_0.entidades.Horario

class AdaptadorPersonalizadoHorario: RecyclerView.Adapter<AdaptadorPersonalizadoHorario.MiViewHolder>() {
    private var listaHorarios:ArrayList<Horario.Personalizado> = ArrayList()
    private lateinit var context:Context

    private var onClickDeleteItem:((Horario.Personalizado) -> Unit)?=null

    fun onClickDeleteItem(callback: (Horario.Personalizado) -> Unit){
        this.onClickDeleteItem=callback
    }

    fun agregarDatos(items: ArrayList<Horario.Personalizado>){
        this.listaHorarios = items
    }

    fun contexto(context: Context){
        this.context=context
    }

    class MiViewHolder(var view: View):RecyclerView.ViewHolder(view) {

        private var filaServicioCita=view.findViewById<TextView>(R.id.filaServicioCita)
        private var filaMedicoCita=view.findViewById<TextView>(R.id.filaMedicoCita)
        private var filafechaCita=view.findViewById<TextView>(R.id.filafechaCita)
        private var filaHoraCita=view.findViewById<TextView>(R.id.filaHoraCita)
        //var filaEditar=view.findViewById<ImageButton>(R.id.filaEditar)

        fun bindView(horario: Horario.Personalizado ){
            filaServicioCita.text = horario.nombreServicio.toString()
            filaMedicoCita.text=horario.nombreMedico.toString()
            filafechaCita.text=horario.fecha
            filaHoraCita.text=horario.horaInicio
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MiViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.fila_horarios, parent, false)
    )

    override fun onBindViewHolder(holder: MiViewHolder, position: Int) {
        val horarioItem=listaHorarios[position]
        holder.bindView(horarioItem)

        //holder.filaEditar.setOnClickListener{
            //val intent = Intent(context, HorariosActivity::class.java)
            //intent.putExtra("var_idServicio",listaServicios[position].idServicio)
            //intent.putExtra("var_nombreServicio",listaServicios[position].nombreServicio)
            //intent.putExtra("var_precio",listaServicios[position].precio)

            //Limpiar varibales globales
            /*
            Variables.Servicio.idServicioVal=0
            Variables.Medico.idMedicoVal=0

            Variables.Servicio.idServicioVal = listaHorarios[position].idServicio
            context.startActivity(intent)
             */
        //}

        /*
        holder.filaEliminar.setOnClickListener{
            onClickDeleteItem?.invoke(personaItem)
        }
        */
    }

    override fun getItemCount(): Int {
        return listaHorarios.size
    }

}