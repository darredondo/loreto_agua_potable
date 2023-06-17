package com.sgap.sistemaaguapotable.models

import java.io.Serializable

data class TomaAgua(
    var idToma: Int = 0,
    var propietario: String = "",
    var fechaInscripcion: String = "",
    var direccion: String = "",
    var activa:Int = 0,
    var idTarifa: Int = 0
):Serializable
