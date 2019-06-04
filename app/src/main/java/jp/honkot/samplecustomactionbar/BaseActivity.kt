package jp.honkot.samplecustomactionbar

import android.databinding.DataBindingUtil
import android.databinding.ObservableBoolean
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import jp.honkot.samplecustomactionbar.databinding.AppBarMainBinding
import kotlinx.android.synthetic.main.app_bar_main.*

abstract class BaseActivity : AppCompatActivity() {

    abstract val label: String

    open val useSearchView: Boolean = false

    private val searchMode = ObservableBoolean(false)

    private val searchTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            // nothing to do.
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // nothing to do.
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onChangedSearchText(s.toString())
        }
    }

    private var savedSearchText: String = ""

    lateinit var toolbar: Toolbar

    val searchInputText: String get() {
        return findViewById<EditText>(R.id.searchAreaOnToolbar).text.toString()
    }

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
            findViewById<TextView>(R.id.titleOnToolbar)?.text = label

            // set toolbar as actionbar
            setSupportActionBar(toolbar)

            if (useSearchView) {
                val binding = DataBindingUtil.bind<AppBarMainBinding>(actionBarView)
                binding?.searchMode = searchMode
                binding?.searchAreaOnToolbar?.addTextChangedListener(searchTextWatcher)
            }
        }
    }

    open fun onChangedSearchMode(isSearchMode: Boolean) {
        // nothing to do.
    }

    fun switchSearchMode() {
        // mode changed
        val turnOn = !searchMode.get()
        searchMode.set(turnOn)

        // maintain search view
        val searchView = findViewById<EditText>(R.id.searchAreaOnToolbar)
        if (turnOn) {
            // restore input text
            searchView.setText(savedSearchText)
        } else {
            // save input text and reset input text
            savedSearchText = searchView.text.toString()
            searchView.setText("")
        }
        onChangedSearchMode(turnOn)
    }

    /**
     * When tapped search clear icon.
     * Its always in search mode.
     */
    open fun onClickedSearchClear(view: View) {
        switchSearchMode()
    }

    open fun onChangedSearchText(text: String) {
        // nothing to do.
    }

    override fun onBackPressed() {
        if (searchMode.get()) {
            switchSearchMode()
        } else {
            super.onBackPressed()
        }
    }
}
