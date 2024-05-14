package com.lara.proyecto_0.Formularios

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
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
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.lara.proyecto_0.CallFragment
import com.lara.proyecto_0.R
import com.lara.proyecto_0.Variables.Variables
import com.lara.proyecto_0.databinding.ActivityHistorialCitaBinding
import com.lara.proyecto_0.databinding.ActivityPerfilDatoBinding

class PerfilDatoActivity : AppCompatActivity(), OnMapReadyCallback {
    private  lateinit var map: GoogleMap

    private val binding: ActivityPerfilDatoBinding by lazy {
        ActivityPerfilDatoBinding.inflate(layoutInflater)
    }

    private lateinit var lblPaciente: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_principal)
        setContentView(binding.root)
        setSupportActionBar(binding.mtToolbar)

        setContentView(R.layout.activity_historial_cita)
        setContentView(binding.root)
        setSupportActionBar(binding.mtToolbar)

        val imageView: ImageView = findViewById(R.id.iv_home)
        val drawerLayout: DrawerLayout = findViewById(R.id.dl_drawer)

        imageView.setOnClickListener {
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

        asignarReferencias()
        recuperarDatos()
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

    fun asignarReferencias()
    {
        lblPaciente=findViewById(R.id.lblPaciente)

        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapa) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun recuperarDatos()
    {
        // Acceder al ID del usuario en otra parte de la aplicación
        val pacienteLogueado = Variables.PacienteManager.nombrePaciente
        lblPaciente.text=pacienteLogueado
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        map.uiSettings.isZoomControlsEnabled = true

        val coordenada1 = LatLng(-12.084648486263701, -77.06210264644955)
        val marcador1 = MarkerOptions().position(coordenada1).title("Sede Brasil")
        marcador1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        map.addMarker(marcador1)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordenada1, 16f))

        val coordenada2 = LatLng(-12.049971048657959, -77.0368246329571)
        val marcador2 = MarkerOptions().position(coordenada2).title("Sede Colmena") // Aquí estaba usando coordenada en lugar de coordenada2
        map.addMarker(marcador2)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordenada2, 16f))

        // Calcula el centro del mapa
        val boundsBuilder = LatLngBounds.Builder()
        boundsBuilder.include(coordenada1)
        boundsBuilder.include(coordenada2)
        val bounds = boundsBuilder.build()

        // Calcula el ancho y alto del mapa
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels

        // Define el padding para los bordes del mapa
        val padding = (width * 0.2).toInt() // 20% del ancho de la pantalla

        // Centra y ajusta el nivel de zoom del mapa
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding)
        map.moveCamera(cameraUpdate)
    }
}