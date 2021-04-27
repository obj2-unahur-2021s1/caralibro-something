package ar.edu.unahur.obj2.caralibro

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import java.lang.Exception

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
        val timon = Usuario()


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

            it("fallo al cambiar la calidad") {
                juana.agregarPublicacion(videoJ720)
                shouldThrow<Exception> { videoJ720.cambiarCalidad("HD10") }
            }

            it("puede agregar amigo") {
                juana.agregarAmigo(pancho)
                juana.amigos.shouldContain(pancho)
            }

            it("puede ver si es mas amistoso que otro usuario") {
                timon.agregarAmigo(juana)
                timon.agregarAmigo(pancho)
                juana.agregarAmigo(pancho)
                timon.soyMasAmistosoQue(juana).shouldBeTrue()
                juana.soyMasAmistosoQue(timon).shouldBeFalse()
            }
        }

        describe("me gusta") {
            timon.agregarAmigo(juana)
            timon.agregarAmigo(pancho)
            juana.agregarAmigo(pancho)
            juana.agregarAmigo(timon)
            pancho.agregarAmigo(timon)
            juana.agregarPublicacion(videoJSD)
            juana.agregarPublicacion(videoJ720)
            pancho.darMeGusta(videoJSD)
            timon.darMeGusta(videoJ720)
            it("puede dar me gusta") {
                videoJSD.usuariosMeGusta.shouldContain(pancho)
                pancho.publicacionesMeGustan.shouldContain(videoJSD)
            }
            it("puede ver cantidad de me gusta") {
                videoJSD.cantidadMeGusta.shouldBe(1)
            }
            it("puede ver la cantidad total de me gusta") {
                juana.totalMeGusta().shouldBe(2)
            }
            it("puede ver quien es el amigo mas popular") {
                timon.amigoMasPopular().shouldBe(juana)
            }
            it("puede ver si un usuario te stalkea") {
                juana.meStalkea(timon).shouldBeFalse()
                timon.darMeGusta(videoJSD)
                juana.meStalkea(timon).shouldBeTrue()
            }
        }

        describe("Permisos") {
            timon.agregarPublicacion(videoJ1080)
            timon.agregarPublicacion(videoJ720)
            timon.agregarPublicacion(videoJSD)
            timon.cambiarAAmigos(videoJ1080)
            timon.agregarAmigo(pancho)
            val permitidos = listOf<Usuario>(pancho)
            timon.cambiarAPrivadoConPermitidos(videoJ720, permitidos)
            timon.cambiarAPublicoConExcluidos(videoJSD, permitidos)

            it("puede cambiar el permiso a amigos y publico") {
                videoJ1080.permiso.shouldBe("amigos")
                timon.cambiarAPublico(videoJ1080)
                videoJ1080.permiso.shouldBe("publico")
            }
            it("puede ver cierta publicacion, privado con lista de permitidos") {
                pancho.puedeVerPublicacion(timon, videoJ720).shouldBeTrue()
            }
            it("no puede ver, el usuario no esta en la lista de permitidos") {
                juana.puedeVerPublicacion(timon, videoJ720).shouldBeFalse()
            }
            it("puede ver, permiso publico con excluidos") {
                juana.puedeVerPublicacion(timon,videoJ720).shouldBeTrue()
            }
            it("no puede ver, el usuario esta en excluidos") {
                pancho.puedeVerPublicacion(timon, videoJSD).shouldBeFalse()
            }
            it("puede ver todas las publicaciones de un usuario") {
                timon.agregarAmigo(juana)
                val permitido = listOf<Usuario>(juana)
                timon.cambiarAPrivadoConPermitidos(videoJ720, permitido)
                juana.puedeVerTodasLasPublicacionesDe(timon).shouldBeTrue()
            }
            it("no puede ver todas las publicaciones") {
                pancho.puedeVerTodasLasPublicacionesDe(timon).shouldBeFalse()
            }
            it("mejores amigos") {
                timon.agregarAmigo(juana)
                timon.mejoresAmigos().shouldContainExactly(juana)
            }
        }
    }
})