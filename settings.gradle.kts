import java.io.BufferedReader
import java.io.InputStreamReader

rootProject.name = "yemen-services"

println("=== GRADLE CONFIGURATION INITIATED ===")

fun runCmd(vararg args: String): String {
    return try {
        val process = ProcessBuilder(*args).redirectErrorStream(true).start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = StringBuilder()
        var line: String? = reader.readLine()
        while (line != null) {
            output.append(line).append("\n")
            line = reader.readLine()
        }
        process.waitFor()
        output.toString()
    } catch (e: Exception) {
        "ERROR: ${e.message}"
    }
}

println("All files in /workspace:\n" + runCmd("ls", "-la", "/workspace"))

println("=== GRADLE CONFIGURATION ENDED ===")
