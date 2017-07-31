package com.tyut.feiyu.myapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SettingActivity extends AppCompatActivity {
    private ListView userlist;
    private UserAdapter adapter;
    private MsgSendThread msgsend;
    public static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);
            } else if (preference instanceof SwitchPreference) {
                        preference.setSummary(stringValue);
            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String account=preferences.getString("students_id","0000000000");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        userlist = (ListView)findViewById(R.id.lv_user);
        msgsend=new MsgSendThread("ContentServlet",account);
        msgsend.start();
        String recMsg = null;
        try {
            msgsend.join();
            Log.d("receive","Thread has stopped");
            recMsg=msgsend.getContent();
            Log.d("receive",recMsg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(recMsg!=null) {
            adapter = new UserAdapter(this,getListItems(recMsg));
            userlist.setAdapter(adapter);
        }
    }
    protected void onStop(){
        super.onStop();
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String name=preferences.getString("students_name","king");
        String gender=preferences.getString("students_gender","male");
        String age=preferences.getString("students_age","21");
        String major=preferences.getString("students_major","computer science");
        String account=preferences.getString("students_id","0000000000");
        String sendMsg=name+"/"+age+"/"+gender+"/"+major+"/"+account;
        Log.d("mytag",sendMsg);
        MsgSendThread msgsend=new MsgSendThread("HelloServlet",sendMsg);
        msgsend.start();
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public  static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);
            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("students_name"));
            bindPreferenceSummaryToValue(findPreference("students_gender"));
            bindPreferenceSummaryToValue(findPreference("students_age"));
            bindPreferenceSummaryToValue(findPreference("students_major"));
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class RewardScorePreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_score_system);
            setHasOptionsMenu(true);
            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("static_score"));
            bindPreferenceSummaryToValue(findPreference("score_reward"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
    private List<Map<String,String>> getListItems(String recMsg) {
        String[] inputmsg = new String[6];
        List<Map<String,String>> listItems = new ArrayList<Map<String,String>>();
        String[] pieces=recMsg.split("/");
        Log.d("tag",String.valueOf(pieces.length));
        for(String str : pieces){
            inputmsg=str.split(":");
            Map<String,String> map = new HashMap<String,String>();
            map.put("first", inputmsg[0]);
            map.put("second", inputmsg[1]);
            map.put("third", inputmsg[2]);
            map.put("content", inputmsg[3]);
            listItems.add(map);
        }
        return listItems;
    }
}
