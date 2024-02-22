import kotlin.random.Random

/*Propiedades:


Métodos:

iniciarCarrera(): Inicia la carrera, estableciendo estadoCarrera a true y comenzando el ciclo de iteraciones donde los vehículos avanzan y realizan acciones.

avanzarVehiculo(vehiculo: Vehiculo): Identificado el vehículo, le hace avanzar una distancia aleatoria entre 10 y 200 km. Si el vehículo necesita repostar, se llama al método repostarVehiculo() antes de que pueda continuar. Este método llama a realizar filigranas.

repostarVehiculo(vehiculo: Vehiculo, cantidad: Float): Reposta el vehículo seleccionado, incrementando su combustibleActual y registrando la acción en historialAcciones.

realizarFiligrana(vehiculo: Vehiculo): Determina aleatoriamente si un vehículo realiza una filigrana (derrape o caballito) y registra la acción.

actualizarPosiciones(): Actualiza posiciones con los kilómetros recorridos por cada vehículo después de cada iteración, manteniendo un seguimiento de la competencia.

determinarGanador(): Revisa posiciones para identificar al vehículo (o vehículos) que haya alcanzado o superado la distanciaTotal, estableciendo el estado de la carrera a finalizado y determinando el ganador.

obtenerResultados(): Devuelve una clasificación final de los vehículos, cada elemento tendrá el nombre del vehiculo, posición ocupada, la distancia total recorrida, el número de paradas para repostar y el historial de acciones. La collección estará ordenada por la posición ocupada.*/

/*nombreCarrera: String - El nombre de la carrera para identificación.
distanciaTotal: Float - La distancia total que los vehículos deben recorrer para completar la carrera.
participantes: List<Vehiculo> - Una lista que contiene todos los vehículos participantes en la carrera.
estadoCarrera: Boolean - Un indicador de si la carrera está en curso o ha finalizado.
historialAcciones: MutableMap<String, MutableList<String>> - Un mapa para registrar el historial de acciones de cada vehículo. La clave es el nombre del vehículo y el valor es una lista de strings describiendo sus acciones.
posiciones: MutableList<Pair<String, Int>> o MutableMap<String, Int> - Una lista o diccionario para mantener un registro de la posición y los kilómetros recorridos por cada vehículo. Cada elemento es un par donde el primer valor es el nombre del vehículo y el segundo su kilometraje acumulado.*/
class Carrera (val nombreCarrera:String, val distanciaTotal: Float, val participantes: List<Vehiculo>, var estadoCarrera:  Boolean, var historialAcciones : MutableMap<String, MutableList<String>>, var posiciones: MutableMap<String, Int>){

    init {
        require(distanciaTotal > 1000){"la carrera debe ser mayor o igual a 1000.00KM"}
    }

    data class ResultadoCarrera(
        val vehiculo: Vehiculo,
        val posicion: Int,
        val kilometraje: Float,
        val paradasRepostaje: Int,
        val historialAcciones: List<String>
    ){
        override fun toString(): String {
            return "hola"
        }


        fun registrarcombustible(combustible:Float){
            historialAcciones.addLast("")
        }
    }


    companion object{
        val KILOMETROS_POR_TURNOS =20.0f
    }


fun comienzo(){
    println("*** Gran Carrera de ${nombreCarrera}\n")
    println(message = 8.toString())
}
/*iniciarCarrera(): Inicia la carrera, estableciendo estadoCarrera a true y comenzando el ciclo de iteraciones donde los vehículos avanzan y realizan acciones.*/
fun iniciarCarrera(){
    comienzo()
    this.estadoCarrera = true
    participantes.forEach{ it.kilometrosActuales = 0.0f}
    actualizarPosiciones()
    do {
        var vehiculo= participantes[Random(0).nextInt(participantes.size)]
        avanzarVehiculo(vehiculo)
        determinarGanador()
    }while(estadoCarrera != false)
    actualizarPosiciones()
    obtenerResultados()
}

/*avanzarVehiculo(vehiculo: Vehiculo): Identificado el vehículo, le hace avanzar una distancia aleatoria entre 10 y 200 km. Si el vehículo necesita repostar, se llama al método repostarVehiculo() antes de que pueda continuar. Este método llama a realizar filigranas.*/
fun avanzarVehiculo(vehiculo: Vehiculo){
    var kilometrosAAvanzar = Random(1000).nextInt(20000).toFloat()/100
    iniciarCarrera()
    do {
        if (kilometrosAAvanzar > KILOMETROS_POR_TURNOS){
            kilometrosAAvanzar -= KILOMETROS_POR_TURNOS
            if(vehiculo.calcularAutonomia() > KILOMETROS_POR_TURNOS){
                vehiculo.realizaViaje(KILOMETROS_POR_TURNOS)
            }else{
                repostarVehiculo(vehiculo,0.0f)
                vehiculo.realizaViaje(KILOMETROS_POR_TURNOS)
            }
            realizarFiligrana(vehiculo)

        }else{
            if(vehiculo.calcularAutonomia() > kilometrosAAvanzar){
                vehiculo.realizaViaje(kilometrosAAvanzar)
            }else{
                repostarVehiculo(vehiculo,0.0f)
                vehiculo.realizaViaje(kilometrosAAvanzar)
            }
        }
    }while (kilometrosAAvanzar != 0.0f)

}

/*repostarVehiculo(vehiculo: Vehiculo, cantidad: Float): Reposta el vehículo seleccionado, incrementando su combustibleActual y registrando la acción en historialAcciones.*/
fun repostarVehiculo(vehiculo: Vehiculo, cantidad: Float = 0.0f){
    historialAcciones.add((vehiculo.repostar(cantidad)).toString())
}

/*realizarFiligrana(vehiculo: Vehiculo): Determina aleatoriamente si un vehículo realiza una filigrana (derrape o caballito) y registra la acción.*/
fun realizarFiligrana(vehiculo: Vehiculo){
    when(vehiculo){
        is Automovil -> historialAcciones.add(vehiculo.realizaDerrape().toString())
        is Motocicleta -> historialAcciones.add(vehiculo.realizaCaballito().toString())
    }
}

/*actualizarPosiciones(): Actualiza posiciones con los kilómetros recorridos por cada vehículo después de cada iteración, manteniendo un seguimiento de la competencia.*/
fun actualizarPosiciones(){
    participantes.forEach{ posiciones[it.nombre] = it.kilometrosActuales.toInt()}
}

/*determinarGanador(): Revisa posiciones para identificar al vehículo (o vehículos) que haya alcanzado o superado la distanciaTotal, estableciendo el estado de la carrera a finalizado y determinando el ganador.*/
fun determinarGanador(){
    for (coches in posiciones){
        if (coches.value >= distanciaTotal){
            estadoCarrera = false
        }
    }
}

/*obtenerResultados(): Devuelve una clasificación final de los vehículos, cada elemento tendrá el nombre del vehiculo, posición ocupada, la distancia total recorrida, el número de paradas para repostar y el historial de acciones. La collección estará ordenada por la posición ocupada.*/
fun obtenerResultados(){
    var puesto=1
    for (coches in posiciones){
        println("el $puesto puesto: ${coches.key} con ${coches.value} KM")
    }
}
}