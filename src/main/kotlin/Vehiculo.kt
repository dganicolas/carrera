/*
*  Clase Base Vehiculo

Propiedades: marca (String), modelo (String), capacidadCombustible (Float), combustibleActual (Float), kilometrosActuales (Float).


repostar(cantidad: float)-> Float: Incrementa combustibleActual hasta el máximo de capacidadCombustible si no se pasa cantidad o si cantidad es 0 o negativa. Sino, incrementa en cantidad hasta llegar a capacidadCombustible. Devuelve la cantidad repostada.
* */
abstract class Vehiculo(val nombre: String,val marca:String, var modelo:String, capacidadCombustible:Float,  var combustibleActual:Float,var kilometrosActuales:Float){

    val capacidadCombustible:Float = capacidadCombustible.redondear()
    init {
        combustibleActual = combustibleActual.redondear()
        require(capacidadCombustible < 0){"la capacidad del tanque no debe ser negativa"}
        require(combustibleActual <  0){"el nivel de combustible debe ser positivo"}
    }
    companion object{
        var KM_LITROS_GAS= 10.0f //esto es una constante en minusculas
    }
    /*obtenerInformacion() -> String, Retorna los kilómetros que el vehículo puede recorrer con el combustible actual (suponemos que cada litro da para 10 km).*/
    open fun obtenerInformacion():String = "El $modelo puede recorrer ${combustibleActual * 10} kilometros mas"
    /*calcularAutonomia() -> Float, que retorna un valor Float (Suponemos que cada litro da para 10 km.).*/
    open fun calcularAutonomia():Float = (combustibleActual * KM_LITROS_GAS)
    /*realizaViaje(distancia: Float) -> Float: Realiza un viaje hasta donde da combustibleActual. Ajusta el combustible gastado y el kilometraje realizado de acuerdo con el viaje. Devuelve la distancia restante.*/
    open fun realizaViaje(distancia : Float): Float{ //si tiene capacidad suficiente hace el viaje que le pasamos por parametro
        if (distancia < calcularAutonomia()) { //comprueba que tiene el combustible necesario para el viaje
            kilometrosActuales += distancia.redondear()
            combustibleActual -= (distancia / KM_LITROS_GAS).redondear()
            return kilometrosActuales
        }else{//el nivel de combustible es bajo y procede a viajar hasta que el combustible quede a 0
            kilometrosActuales += (combustibleActual * KM_LITROS_GAS).redondear()
            combustibleActual = 0.0F
            return kilometrosActuales

        }
    }
    /*repostar(cantidad: float)-> Float: Incrementa combustibleActual hasta el máximo de capacidadCombustible si no se pasa cantidad o si cantidad es 0 o negativa. Sino, incrementa en cantidad hasta llegar a capacidadCombustible. Devuelve la cantidad repostada.*/
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