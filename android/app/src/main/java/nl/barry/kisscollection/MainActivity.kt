package nl.barry.kisscollection

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent

/**
 * Opens Barry's Kiss collection (a claude.ai artifact) in a Chrome Custom Tab.
 * A Custom Tab shares the Chrome login, so the collection opens without signing in again,
 * which a plain WebView cannot do for claude.ai.
 */
class MainActivity : AppCompatActivity() {
    private var opened = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        opened = savedInstanceState?.getBoolean("opened") ?: false
    }

    override fun onResume() {
        super.onResume()
        // First time: open the collection. Coming back after the tab was closed: leave the app.
        if (!opened) { opened = true; openCollection() } else finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("opened", opened)
    }

    private fun openCollection() {
        val black = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(Color.BLACK)
            .setNavigationBarColor(Color.BLACK)
            .build()
        CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(black)
            .setColorScheme(CustomTabsIntent.COLOR_SCHEME_DARK)
            .setShowTitle(true)
            .setUrlBarHidingEnabled(true)
            .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
            .build()
            .launchUrl(this, Uri.parse(COLLECTION_URL))
    }

    companion object {
        const val COLLECTION_URL = "https://claude.ai/artifact/5ezkixSFRAaSSJiygRLam7"
    }
}
