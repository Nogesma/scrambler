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
		"333" to PuzzleRegistry.THREE,
		"222" to PuzzleRegistry.TWO,
		"444" to PuzzleRegistry.FOUR,
		"555" to PuzzleRegistry.FIVE,
		"3BLD" to PuzzleRegistry.THREE_NI,
		"OH" to PuzzleRegistry.THREE,
		"SQ1" to PuzzleRegistry.SQ1,
		"MEGA" to PuzzleRegistry.MEGA,
		"PYRA" to PuzzleRegistry.PYRA,
		"CLOCK" to PuzzleRegistry.CLOCK,
		"SKEWB" to PuzzleRegistry.SKEWB,
	)

	puzzles.forEach { (t, u) -> generateScrambles(t, u, date, scrambleStringCollection, scrambleSvgCollection) }
}

fun generateScrambles(
	event: String,
	Puzzle: PuzzleRegistry,
	date: String,
	scrambleStringCollection: MongoCollection<Document>,
	scrambleSvgCollection: MongoCollection<Document>
) {
	val scrambles = getScrambleString(Puzzle, 5)
	val svg = getScrambleSvg(Puzzle, scrambles)
	scrambleStringCollection.insertOne(Document.parse(Gson().toJson(ScrambleStringObject(scrambles, event, date))))
	scrambleSvgCollection.insertOne(Document.parse(Gson().toJson(ScrambleSvgObject(svg, event, date))))
}

fun parseSVG(svg: Svg): String {
	svg.setAttribute("width", "100%")
	svg.setAttribute("height", "100%")
	return svg.toString().replace(oldValue = "stroke=\"#000000\"", newValue = "stroke=\"#1E1E1E\"")
}

class ScrambleStringObject(val scrambles: List<String>, val event: String, val date: String)

fun getScrambleString(Puzzle: PuzzleRegistry, n: Int): List<String> {
	return List(n) { Puzzle.scrambler.generateScramble() }
}

class ScrambleSvgObject(val svg: List<String>, val event: String, val date: String)

fun getScrambleSvg(Puzzle: PuzzleRegistry, scrambles: List<String>): List<String> {
	return scrambles.map { s -> parseSVG(Puzzle.scrambler.drawScramble(s, Puzzle.scrambler.defaultColorScheme)) }
}
