package studio.crazybt.blockingsms;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import studio.crazybt.blockingsms.adapters.SmsBlockedListAdapter;
import studio.crazybt.blockingsms.models.SmsBlocked;
import studio.crazybt.blockingsms.utils.DatabaseHelper;
import studio.crazybt.blockingsms.utils.SharedPref;

public class MainActivity extends AppCompatActivity {

    public static final String FIRST_TIME_USING = "first_time_using";
    private RecyclerView rvSmsBlocked;
    private LinearLayoutManager llmSmsBlocked;
    private SmsBlockedListAdapter sblaAdapter;
    private List<SmsBlocked> smsBlockedList;
    private static MainActivity instance;

    public static MainActivity getInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPref.getInstance(this).getBoolean(FIRST_TIME_USING, true)) {
            new AlertDialog.Builder(this)
                    .setTitle("Welcome")
                    .setMessage("Welcome to the SMS Blocked all of your messages!")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPref.getInstance(getBaseContext()).putBoolean(FIRST_TIME_USING, false);
                            dialog.dismiss();
                        }
                    }).show();
        }
        smsBlockedList = new ArrayList<>();
        rvSmsBlocked = (RecyclerView) findViewById(R.id.rvSmsBlocked);
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        this.listenSmsBlocked(databaseHelper.getAllSms());

    }

    @Override
    protected void onStart() {
        super.onStart();
        instance = this;
    }

    private void initSmsBlockedList(){
        llmSmsBlocked = new LinearLayoutManager(getBaseContext());
        rvSmsBlocked.setLayoutManager(llmSmsBlocked);
        sblaAdapter = new SmsBlockedListAdapter(smsBlockedList);
        rvSmsBlocked.setAdapter(sblaAdapter);
    }

    public void listenSmsBlocked(List<SmsBlocked> smsBlockedList){
        if(this.smsBlockedList.size() == 0){
            this.smsBlockedList.addAll(smsBlockedList);
            this.initSmsBlockedList();
        }else{
            this.smsBlockedList.addAll(smsBlockedList);
            sblaAdapter.notifyDataSetChanged();
        }
    }
}
