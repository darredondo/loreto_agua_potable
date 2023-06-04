package com.sgap.sistemaaguapotable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.sgap.sistemaaguapotable.databinding.ActivityMainBinding
import com.sgap.sistemaaguapotable.pago.PagoActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        initListeners()
    }

    private fun initListeners() {
        binding.btnRegistrarPago.setOnClickListener {
            startActivity(Intent(this, PagoActivity::class.java))
        }
        binding.btnAjustes.setOnClickListener {
            Toast.makeText(this,"Ajustes",Toast.LENGTH_SHORT).show()
        }
        binding.btnCerrarSesion.setOnClickListener {
            finish()
        }
    }
}