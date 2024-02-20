abstract class Vehiculo(var marca:String, var modelo:String, capacidadCombustible:Float,  var combustibleActual:Float,var kilometrosActuales:Int){
    var KM_litros_gas= 10.0f //esto es una constante en minusculas
    val capacidadCombustible:Float = capacidadCombustible.redondear()
    init {
        combustibleActual = combustibleActual.redondear()
        require(capacidadCombustible < 0){"la capacidad del tanque no debe ser negativa"}
        require(combustibleActual <  0){"el nivel de combustible debe ser positivo"}
    }
    open fun obtenerInformacion():String = "El $modelo puede recorrer ${combustibleActual * 10} kilometros mas"
    open fun calcularAutonomia():Int = (combustibleActual * KM_litros_gas).toInt()
    open fun realizaViaje(distancia : Int): Int{ //si tiene capacidad suficiente hace el viaje que le pasamos por parametro
        if (distancia < calcularAutonomia()) { //comprueba que tiene el combustible necesario para el viaje
            kilometrosActuales += distancia
            combustibleActual -= (distancia / KM_litros_gas).redondear()
            return kilometrosActuales
        }else{//el nivel de combustible es bajo y procede a viajar hasta que el combustible quede a 0
            kilometrosActuales = (combustibleActual * KM_litros_gas).toInt()
            combustibleActual = 0.0F
            return kilometrosActuales

        }
    }
    open fun repostar(cantidad: Float = 0f): Float {
        val combustiblePrevio = combustibleActual
        if ( cantidad >= 0 || (cantidad + combustibleActual) > capacidadCombustible) {
            combustibleActual = capacidadCombustible
        } else {
            combustibleActual += cantidad
        }
        return capacidadCombustible - combustiblePrevio
    }
}