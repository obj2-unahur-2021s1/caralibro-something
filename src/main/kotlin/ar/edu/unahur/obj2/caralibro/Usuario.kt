package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val publicacionesMeGustan = mutableListOf<Publicacion>()
  val amigos = mutableListOf<Usuario>()

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

  fun darMeGusta(publicacion: Publicacion) {
    if (!publicacionesMeGustan.contains(publicacion)) {
      publicacion.meGusta()
      publicacion.agregarUsuario(this)
      publicacionesMeGustan.add(publicacion)
    }
  }

  fun totalMeGusta() = publicaciones.sumBy { it.cantidadMeGusta }

  fun agregarAmigo(usuario: Usuario) {
    if (!amigos.contains(usuario)) {
      amigos.add(usuario)
    }
  }

  fun totalAmigos() = this.amigos.size

  fun esAmigoDe(usuario: Usuario) = amigos.contains(usuario)

  fun soyMasAmistosoQue(usuario: Usuario) = this.totalAmigos() > usuario.totalAmigos()

  fun puedeVerPublicacion(usuario: Usuario, publicacion: Publicacion): Boolean {
    var resultado = true
    if (publicacion.permiso == "amigos" && !usuario.esAmigoDe(this)) {
      resultado = false
    }
      else if (((publicacion.permiso == "privado") || (publicacion.permiso == "excluidos"))
        && this.estaExcluidoYNoPermitido(publicacion)) {
       resultado = false
      }
      else if (!usuario.esAmigoDe(this) && publicacion.permiso == "privado") {
        resultado = false
      }
    return resultado
  }

  fun estaExcluidoYNoPermitido(publicacion: Publicacion) = publicacion.excluidos.contains(this) &&
          !publicacion.permitidos.contains(this)

  fun mejoresAmigos() = amigos.filter { it.puedeVerTodasLasPublicacionesDe(this) }

  fun puedeVerTodasLasPublicacionesDe(usuario: Usuario): Boolean {
    var resultado = true
    if (usuario.publicaciones.any { p -> !this.puedeVerPublicacion(usuario, p) }) resultado = false
    return resultado
  }

  fun amigoMasPopular() = this.amigos.maxByOrNull { it.totalAmigos() }

  fun meStalkea(usuario: Usuario) =
    this.publicaciones.count { it.usuariosMeGusta.contains(usuario) } > (publicaciones.size * 0.9)

  fun cambiarAPublico(publicacion: Publicacion) {
    publicacion.permiso = "publico"
  }

  fun cambiarAAmigos(publicacion: Publicacion) {
    publicacion.permiso = "amigos"
  }

  fun cambiarAPrivadoConPermitidos(publicacion: Publicacion, listaDePermitidos: List<Usuario>) {
    publicacion.permiso = "privado"
    publicacion.permitidos.clear()
    publicacion.permitidos.addAll(listaDePermitidos)
  }

  fun cambiarAPublicoConExcluidos(publicacion: Publicacion, listaDeExcluidos: List<Usuario>) {
    publicacion.permiso = "excluidos"
    publicacion.excluidos.clear()
    publicacion.excluidos.addAll(listaDeExcluidos)
  }
}