package day13

import java.util.PriorityQueue

fun main() {
    breakfast()
    lunch()
}

data class Point(val x: Int, val y: Int, var cost: Int = Int.MAX_VALUE) {

    fun isWall(): Boolean {
        val n = x*x + 3*x + 2*x*y + y + y*y + SECRET
        return Integer.bitCount(n) % 2 == 1
    }

    val icon = if (this.isWall()) '#' else '.'

    // nts: since I just look up the status this only works if the table is big enough
    fun neighbors(): List<Point> {
        val neighbors = listOf(Point(x, y - 1),
            Point(x - 1, y ),
            Point(x + 1, y ),
            Point(x, y + 1))
        return neighbors.filter { lookup.getOrDefault(it, false) }
    }

    companion object {
        const val SECRET = 1358
        const val SIZE = 50

        fun printGrid(size: Int = SIZE) {
            repeat(size) { y ->
                repeat(size) { x ->
                    print(Point(x, y).icon)
                }
                print("\n")
            }
        }

        val lookup by lazy {
            (0 until SIZE).flatMap { y ->
                (0 until SIZE).map { x ->
                    val p = Point(x, y)
                    p to !p.isWall()
                }
            }.toMap()
        }
    }
}

// update 2022-12-05: complete. the only mistake was to start at 0, 0 instead of 1, 1...
fun breakfast() {
    Point.printGrid(50)
    val start = Point(1, 1, 0)
    val end = Pair(31, 39)
    val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
    val compareByCost: Comparator<Point> = compareBy { it.cost }
    val queue: PriorityQueue<Point> = PriorityQueue(compareByCost)
    queue.add(start)
    while (queue.isNotEmpty() && Pair(end.first, end.second) !in visited) {
        val next = queue.poll()
        visited.add(Pair(next.x, next.y))
        if (next.x == end.first && next.y == end.second) {
            println(next)
        }
        println(visited)
        val neighbors = next.neighbors().filterNot { Pair(it.x, it.y) in visited }
        neighbors.forEach { neighbor ->
            neighbor.cost = next.cost + 1
            queue.add(neighbor)
        }
    }
}

fun lunch() {
    Point.printGrid(50)
    val max = 51
    val start = Point(1, 1, 0)
    val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
    val compareByCost: Comparator<Point> = compareBy { it.cost }
    val queue: PriorityQueue<Point> = PriorityQueue(compareByCost)
    queue.add(start)
    while (queue.isNotEmpty()) {
        val next = queue.poll()
        if (next.cost == max) {
            println(next)
            println(visited.size)
            println(visited)
            break
        }
        visited.add(Pair(next.x, next.y))
        val neighbors = next.neighbors().filterNot { Pair(it.x, it.y) in visited }
        neighbors.forEach { neighbor ->
            neighbor.cost = next.cost + 1
            queue.add(neighbor)
        }
    }
}