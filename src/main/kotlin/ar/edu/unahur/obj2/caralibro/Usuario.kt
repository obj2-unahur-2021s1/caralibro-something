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

/*
  fun puedeVerPublicacion(usuario: Usuario, publicacion: Publicacion) = this.tienePermiso(usuario, publicacion)
  fun tienePermiso(usuario: Usuario, publicacion: Publicacion): Boolean {
    var resultado = false
    if (publicacion.permiso == "publico") {
      resultado = true
    } else if (publicacion.permiso == "amigos" && usuario.esAmigoDe(this)) {
      resultado = true
    } else if (publicacion.permiso == "privado" && publicacion.permitidos.contains(usuario)) {
      resultado = true
    } else if (publicacion.permiso == "excluidos" && !publicacion.excluidos.contains(usuario)) {
      resultado = true
    }
    return resultado}
*/
/////////////////////////////////////////
////////////////////////////////////////
  // error con permiso publico y excluidos
  //los casos de permiso publico y si el usuario es el propietario deberian dar true directamente
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
//////////////////////////////
//////////////////////////////


//  fun mejoresAmigosDe(usuario: Usuario) = amigos.filter {it.puedeVerTodasLasPublicacionesDe(usuario)}
    /*En está función tenía pensado hacer un filtro de la lista de amigos de un Usuario que sólo contenga los amigos
  del Usuario en cuestión que pueden ver todas las publicaciones de él*/
  fun mejoresAmigos() = amigos.filter { it.puedeVerTodasLasPublicacionesDe(this) }

    //  fun puedeVerTodasLasPublicacionesDe(usuario: Usuario) : Boolean {
//    usuario.publicaciones.forEach(puedeVerUnaPublicacionDe())
//  }
    /*Y acá habría que retornar un booleano para aplicarlo en la función mejoresAmigosDe(), entonces se usa ese booleano
  para configurar el filtro*/
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