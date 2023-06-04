package com.sgap.sistemaaguapotable.pago

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sgap.sistemaaguapotable.comprobante_pago.ComprobantePagoActivity
import com.sgap.sistemaaguapotable.connexionsql.Constantes.BASE_URL
import com.sgap.sistemaaguapotable.databinding.ActivityPagoBinding
import com.sgap.sistemaaguapotable.models.TomaAgua
import org.json.JSONArray
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.Date

class PagoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPagoBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        loadData()
        initListeners()
    }

    @SuppressLint("SimpleDateFormat")
    private fun loadData() {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val fechaPago = sdf.format(Date())
        binding.tvFechaCampo.text = fechaPago
        loadSpinner()
    }

    private fun loadSpinner() {
        val listaTomasAgua = listOf<String>().toMutableList()

        val queue = Volley.newRequestQueue(this)
        val url = "${BASE_URL}getTomasDeAgua.php"

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val tomaAgua = TomaAgua(
                            jsonObject.getString("idtoma_agua").toInt(),
                            jsonObject.getString("propietario"),
                            jsonObject.getString("fecha_inscripcion"),
                            jsonObject.getString("direccion"),
                            jsonObject.getString("activa").toInt(),
                            jsonObject.getString("tarifa_idtarifa").toInt()
                        )
                        listaTomasAgua.add(tomaAgua.idTarifa.toString())
                    }
                    binding.spTomasAgua.adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        listaTomasAgua
                    )
                    binding.spTomasAgua.onItemSelectedListener = object: OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long,
                        ) {
                            loadTarifa(listaTomasAgua[position])
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) { }

                    }
                } catch (e: JSONException) {
                    Log.e("LOAD_SPINNER", "Error al cargar las tomas de agua")
                }
            }, { error ->
                error?.let {
                    Log.e("LOAD_SPINNER", error.localizedMessage!!.toString())
                }
            })
        queue.add(stringRequest)
    }

    private fun loadTarifa(idTarifa: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "${BASE_URL}getTarifaById.php?id=$idTarifa"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    val jsonObject = jsonArray.getJSONObject(0)
                    val monto = jsonObject.getString("costo_mensual")
                    binding.tvMontoCampo.text = "$$monto"
                } catch (e: JSONException) {
                    Log.e("ITEM_SELECTED", "Error al cargar el monto de la tarifa")
                }
            },
            { error ->
                error?.let {
                    Log.e("ITEM_SELECTED", error.localizedMessage!!.toString())
                }
            })
        queue.add(stringRequest)
    }

    private fun initListeners() {
        binding.ivBtnBack.setOnClickListener {
            finish()
        }
        binding.btnRegresar.setOnClickListener {
            finish()
        }
        binding.btnRegistrarPagoMensual.setOnClickListener {
            registrarPago()
        }
        binding.btnGenerarComprobante.setOnClickListener {
            mostrarComprobante()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun registrarPago() {
        val numToma = binding.spTomasAgua.selectedItem.toString()
        val fechaPago = binding.tvFechaCampo.text.toString()
        val monto = binding.tvMontoCampo.text.toString()

        val queue = Volley.newRequestQueue(this)
        val url = "${BASE_URL}insertNuevoPago.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            { response ->
                if (response == "Registro exitoso") {
                    binding.clFormulario.isVisible = false
                    binding.clResultado.isVisible = true
                } else {
                    Toast.makeText(this,"Error al registrar el pago",Toast.LENGTH_LONG).show()
                }
            },
            { error ->
                error?.let {
                    Log.e("REGISTER_PAYMENT", error.localizedMessage!!.toString())
                }
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["fecha"] = fechaPago
                params["monto"] = monto.replace("$","")
                params["idtoma"] = numToma
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }
        }
        queue.add(stringRequest)
    }

    private fun mostrarComprobante() {
        Intent(this,ComprobantePagoActivity::class.java).also {
            it.putExtra("nombre_cobrador",binding.tvCobradorCampo.text)
            it.putExtra("toma",binding.spTomasAgua.selectedItem.toString())
            it.putExtra("fecha",binding.tvFechaCampo.text.toString())
            it.putExtra("monto",binding.tvMontoCampo.text.toString())
            startActivity(it)
        }
    }

}