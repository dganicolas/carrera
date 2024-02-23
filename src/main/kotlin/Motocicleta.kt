import kotlin.math.absoluteValue

/*Propiedad específica: cilindrada (Int) (no podrá ser inferior a 125 ni superior a 1000 cc).
Comportamiento Especializado:
calcularAutonomia() -> Float: Modifica el cálculo de autonomía asumiendo un rendimiento de 20 km por litro. También afectará la cilindrada de la misma... si es 1000 no afectará, pero el resto de cilindradas menores, restará su valor dividido por 1000. Es decir, una motocicleta de 500 cc podrá recorrer 19.5 km por litro, mientras que una de 125 solo 19.125 y una moto de 1000cc recorrerá el máximo... 20 km por litro.
realizaViaje(distancia: Float) -> Float: Ajusta el cálculo de combustible necesario para viajes basándose en su autonomía específica.
Comportamiento Adicional:
realizaCaballito()-> Float: realiza una gasto adicional en el combustible, retornando el combustible restante. El gasto equivale a haber realizado 6,5 kilómetros.*/
class Motocicleta   (nombre:String,marca:String, modelo:String,capacidadCombustible:Float,combustibleActual:Float,kilometrosActuales:Float, var cilindrada: Int) : Vehiculo(nombre, marca, modelo, capacidadCombustible,combustibleActual,kilometrosActuales)  {
    init {
        require(cilindrada > 125 || cilindrada < 1000){"ERROR las cilindradas deben estar enter 125 a 10000 cc"}
    }


    companion object{
        val KM_POR_LITROS_MOTOS = 20.0f
        val GASTO_KM_CABALLITO = 6.5f
    }


    /*calcularAutonomia() -> Float: Modifica el cálculo de autonomía asumiendo un rendimiento de 20 km por litro. También afectará la cilindrada de la misma... si es 1000 no afectará, pero el resto de cilindradas menores, restará su valor dividido por 1000. Es decir, una motocicleta de 500 cc podrá recorrer 19.5 km por litro, mientras que una de 125 solo 19.125 y una moto de 1000cc recorrerá el máximo... 20 km por litro.*/
    override fun calcularAutonomia() = (combustibleActual * (KM_POR_LITROS_MOTOS - ((1000 - cilindrada)/ 1000.0f))).redondear()


    fun calcular_kilometros_con_un_litro(): Float = (KM_POR_LITROS_MOTOS - ((1000 - cilindrada)/ 1000.0f)).redondear()


    override fun realizaViaje(distancia: Float): Float {
        if (distancia < calcularAutonomia()) { //comprueba que tiene el combustible necesario para el viaje
            kilometrosActuales += distancia.redondear()
            combustibleActual -= (distancia / calcular_kilometros_con_un_litro()).redondear()
            combustibleActual = combustibleActual.redondear()
            return kilometrosActuales
        }else{//el nivel de combustible es bajo y procede a viajar hasta que el combustible quede a 0
            kilometrosActuales += calcularAutonomia()
            combustibleActual = 0.0F
            return kilometrosActuales

        }
    }


    open fun realizaCaballito(): Float{
        combustibleActual -= GASTO_KM_CABALLITO / calcular_kilometros_con_un_litro()
            combustibleActual = combustibleActual.redondear()
        return combustibleActual

    }

}