package jp.honkot.samplecustomactionbar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    abstract val label: String

    lateinit var toolbar: Toolbar

    /**
     * nothing to do in this base activity.
     * In the activity, it has to prepare view in this method.
     */
    abstract fun setContentView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize view
        setContentView()

        // initialize toolbar
        toolbar = findViewById(R.id.toolbar)
        // remove default title text
        toolbar.title = ""
        // set toolbar as actionbar
        setSupportActionBar(toolbar)
    }
}
