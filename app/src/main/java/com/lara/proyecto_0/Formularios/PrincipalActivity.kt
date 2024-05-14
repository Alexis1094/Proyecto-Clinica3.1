package com.lara.proyecto_0.Formularios

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lara.proyecto_0.Adaptador.AdaptadorPersonalizado
import com.lara.proyecto_0.CallFragment
import com.lara.proyecto_0.R
import com.lara.proyecto_0.Variables.Variables
import com.lara.proyecto_0.databinding.ActivityPrincipalBinding
import com.lara.proyecto_0.modelo.ServicioDAO
import org.w3c.dom.Text

class PrincipalActivity : AppCompatActivity() {

    private val binding: ActivityPrincipalBinding by lazy {
        ActivityPrincipalBinding.inflate(layoutInflater)
    }

    private lateinit var btnSalir: Button
    private lateinit var lblPaciente: TextView
    private lateinit var rvServicios: RecyclerView
    private lateinit var oServicioDao: ServicioDAO
    private var adaptador:AdaptadorPersonalizado=AdaptadorPersonalizado()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_principal)
        setContentView(binding.root)
        setSupportActionBar(binding.mtToolbar)

        asignarReferencias()
        mostrarServicios()
        recuperarDatos()

        val imageView: ImageView = findViewById(R.id.iv_home)
        val drawerLayout: DrawerLayout = findViewById(R.id.dl_drawer)

        imageView.setOnClickListener {
            // Abre el cajón del menú lateral
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val toogle = ActionBarDrawerToggle(this,
            binding.dlDrawer,
            binding.mtToolbar,
            R.string.open_nav,
            R.string.close_name
        )

        binding.dlDrawer.addDrawerListener(toogle)
        toogle.syncState()

        binding.nvMenuLateral.setNavigationItemSelectedListener {
            onNavigationItemSelected(it)
            true
        }
    }

    private fun getOpenFacebookIntent(): Intent? {
        return try {
            packageManager.getPackageInfo("com.facebook.katana", 0)
            Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/426253597411506"))
        } catch (e: Exception) {
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/appetizerandroid"))
        }
    }

    override fun onBackPressed() {
        binding.dlDrawer.run{
            if (isDrawerOpen(GravityCompat.START))
                closeDrawer(GravityCompat.START)
            else
                super.onBackPressed()
        }
    }

    fun onNavigationItemSelected(item: MenuItem){
        var mensaje = ""
        when(item.itemId){
            R.id.menu_home_icon -> {
                binding.mtToolbar.setTitle("Inicio")
            }
            R.id.menu_reservamedico_icon -> {
                binding.mtToolbar.setTitle("Reservar Por Medico")
                loadFragment(CallFragment.newInstance(android.R.color.holo_green_light))

                val intent = Intent(this, ListarMedicoActivity::class.java)
                intent.putExtra("mensaje", mensaje)
                startActivity(intent)
            }
            R.id.menu_reservaservicio_icon -> {
                binding.mtToolbar.setTitle("Reservar Por Servicio")
                loadFragment(CallFragment.newInstance(android.R.color.holo_green_light))

                val intent = Intent(this, PrincipalActivity::class.java)
                intent.putExtra("mensaje", mensaje)
                startActivity(intent)
            }
            R.id.menu_historialcitas_icon -> {
                binding.mtToolbar.setTitle("Historial de Citas")
                loadFragment(CallFragment.newInstance(android.R.color.holo_green_light))

                val intent = Intent(this, HistorialCitaActivity::class.java)
                intent.putExtra("mensaje", mensaje)
                startActivity(intent)
            }
            R.id.menu_perfil_icon -> {
                binding.mtToolbar.setTitle("Perfil")
                loadFragment(CallFragment.newInstance(android.R.color.holo_green_light))

                val intent = Intent(this, PerfilDatoActivity::class.java)
                intent.putExtra("mensaje", mensaje)
                startActivity(intent)
            }
            R.id.menu_mant_medico -> {
                binding.mtToolbar.setTitle("Mant. Medico")
                loadFragment(CallFragment.newInstance(android.R.color.holo_green_light))

                val intent = Intent(this, MedicoActivity::class.java)
                intent.putExtra("mensaje", mensaje)
                startActivity(intent)
            }
            R.id.menu_mant_servicio -> {
                binding.mtToolbar.setTitle("Mant. Servicio")
                loadFragment(CallFragment.newInstance(android.R.color.holo_green_light))

                val intent = Intent(this, ServicioActivity::class.java)
                intent.putExtra("mensaje", mensaje)
                startActivity(intent)
            }
            R.id.menu_compartir -> {
                binding.mtToolbar.setTitle("Compartir")
                loadFragment(CallFragment.newInstance(android.R.color.holo_green_light))

                startActivity(getOpenFacebookIntent());
            }
            R.id.menu_salir -> {
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
        Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show()
        binding.dlDrawer.closeDrawer(GravityCompat.START)
    }

    fun loadFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment!!)
            .commit()
    }

    fun asignarReferencias(){
        btnSalir=findViewById(R.id.btnSalir)
        lblPaciente=findViewById(R.id.lblPaciente)
        btnSalir.setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        oServicioDao = ServicioDAO(this)
        rvServicios=findViewById(R.id.rvServicios)
        rvServicios.layoutManager= LinearLayoutManager(this)
        rvServicios.adapter = adaptador
    }

    fun mostrarMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Mensaje informativo")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", { dialogInterface: DialogInterface, i:Int->
            val intent =Intent(this,PrincipalActivity::class.java)
            startActivity(intent)
        })
        ventana.create().show()
    }

    fun mostrarServicios(){
        var listaServicios = oServicioDao.cargarServicios()
        adaptador.agregarDatos(listaServicios)
        adaptador.contexto(this)
    }

    fun recuperarDatos()
    {
        // Acceder al ID del usuario en otra parte de la aplicación
        val pacienteLogueado = Variables.PacienteManager.nombrePaciente
        lblPaciente.text=pacienteLogueado
    }


}