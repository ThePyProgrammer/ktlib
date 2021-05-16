package geometry

data class Point(val x: Double, val y: Double) {
    companion object {
        fun randomize(): Double {
            val MIN = -20
            val MAX = 20
            val range = MAX - MIN + 1
            return (Math.random() * range) + MIN
        }
    }

    constructor() : this(randomize(), randomize())

    override fun toString(): String = "($x, $y)"

    constructor(p: Point): this(p.x, p.y)

    fun copy(): Point = Point(this);
}