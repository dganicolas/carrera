class Automovil (marca:String, modelo:String,capacidadCombustible:Float,combustibleActual:Float,kilometrosActuales:Int, val esElectrico : Boolean) : Vehiculo(marca, modelo, capacidadCombustible,combustibleActual,kilometrosActuales) {
    val KM_Litros = 15.0f //esto es una constantes
    val DERRAPE = 5

    companion object {
        //preguntar a diego NOCEH
        var condicionBritania: Boolean = false
        fun cambiarCondicionBritania(nuevaCondicion: Boolean) {
            condicionBritania = nuevaCondicion
        }
    }//creo que funciona

    override fun obtenerInformacion(): String {
        return if (!esElectrico) {
            super.obtenerInformacion()
        } else {
            "El $modelo puede recorrer ${combustibleActual * 5} kilometros mas"
        }
    }//esta funcion funciona

    override fun calcularAutonomia(): Int {
        return if (!esElectrico) {
            super.calcularAutonomia()
        } else {
            return (capacidadCombustible * KM_Litros).toInt()
        }
    }//esta funcion funciona

    override fun realizaViaje(distancia: Int): Int {
        if (!esElectrico) {
            return super.realizaViaje(distancia)
        } else {
            if (distancia < calcularAutonomia()) { //comprueba que tiene el combustible necesario para el viaje
                kilometrosActuales += distancia
                combustibleActual -= distancia / KM_Litros
                //lo pongo to-do a dos decimales
                combustibleActual = combustibleActual.redondear()
                return kilometrosActuales
            } else {//el nivel de combustible es bajo y procede a viajar hasta que el combustible quede a 0
                println("nivel de combustible insuficiente, se recorrio ${combustibleActual * KM_Litros}, nivel de combustible a 0")
                kilometrosActuales = (combustibleActual * KM_Litros).toInt()
                combustibleActual = 0.0F
                //lo pongo to-do a dos decimales
                combustibleActual = combustibleActual.redondear()
                return kilometrosActuales
            }
        }
    }//CREO QUE FUNCIONA
    fun realizaDerrape():Float{
        if (esElectrico){
            return (DERRAPE / KM_Litros)
        }else{
            return (DERRAPE/KM_litros_gas)
        }
    }

}