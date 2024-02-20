class Motocicleta   (marca:String, modelo:String,capacidadCombustible:Float,combustibleActual:Float,kilometrosActuales:Int, var cilindrada: Int) : Vehiculo(marca, modelo, capacidadCombustible,combustibleActual,kilometrosActuales)  {
    val KM_LITROS_MOTOS = 20.0f
    val CABALLITO = 5
    override fun calcularAutonomia() = (combustibleActual * KM_LITROS_MOTOS).toInt()
    override fun realizaViaje(distancia: Int): Int {
        if (distancia < calcularAutonomia()) { //comprueba que tiene el combustible necesario para el viaje
            kilometrosActuales += distancia
            combustibleActual -= (distancia / KM_LITROS_MOTOS).redondear()
            return kilometrosActuales
        }else{//el nivel de combustible es bajo y procede a viajar hasta que el combustible quede a 0
            kilometrosActuales = (combustibleActual * KM_LITROS_MOTOS).toInt()
            combustibleActual = 0.0F
            return kilometrosActuales

        }
    }
    fun realizaCaballito(): Int{
        combustibleActual -= CABALLITO / KM_LITROS_MOTOS
    }

}