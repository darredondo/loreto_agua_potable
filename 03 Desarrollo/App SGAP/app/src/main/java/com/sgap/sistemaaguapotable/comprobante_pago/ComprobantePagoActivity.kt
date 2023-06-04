package com.sgap.sistemaaguapotable.comprobante_pago

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.sgap.sistemaaguapotable.databinding.ActivityComprobantePagoBinding

class ComprobantePagoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComprobantePagoBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComprobantePagoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombreCobrador = intent?.extras?.getString("nombre_cobrador","")
        val toma = intent?.extras?.getString("toma","")
        val fecha = intent?.extras?.getString("fecha","")
        val monto = intent?.extras?.getString("monto","")

        binding.tvNombreCobrador.text = nombreCobrador
        binding.tvNumeroToma.text = toma
        binding.tvFechaPago.text = fecha
        binding.tvMonto.text = monto

        binding.btnBack.setOnClickListener {
            finish()
        }

    }
}