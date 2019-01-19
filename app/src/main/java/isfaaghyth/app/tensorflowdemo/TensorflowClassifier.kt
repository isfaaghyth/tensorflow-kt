package isfaaghyth.app.tensorflowdemo

import android.content.res.AssetManager
import android.graphics.Bitmap
import isfaaghyth.app.tensorflowdemo.data.Classifier
import isfaaghyth.app.tensorflowdemo.data.Entity
import isfaaghyth.app.tensorflowdemo.utils.BitmapConverter
import isfaaghyth.app.tensorflowdemo.utils.Files
import org.tensorflow.lite.Interpreter
import java.lang.Float
import java.util.*
import kotlin.experimental.and


/**
 * Created by isfaaghyth on 19/01/19.
 * github: @isfaaghyth
 */
class TensorflowClassifier(
    assetManager: AssetManager,
    modelPath: String,
    labelPath: String,
    private val inputSize: Int): Classifier {

    private val interpreter: Interpreter = Interpreter(Files.model(assetManager, modelPath))
    private var labels: List<String> = Files.label(assetManager, labelPath)

    private val MAX_RESULTS = 3
    private val THRESHOLD = 0.1f

    override fun recognize(bitmap: Bitmap): List<Entity> {
        val bmp = BitmapConverter.bitmapToTypeBuffer(bitmap, inputSize)
        val result = Array(1) {
            ByteArray(labels.size)
        }
        interpreter.run(bmp, result)
        return sortPredicted(result)
    }

    override fun close() {
        interpreter.close()
    }

    private fun sortPredicted(labels: Array<ByteArray>): List<Entity> {
        val priorityQueue = PriorityQueue(
            MAX_RESULTS,
            Comparator<Entity> { lhs, rhs ->
                Float.compare(rhs.confidence, lhs.confidence)
            })

        for (i in 0 until this.labels.size) {
            val confidence = (labels[0][i] and 0xff.toByte()) / 255.0f
            if (confidence > THRESHOLD) {
                priorityQueue.add(
                    Entity(
                        "" + i,
                        if (this.labels.size > i) this.labels[i] else "unknown",
                        confidence
                    )
                )
            }
        }

        val entities = arrayListOf<Entity>()
        val recognitionsSize = Math.min(priorityQueue.size, MAX_RESULTS)
        for (i in 0 until recognitionsSize) {
            entities.add(priorityQueue.poll())
        }

        return entities
    }

}