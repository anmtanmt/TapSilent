package jp.anmt.silent.tapsilent;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.os.Bundle;
import android.util.Log;

public class SetActivity extends PreferenceActivity {
    private static final String TAG = "SetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingFragment())
                .commit();
    }

    public static class SettingFragment extends PreferenceFragment {

        public SettingFragment() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings);

            // プリファレンスクリックのリスナー登録
            bindPreferenceClick(findPreference("key_no_alarm"));
            bindPreferenceClick(findPreference("key_no_music"));
            bindPreferenceClick(findPreference("key_license"));
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            initView();
        }

        private void initView() {
            // 初期化
        }

    }

    private static Preference.OnPreferenceClickListener sBindPreferenceClickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference pref) {
            final Context context = pref.getContext();
            String key = pref.getKey();

            if (Param.D) Log.d(TAG, "onPreferenceClick() key:" + key);

            if (key.equals("key_license")) {
                // ライセンス表示
                Intent licenseIntent = new Intent(context, LicenseActivity.class);
                context.startActivity(licenseIntent);
            } else if (key.equals("key_no_alarm")) {
                // 記憶していたアラーム音量の消去
                PrefUtils.removePref(context, PrefUtils.KEY_VOL_ALARM);
            } else if (key.equals("key_no_music")) {
                // 記憶していた音楽再生音量の消去
                PrefUtils.removePref(context, PrefUtils.KEY_VOL_MUSIC);
            }

            return true;
        }
    };

    private static void bindPreferenceClick(Preference preference) {
        if (Param.D) Log.d(TAG, "bindPreferenceClick() title:" + preference.getTitle());

        // Set the listener to click.
        preference.setOnPreferenceClickListener(sBindPreferenceClickListener);
    }

}
