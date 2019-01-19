package isfaaghyth.app.tensorflowdemo

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import isfaaghyth.app.tensorflowdemo.data.Classifier
import java.util.concurrent.Executors
import android.util.Log



class MainActivity : AppCompatActivity() {

    private val INPUT_SIZE = 224
    private lateinit var classifier: Classifier

    private val MODEL = "mobilenet_quant_v1_224.tflite"
    private val LABEL = "labels.txt"

    private lateinit var imgObject: ImageView
    private lateinit var edtUrl: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgObject = findViewById(R.id.img_object)
        edtUrl = findViewById(R.id.edt_url)

        initialize()

        findViewById<Button>(R.id.btn_predict).setOnClickListener {
            val url = edtUrl.text.toString()
            Glide.with(this)
                .load(url)
                .asBitmap()
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                        val bitmap = resource.let {
                            Bitmap.createScaledBitmap(it!!, INPUT_SIZE, INPUT_SIZE, false)
                        }
                        imgObject.setImageBitmap(bitmap)
                        val result = classifier.recognize(bitmap)
                        Log.d("TAG", result[0].title)
                    }
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Executors.newSingleThreadExecutor().execute{
            classifier.close()
        }
    }

    fun initialize() {
        Executors.newSingleThreadExecutor().execute{
            classifier = TensorflowClassifier(assets, MODEL, LABEL, INPUT_SIZE)
        }
    }

}
