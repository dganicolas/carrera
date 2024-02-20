import kotlin.math.roundToInt
fun Float.redondear(): Float{//me han dicho que el round to int no se puede usar XD
    return (((this *100).roundToInt()).toFloat()/100)

}
fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}