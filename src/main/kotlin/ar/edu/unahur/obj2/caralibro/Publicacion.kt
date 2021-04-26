package ar.edu.unahur.obj2.caralibro

import java.lang.Exception
import kotlin.math.ceil
import kotlin.Exception as Exception1

abstract class Publicacion {
  var cantidadMeGusta = 0
  var usuariosMeGusta = mutableListOf<Usuario>()
  var permiso = "publico"
  var permitidos = mutableListOf<Usuario>()
  var excluidos = mutableListOf<Usuario>()
//  var propietario = String()

  abstract fun espacioQueOcupa(): Int

  fun meGusta() = cantidadMeGusta++

  fun agregarUsuario(usuario: Usuario) = usuariosMeGusta.add(usuario)



// revisar. ¿como ver el propietario de la publicacion)
//
//  fun puedeVer(usuario: Usuario): Boolean {
//    return usuario.publicaciones.contains(this) ||
//      (usuario.esAmigoDe(propietario) && permiso == "amigos") ||
//      permitidos.contains(usuario) || !excluidos.contains(usuario) ||
//      usuario == propietario
//  }
}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {
  val factorDeCompresion = 0.7
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt()
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Video(val duracion: Int, var calidad: String ) : Publicacion() {


  override fun espacioQueOcupa(): Int {
    var peso = duracion
    if (calidad == "HD720p")
      peso *= 3
    else if (calidad == "HD1080p")
      peso *= 6
    return peso
  }

  fun cambiarCalidad(calidadNueva: String) {
    val calidades = listOf<String>("SD", "HD720p", "HD1080p")
    if (calidades.contains(calidadNueva))
      calidad = calidadNueva
    else throw Exception1("Formatos disponibles <SD, HD720p, HD1080p>")
  }
}