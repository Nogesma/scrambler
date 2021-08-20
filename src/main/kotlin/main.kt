import com.mongodb.client.MongoClients
import org.bson.Document
import com.google.gson.Gson
import com.mongodb.client.MongoCollection
import org.worldcubeassociation.tnoodle.scrambles.PuzzleRegistry
import org.worldcubeassociation.tnoodle.svglite.Svg
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main(args:Array<String>) {
    val mongoClient = MongoClients.create("mongodb://${args[0]}:${args[1]}/")
    val database = mongoClient.getDatabase(args[2])
    val collection = database.getCollection("scrambles")

    val date = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    generateScrambles(PuzzleRegistry.THREE, "333", date, collection)
    generateScrambles(PuzzleRegistry.TWO, "222", date, collection)
    generateScrambles(PuzzleRegistry.FOUR, "444", date, collection)
    generateScrambles(PuzzleRegistry.FIVE, "555", date, collection)
    generateScrambles(PuzzleRegistry.THREE_NI, "3BLD", date, collection)
    generateScrambles(PuzzleRegistry.THREE, "OH", date, collection)
    generateScrambles(PuzzleRegistry.SQ1, "SQ1", date, collection)
    generateScrambles(PuzzleRegistry.MEGA, "MEGA", date, collection)
    generateScrambles(PuzzleRegistry.PYRA, "PYRA", date, collection)
    generateScrambles(PuzzleRegistry.CLOCK, "CLOCK", date, collection)
    generateScrambles(PuzzleRegistry.SKEWB, "SKEWB", date, collection)
}

fun generateScrambles(Puzzle: PuzzleRegistry, event: String, date: String, collection: MongoCollection<Document>) {
    val scrambles = getScrambles(Puzzle, 5)
    collection.insertOne(Document.parse(Gson().toJson(ScrambleObject(scrambles, event, date))))
}

fun parseSVG(svg: Svg): String {
    svg.setAttribute("width", "100%")
    svg.setAttribute("height", "100%")
    return svg.toString().replace(oldValue = "stroke=\"#000000\"", newValue = "stroke=\"#1E1E1E\"")
}

class Scramble(Puzzle: PuzzleRegistry) {
    private val scrambleString: String = Puzzle.scrambler.generateScramble()
    private val svg: String

    init {
        svg = parseSVG(Puzzle.scrambler.drawScramble(scrambleString, Puzzle.scrambler.defaultColorScheme))
    }
}

class ScrambleObject(val scrambles: Array<Scramble>, val event: String, val date: String)

fun getScrambles(Puzzle: PuzzleRegistry, n: Int): Array<Scramble> {
    return Array(n) { Scramble(Puzzle) }
}
