package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion {
  var cantidadMeGusta = 0
  var usuariosMeGusta = mutableListOf<Usuario>()
//  var permiso =

  abstract fun espacioQueOcupa(): Int

  fun meGusta() = cantidadMeGusta ++

  fun agregarUsuario(usuario: Usuario) = usuariosMeGusta.add(usuario)
}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {
  val factorDeCompresion = 0.7
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt()
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Video(val duracion: Int, var calidad: String ) : Publicacion() {
//  faltaria una comprobacion o usar enums
//  val calidades = listOf<String>("SD", "HD720p", "HD1080p")

  override fun espacioQueOcupa(): Int {
    var peso = duracion
    if (calidad == "HD720p") {
      peso *= 3
    } else if (calidad == "HD1080p") {
      peso *= 6
    }
    return peso
  }

  fun cambiarCalidad(calidadNueva: String) {
    calidad = calidadNueva
  }
}