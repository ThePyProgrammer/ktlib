package geometry

data class Line(var m: Double, var c: Double) {

    constructor(p1: Point, p2: Point) :
        this((p2.y - p1.y) / (p2.x - p1.x), p2.y - ((p2.y - p1.y) / (p2.x - p1.x)) * p2.x)

    // 0 = mx + c, x = -c/m
    fun xIntercept(): Double =// 0 = mx + c, x = -c/m
            -c / m

    fun findIntersection(l1: Line): Point {
        // m1x + c1 = m2x + c2 = y
        // (m1-m2)x = c2 - c1
        val x = (l1.c - c) / (m - l1.m)
        val y = m * x + c
        return Point(x, y)
    }

    fun isParallel(l1: Line): Boolean {
        return m == l1.m
    }

    fun isPerpendicular(l1: Line): Boolean {
        return m == -(1 / l1.m)
    }

    fun isOnLine(p: Point): Boolean {
        return p.y == m * p.x + c
    }

    override fun toString(): String {
        return "y = " + m + "x + " + c
    }
}