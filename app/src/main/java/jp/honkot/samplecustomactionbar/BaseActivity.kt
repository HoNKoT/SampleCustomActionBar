package jp.honkot.samplecustomactionbar

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableBoolean
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import jp.honkot.samplecustomactionbar.databinding.AppBarMainBinding
import android.view.inputmethod.InputMethodManager
import android.view.View.OnFocusChangeListener




abstract class BaseActivity : AppCompatActivity() {

    abstract val label: String

    open val useSearchView: Boolean = false

    private val searchMode = ObservableBoolean(false)

    val isSearchMode: Boolean get() = searchMode.get()

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

        // If it has actionbar, set up toolbar contents
        findViewById<View>(R.id.actionBar)?.let { actionBarView ->
            // initialize toolbar
            toolbar = findViewById(R.id.toolbar)
            // remove default and set title
            toolbar.title = ""
            findViewById<TextView>(R.id.titleOnToolbar).text = label
            // set toolbar as actionbar
            setSupportActionBar(toolbar)

            if (useSearchView) {
                val binding = DataBindingUtil.bind<AppBarMainBinding>(actionBarView)
                binding?.searchMode = searchMode
            }
        }
    }

    open fun onChangedSearchMode(isSearchMode: Boolean) {
        // nothing to do.
    }

    fun switchSearchMode() {
        val turnOn = !searchMode.get()
        searchMode.set(turnOn)
        onChangedSearchMode(isSearchMode)
    }

    override fun onBackPressed() {
        if (searchMode.get()) {
            switchSearchMode()
        } else {
            super.onBackPressed()
        }
    }
}
