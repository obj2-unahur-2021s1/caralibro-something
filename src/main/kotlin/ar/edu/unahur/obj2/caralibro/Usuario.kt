package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val publicacionesMeGustan = mutableListOf<Publicacion>()

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

// revisar
  fun darMeGusta(publicacion: Publicacion) {
    if (!publicacionesMeGustan.contains(publicacion)) {
      publicacion.meGusta()
      publicacion.agregarUsuario(this)
      publicacionesMeGustan.add(publicacion)
    }
  }
}

