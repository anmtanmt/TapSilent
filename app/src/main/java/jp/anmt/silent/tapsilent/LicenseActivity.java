package jp.anmt.silent.tapsilent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class LicenseActivity extends AppCompatActivity {
    private static final String TAG = "LicenseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Param.D) Log.d(TAG, "onCreate()");

        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_license);

        WebView wView = (WebView)findViewById(R.id.licenseWebView);
        wView.loadUrl("file:///android_asset/licenses.html");
    }
}
