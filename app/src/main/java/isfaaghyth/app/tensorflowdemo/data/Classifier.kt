package isfaaghyth.app.tensorflowdemo.data

import android.graphics.Bitmap

/**
 * Created by isfaaghyth on 19/01/19.
 * github: @isfaaghyth
 */
interface Classifier {
    fun recognize(bitmap: Bitmap): List<Entity>
    fun close()
}