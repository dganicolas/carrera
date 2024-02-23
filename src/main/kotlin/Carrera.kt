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
class Carrera (val nombreCarrera:String, val distanciaTotal: Float, var participantes: List<Vehiculo>){
    var estadoCarrera:  Boolean = true
    var historialAcciones : MutableMap<String, MutableList<String>> =  mutableMapOf()
    var posiciones: MutableMap<String, Int> = mutableMapOf()


    init {
        require(distanciaTotal >= 1000){"la carrera debe ser mayor o igual a 1000.00KM"}
    }

    data class ResultadoCarrera(
        val vehiculo: Vehiculo,
        val posicion: Int,
        val kilometraje: Float,
        val paradasRepostaje: Int,
        val historialAcciones: List<String>
    ){
    }


    companion object{
        val KILOMETROS_POR_TURNOS =20.0f
    }


fun comienzo(){
    for (coches in participantes){
        historialAcciones[coches.nombre] = emptyList<String>().toMutableList()
    }
    println("*** Gran Carrera de ${nombreCarrera}\n")
    println(".....................")
}
/*iniciarCarrera(): Inicia la carrera, estableciendo estadoCarrera a true y comenzando el ciclo de iteraciones donde los vehículos avanzan y realizan acciones.*/
fun iniciarCarrera(){
    comienzo()
    this.estadoCarrera = true
    participantes.forEach{ it.kilometrosActuales = 0.0f}
    do {
        var vehiculo= participantes.random()
        avanzarVehiculo(vehiculo)
        actualizarPosiciones()
        determinarGanador()
    }while(estadoCarrera != false)

    obtenerResultados()
}

/*avanzarVehiculo(vehiculo: Vehiculo): Identificado el vehículo, le hace avanzar una distancia aleatoria entre 10 y 200 km. Si el vehículo necesita repostar, se llama al método repostarVehiculo() antes de que pueda continuar. Este método llama a realizar filigranas.*/
fun avanzarVehiculo(vehiculo: Vehiculo){
    var kilometrosAAvanzar = Random(1000).nextInt(20000).toFloat()/100
    registrarAccion(vehiculo.nombre, "Inicia viaje: A recorrer ${kilometrosAAvanzar.redondear()} (${vehiculo.kilometrosActuales} kms y ${vehiculo.combustibleActual} L actuales).")
    do {
        if (kilometrosAAvanzar > KILOMETROS_POR_TURNOS){
            kilometrosAAvanzar -= KILOMETROS_POR_TURNOS
            if(vehiculo.calcularAutonomia() > KILOMETROS_POR_TURNOS){
                vehiculo.realizaViaje(KILOMETROS_POR_TURNOS)
            }else{
                repostarVehiculo(vehiculo,0.0f)
                vehiculo.realizaViaje(KILOMETROS_POR_TURNOS)
            }
            registrarAccion(vehiculo.nombre, "Avance tramo: Recorrido ${vehiculo.kilometrosActuales} kms.")
            realizarFiligrana(vehiculo)

        }else{

            if(vehiculo.calcularAutonomia() > kilometrosAAvanzar){
                vehiculo.realizaViaje(kilometrosAAvanzar)
            }else{
                repostarVehiculo(vehiculo,0.0f)
                vehiculo.realizaViaje(kilometrosAAvanzar)
            }
            kilometrosAAvanzar -= kilometrosAAvanzar
            registrarAccion(vehiculo.nombre, "Avance tramo: Recorrido ${vehiculo.kilometrosActuales} kms.")
        }
    }while (kilometrosAAvanzar != 0.0f)
    registrarAccion(vehiculo.nombre, "Finaliza viaje: Total Recorrido ${vehiculo.kilometrosActuales}")
}

/*repostarVehiculo(vehiculo: Vehiculo, cantidad: Float): Reposta el vehículo seleccionado, incrementando su combustibleActual y registrando la acción en historialAcciones.*/
fun repostarVehiculo(vehiculo: Vehiculo, cantidad: Float = 0.0f){
    vehiculo.paradas ++
    registrarAccion(vehiculo.nombre, "Respotaje tramo: ${vehiculo.repostar(cantidad)} L.")
}

/*realizarFiligrana(vehiculo: Vehiculo): Determina  aleatoriamente si un vehículo realiza una filigrana (derrape o caballito) y registra la acción.*/
fun realizarFiligrana(vehiculo: Vehiculo){
        val ran= mutableListOf(1,2).random()
        for( i in 1..ran){//lo quiero poner ramdon entre 1 o 2 ??
        when(vehiculo){
        is Automovil -> registrarAccion(vehiculo.nombre, "Derrape: Combustible restante ${vehiculo.realizaDerrape()}  L.")
        is Motocicleta -> registrarAccion(vehiculo.nombre, "Caballito: Combustible restante ${vehiculo.realizaCaballito()}  L.")
    }}
}
/*registrarAccion(vehiculo: String, accion: String): Añade una acción al historialAcciones del vehículo especificado.*/
    fun registrarAccion(vehiculo: String, accion: String){
     historialAcciones[vehiculo]?.add(accion)
    }
/*actualizarPosiciones(): Actualiza posiciones con los kilómetros recorridos por cada vehículo después de cada iteración, manteniendo un seguimiento de la competencia.*/
    fun actualizarPosiciones(){
    participantes = participantes.sortedByDescending { it.kilometrosActuales }
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
    println("¡Carrera finalizada!\n")
    println("¡¡¡ENHORABUENA ${participantes.maxBy { it.kilometrosActuales }}!!!\n")
    println("* Clasificación:")
    for (coches in participantes){
        println("$puesto -> ${coches.nombre} (${coches.kilometrosActuales})")
        puesto++
    }
    puesto = 1
    var listafinal = emptyList<ResultadoCarrera>().toMutableList()
    for (i in participantes){

        listafinal.add(ResultadoCarrera(i,puesto,i.kilometrosActuales, i.paradas, historialAcciones[i.nombre] ?: emptyList()))
        puesto++
    }
    listafinal.forEach{ println(it)}

    println("* Historial detallado:")

    for (coche in listafinal){

        println("${coche.posicion} -> ${coche.vehiculo}")

        println("total de paradas: ${coche.paradasRepostaje}")


        for (linea in coche.historialAcciones){
                println(linea)

            }
        }



}
}