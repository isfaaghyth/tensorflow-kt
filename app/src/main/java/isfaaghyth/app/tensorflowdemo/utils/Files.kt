package isfaaghyth.app.tensorflowdemo.utils

import android.content.res.AssetManager
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

/**
 * Created by isfaaghyth on 19/01/19.
 * github: @isfaaghyth
 */
object Files {

    fun model(assetManager: AssetManager, path: String): MappedByteBuffer {
        val file = assetManager.openFd(path)
        val inputStream = FileInputStream(file.fileDescriptor)
        val fileChannel = inputStream.channel

        //offset
        val start = file.startOffset
        val length = file.declaredLength

        return fileChannel.map(FileChannel.MapMode.READ_ONLY, start, length)
    }



}