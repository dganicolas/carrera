/*esHibrido (Boolean): Indica si el automóvil es híbrido (eléctrico + gasolina) o no (solo gasolina).
condicionBritanica (Boolean): Propiedad de clase que indica si los automóviles están configurados para conducción británica (volante a la derecha) o no.
Comportamiento Especializado:
Comportamiento Adicional:
cambiarCondicionBritanica(nuevaCondicion: Boolean): Método de clase que permite modificar la configuración de conducción británica para todos los automóviles.
*/
class Automovil (nombre:String, marca:String, modelo:String,capacidadCombustible:Float,combustibleActual:Float,kilometrosActuales:Float, val esHibrido: Boolean) : Vehiculo(nombre, marca, modelo, capacidadCombustible,combustibleActual,kilometrosActuales) {
    val KM_Hibrido_Litros = 15.0f //esto es una constantes
    val DERRAPE = 7.5F
    val DERRAPE_HIBRIDO = 6.25F
    companion object {
        //preguntar a diego NOCEH
        var condicionBritania: Boolean = false
        fun cambiarCondicionBritania(nuevaCondicion: Boolean) {
            condicionBritania = nuevaCondicion
        }
    }//creo que funciona

    override fun obtenerInformacion(): String {
        return if (!esHibrido) {
            super.obtenerInformacion()
        } else {
            "El $modelo puede recorrer ${combustibleActual * KM_Hibrido_Litros} kilometros mas"
        }
    }//esta funcion funciona

    /*calcularAutonomia() -> Float: Modifica el cálculo de autonomía asumiendo un rendimiento de 5 km más por litro si es híbrido.*/
    override fun calcularAutonomia(): Float {
        return if (!esHibrido) {
            super.calcularAutonomia()
        } else {
            return (combustibleActual * KM_Hibrido_Litros)
        }
    }//esta funcion funciona

    override fun realizaViaje(distancia: Float): Float {
        if (!esHibrido) {
            return super.realizaViaje(distancia)
        } else {
            if (distancia < calcularAutonomia()) { //comprueba que tiene el combustible necesario para el viaje
                kilometrosActuales += distancia
                combustibleActual -= distancia / KM_Hibrido_Litros
                //lo pongo to-do a dos decimales
                combustibleActual = combustibleActual.redondear()
                return kilometrosActuales
            } else {//el nivel de combustible es bajo y procede a viajar hasta que el combustible quede a 0
                kilometrosActuales += (combustibleActual * KM_Hibrido_Litros)
                combustibleActual = 0.0F
                //lo pongo to-do a dos decimales
                combustibleActual = combustibleActual.redondear()
                return kilometrosActuales
            }
        }
    }//CREO QUE FUNCIONA
    /*realizaDerrape()-> Float: método que simula un derrape. Realiza una gasto adicional en el combustible, retornando el combustible restante. El gasto equivale a haber realizado 7,5 km o 6,25 km si es híbrido.*/
    fun realizaDerrape():Float{

        return if (esHibrido){
            combustibleActual -= DERRAPE_HIBRIDO / KM_Hibrido_Litros
            (DERRAPE_HIBRIDO / KM_Hibrido_Litros)
        }else{
            combustibleActual -= DERRAPE/KM_LITROS_GAS
            (DERRAPE/KM_LITROS_GAS)
        }
    }

}