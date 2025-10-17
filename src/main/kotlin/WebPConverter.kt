import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.webp.WebpWriter
import com.sksamuel.scrimage.nio.PngWriter
import java.io.File
import java.io.IOException

object WebPConverter {
    @JvmStatic
    fun main(args: Array<String>) {
        // Example usage
        try {
            // Convert with default quality (80)
            convertToWebP("input.png", "output.webp", quality = 20)
            convertToPNG("input.png", "output.png", 9)
        } catch (e: Exception) {
            System.err.println("Conversion failed: ${e.message}")
            e.printStackTrace()
        }
    }

    /**
     * Convert PNG or JPG to WebP with default quality (80)
     */
    @JvmOverloads
    @Throws(IOException::class)
    fun convertToWebP(inputPath: String, outputPath: String, quality: Int) {
        require(quality in 0..100) { "Quality must be between 0 and 100" }

        val inputFile = File(inputPath)
        if (!inputFile.exists()) {
            throw IOException("Input file not found: $inputPath")
        }

        // Load image
        val image = ImmutableImage.loader().fromFile(inputFile)

        // Create WebP writer with specified quality
        val writer = WebpWriter.DEFAULT.withQ(quality)

        // Write WebP file
        image.output(writer, File(outputPath))

        val inputSize = inputFile.length()
        val outputSize = File(outputPath).length()
        val compression = ((1 - outputSize.toDouble() / inputSize) * 100).toInt()

        println("✓ Converted: $inputPath -> $outputPath")
        println("  Size: ${inputSize / 1024}KB -> ${outputSize / 1024}KB (${compression}% reduction)")
    }

    /**
     * Convert to compressed PNG
     * @param compressionLevel 0-9 (0 = no compression, 9 = max compression)
     */
    @JvmOverloads
    @Throws(IOException::class)
    fun convertToPNG(inputPath: String, outputPath: String, compressionLevel: Int) {
        require(compressionLevel in 0..9) { "Compression level must be between 0 and 9" }

        val inputFile = File(inputPath)
        if (!inputFile.exists()) {
            throw IOException("Input file not found: $inputPath")
        }

        // Load image
        val image = ImmutableImage.loader().fromFile(inputFile)

        // Create PNG writer with compression
        val writer = PngWriter.MaxCompression.withCompression(compressionLevel)

        // Write PNG file
        image.output(writer, File(outputPath))

        printConversionStats(inputFile, File(outputPath))
    }

    private fun printConversionStats(inputFile: File, outputFile: File) {
        val inputSize = inputFile.length()
        val outputSize = outputFile.length()
        val compression = ((1 - outputSize.toDouble() / inputSize) * 100).toInt()

        println("✓ Converted: ${inputFile.name} -> ${outputFile.name}")
        println("  Size: ${inputSize / 1024}KB -> ${outputSize / 1024}KB (${compression}% reduction)")
    }
}