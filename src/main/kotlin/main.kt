import com.mongodb.client.MongoClients
import org.bson.Document
import com.google.gson.Gson
import com.mongodb.client.MongoCollection
import org.worldcubeassociation.tnoodle.scrambles.PuzzleRegistry
import org.worldcubeassociation.tnoodle.svglite.Svg
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main(args: Array<String>) {
    val mongoClient = MongoClients.create("mongodb://${args[0]}:${args[1]}/")
    val database = mongoClient.getDatabase(args[2])
    val scrambleStringCollection = database.getCollection("scrambles")
    val scrambleSvgCollection = database.getCollection("svgs")

	val date = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    val puzzles = mapOf(
        "333" to Triple(PuzzleRegistry.THREE, 5, false),
        "222" to Triple(PuzzleRegistry.TWO, 5, false),
        "444" to Triple(PuzzleRegistry.FOUR, 5, false),
        "555" to Triple(PuzzleRegistry.FIVE, 5, false),
        "3BLD" to Triple(PuzzleRegistry.THREE_NI, 5, false),
        "OH" to Triple(PuzzleRegistry.THREE, 5, false),
        "SQ1" to Triple(PuzzleRegistry.SQ1, 5, true),
        "MEGA" to Triple(PuzzleRegistry.MEGA, 5, false),
        "PYRA" to Triple(PuzzleRegistry.PYRA, 5, true),
        "CLOCK" to Triple(PuzzleRegistry.CLOCK, 5, true),
        "SKEWB" to Triple(PuzzleRegistry.SKEWB, 5, true),
        "666" to Triple(PuzzleRegistry.SIX, 3, false),
        "777" to Triple(PuzzleRegistry.SEVEN, 3, false),
        "4BLD" to Triple(PuzzleRegistry.FOUR_NI, 3, false),
    )

    puzzles.forEach { (t, u) -> generateScrambles(t, u, date, scrambleStringCollection, scrambleSvgCollection) }
}

fun generateScrambles(
    event: String,
    puzzle: Triple<PuzzleRegistry, Int, Boolean>,
    date: String,
    scrambleStringCollection: MongoCollection<Document>,
    scrambleSvgCollection: MongoCollection<Document>
) {
    val scrambles = getScrambleString(puzzle.first, puzzle.second)
    scrambleStringCollection.insertOne(Document.parse(Gson().toJson(ScrambleStringObject(scrambles, event, date))))
    if (puzzle.third) {
        val svg = getScrambleSvg(puzzle.first, scrambles)
        scrambleSvgCollection.insertOne(Document.parse(Gson().toJson(ScrambleSvgObject(svg, event, date))))
    }
}

fun parseSVG(svg: Svg): String {
    svg.setAttribute("width", "100%")
    svg.setAttribute("height", "100%")
    return svg.toString().replace(oldValue = "stroke=\"#000000\"", newValue = "stroke=\"#1E1E1E\"")
}

class ScrambleStringObject(val scrambles: Array<String>, val event: String, val date: String)

fun getScrambleString(puzzle: PuzzleRegistry, n: Int): Array<String> {
     return puzzle.scrambler.generateScrambles(n)
}

class ScrambleSvgObject(val svg: List<String>, val event: String, val date: String)

fun getScrambleSvg(puzzle: PuzzleRegistry, scrambles: Array<String>): List<String> {
    return scrambles.map { s -> parseSVG(puzzle.scrambler.drawScramble(s, puzzle.scrambler.defaultColorScheme)) }
}
