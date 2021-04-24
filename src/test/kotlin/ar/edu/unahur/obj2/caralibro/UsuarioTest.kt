package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz")
    val fotoEnCuzco = Foto(768, 1024)
    val videoASD = Video(100, "SD")
    val videoA720 = Video(100, "HD720p")
    val videoA1080 = Video(100, "HD1080p")
    val juana = Usuario()


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
          videoASD.espacioQueOcupa().shouldBe(100)
          videoA720.espacioQueOcupa().shouldBe(300)
          videoA1080.espacioQueOcupa().shouldBe(600)
        }
      }
    }

    describe("Un usuario") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
//        val juana = Usuario()
        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
//      juana.espacioDePublicaciones().shouldBe(550548)
        juana.agregarPublicacion((videoASD))
        juana.espacioDePublicaciones().shouldBe(550648)
      }

      it("puede cambiar la calidad de video") {
        juana.agregarPublicacion(videoA720)
        videoA720.cambiarCalidad("HD1080p")
        juana.espacioDePublicaciones().shouldBe(600)
      }
    }
  }
})
