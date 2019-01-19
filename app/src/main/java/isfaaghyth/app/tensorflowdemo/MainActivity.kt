package isfaaghyth.app.tensorflowdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import isfaaghyth.app.tensorflowdemo.data.Classifier

class MainActivity : AppCompatActivity() {

    private lateinit var classifier: Classifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun initialize() {
        //classifier = Tens
    }

}
