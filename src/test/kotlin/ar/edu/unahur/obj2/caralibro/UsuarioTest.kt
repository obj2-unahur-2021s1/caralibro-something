package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz")
    val fotoEnCuzco = Foto(768, 1024)
    val videoJSD = Video(100, "SD")
    val videoJ720 = Video(100, "HD720p")
    val videoJ1080 = Video(100, "HD1080p")
    val videoP720 = Video(200, "HD720p")
    val juana = Usuario()
    val pancho = Usuario()


    describe("Una publicación") {
      describe("de tipo foto") {
        it("ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldBe(550503)
        }
      }

      describe("de tipo texto") {
        it("ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldBe(45)
        }
      }

      describe("de tipo video") {
        it("ocupa, SD duracion, 720 duracion *3, 1080 duracion *6") {
          videoJSD.espacioQueOcupa().shouldBe(100)
          videoJ720.espacioQueOcupa().shouldBe(300)
          videoJ1080.espacioQueOcupa().shouldBe(600)
        }
      }
    }

    describe("Un usuario") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
//        val juana = Usuario()
        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
//      juana.espacioDePublicaciones().shouldBe(550548)
        juana.agregarPublicacion((videoJSD))
        juana.espacioDePublicaciones().shouldBe(550648)
      }

      it("puede cambiar la calidad de video") {
        juana.agregarPublicacion(videoJ720)
        videoJ720.cambiarCalidad("HD1080p")
        juana.espacioDePublicaciones().shouldBe(600)
      }

      it("puede agregar amigo") {
          juana.agregarAmigo(pancho)
          juana.amigos.shouldContain(pancho)
      }
    }

    describe("me gusta") {
        juana.agregarPublicacion((videoJSD))
        pancho.darMeGusta(videoJSD)
        it("puede dar me gusta") {
            videoJSD.cantidadMeGusta.shouldBe(1)
            videoJSD.usuariosMeGusta.shouldContain(pancho)
            pancho.publicacionesMeGustan.shouldContain(videoJSD)
        }
    }

  }
})
