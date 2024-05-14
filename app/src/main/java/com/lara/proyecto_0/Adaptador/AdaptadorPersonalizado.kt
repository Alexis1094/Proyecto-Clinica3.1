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
import com.lara.proyecto_0.Formularios.MainActivity
import com.lara.proyecto_0.R
import com.lara.proyecto_0.Variables.Variables
import com.lara.proyecto_0.entidades.Servicio

class AdaptadorPersonalizado: RecyclerView.Adapter<AdaptadorPersonalizado.MiViewHolder>() {
    private var listaServicios:ArrayList<Servicio> = ArrayList()
    private lateinit var context:Context

    private var onClickDeleteItem:((Servicio) -> Unit)?=null

    fun onClickDeleteItem(callback: (Servicio) -> Unit){
        this.onClickDeleteItem=callback
    }

    fun agregarDatos(items: ArrayList<Servicio>){
        this.listaServicios = items
    }

    fun contexto(context: Context){
        this.context=context
    }

    class MiViewHolder(var view: View):RecyclerView.ViewHolder(view) {
        private var precio=view.findViewById<TextView>(R.id.filaPrecio)
        private var nombreServicio=view.findViewById<TextView>(R.id.filaNombreServicios)
        private var imagenServicio = view.findViewById<ImageView>(R.id.filaImgServicio)
        var filaEditar=view.findViewById<ImageButton>(R.id.filaEditar)

        fun bindView(servicio: Servicio ){
            nombreServicio.text = servicio.nombreServicio.toString()
            precio.text=servicio.precio.toString()

            var imagenResource = R.drawable.otraespecialidad

            if (servicio.nombreServicio.equals("CARDIOLOGIA", ignoreCase = true))
                imagenResource= R.drawable.cardiologia
            else if (servicio.nombreServicio.equals("UROLOGIA", ignoreCase = true))
                imagenResource= R.drawable.urologia
            else if (servicio.nombreServicio.equals("PEDIATRIA", ignoreCase = true))
                imagenResource= R.drawable.pediatria
            else if (servicio.nombreServicio.equals("OFTALMOLOGIA", ignoreCase = true))
                imagenResource= R.drawable.oftalmologia
            else if (servicio.nombreServicio.equals("GINECOLOGIA", ignoreCase = true))
                imagenResource= R.drawable.ginecologia
            else if (servicio.nombreServicio.equals("GASTROENTEROLOGIA", ignoreCase = true))
                imagenResource= R.drawable.gastroenterologia
            else
                imagenResource= R.drawable.otraespecialidad

            imagenServicio.setImageResource(imagenResource)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MiViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.fila, parent, false)
    )

    override fun onBindViewHolder(holder: MiViewHolder, position: Int) {
        val servicioItem=listaServicios[position]
        holder.bindView(servicioItem)

        holder.filaEditar.setOnClickListener{
            val intent = Intent(context, HorariosActivity::class.java)
            //intent.putExtra("var_idServicio",listaServicios[position].idServicio)
            //intent.putExtra("var_nombreServicio",listaServicios[position].nombreServicio)
            //intent.putExtra("var_precio",listaServicios[position].precio)

            //Limpiar varibales globales
            Variables.Servicio.idServicioVal=0
            Variables.Medico.idMedicoVal=0

            Variables.Servicio.idServicioVal = listaServicios[position].idServicio
            context.startActivity(intent)
        }

        /*
        holder.filaEliminar.setOnClickListener{
            onClickDeleteItem?.invoke(personaItem)
        }
        */
    }

    override fun getItemCount(): Int {
        return listaServicios.size
    }

}