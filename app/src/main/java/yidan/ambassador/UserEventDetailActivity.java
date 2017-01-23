package yidan.ambassador;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import yidan.ambassador.constant.SQLCommand;
import yidan.ambassador.util.DBOperator;

/**
 * Created by xyd on 16/4/23.
 */
public class UserEventDetailActivity extends Activity implements View.OnClickListener {
    ImageButton regBtn;
    int ambid;
    String ambidStr;
    int evtNum, evtDur, evtAmount, evtCurrent;
    String evtNumStr, evtDateStr, evtTimeStr, evtDurStr, evtTopicStr, evtDescStr, evtForStr, evtAmtStr, evtCurStr;
    TextView evtDateTxt, evtTimeTxt, evtDurTxt, evtTopicTxt, evtForTxt, evtAmtTxt, evtDescTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_event_detail_layout);

        regBtn = (ImageButton) this.findViewById(R.id.user_event_register_button);
        regBtn.setOnClickListener(this);

        evtDateTxt = (TextView) this.findViewById(R.id.user_event_date_content);
        evtTimeTxt = (TextView) this.findViewById(R.id.user_event_time_content);
        evtDurTxt = (TextView) this.findViewById(R.id.user_event_duration_content);
        evtTopicTxt = (TextView) this.findViewById(R.id.user_event_topic_content);
        evtForTxt = (TextView) this.findViewById(R.id.user_event_format_content);
        evtAmtTxt = (TextView) this.findViewById(R.id.user_event_amt_content);

        evtDescTxt = (TextView) this.findViewById(R.id.user_event_desc_content);

        Intent intent = getIntent();
        evtNum = intent.getIntExtra("EvtNum", 0);
        evtDateStr = intent.getStringExtra("EvtDate");
        evtTimeStr = intent.getStringExtra("EvtTime");
        evtDur = intent.getIntExtra("EvtDur", 0);
        evtTopicStr = intent.getStringExtra("EvtTopic");
        evtDescStr = intent.getStringExtra("EvtDesc");
        evtForStr = intent.getStringExtra("EvtFormat");
        evtAmount = intent.getIntExtra("EvtAmount", 0);
        ambid = intent.getIntExtra("AmbID",0);

        evtNumStr = Integer.toString(evtNum);
        evtDurStr = Integer.toString(evtDur);
        evtAmtStr = Integer.toString(evtAmount);
        ambidStr = Integer.toBinaryString(ambid);

        evtDateTxt.setText(evtDateStr);
        evtTimeTxt.setText(evtTimeStr);
        evtDurTxt.setText(evtDurStr);
        evtTopicTxt.setText(evtTopicStr);
        evtForTxt.setText(evtForStr);
        evtAmtTxt.setText(evtAmtStr);

        evtDescTxt.setText(evtDescStr);

        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.AMB_REGISTER_TOTAL, this.getParameter(evtNumStr));
        while (cursor.moveToNext()) {
            int count = cursor.getInt(cursor.getColumnIndex("count(Amb_ID)"));
            evtCurrent = count;
            evtCurStr = Integer.toString(evtCurrent);
        }


    }

    public String[] getParameter(String evtNum) {
        String[] args = new String[1];
        args[0] = evtNum;
        return args;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int maxNum = 0;
        int count = 0;
        String countStr;
        String maxStr;
        String date = "2016-04-26";
        int a =0;
        if (id == R.id.user_event_register_button) {

            Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.REGISTER_COUNT);
            while (cursor.moveToNext()){
                maxNum = cursor.getInt(cursor.getColumnIndex("count(*)"));
            }
            maxNum = maxNum + 1;
            maxStr = Integer.toString(maxNum);

            Cursor cursorCheck = DBOperator.getInstance().execQuery(SQLCommand.USER_EVENT_SELECT,this.getParameterEvent(ambidStr,evtNumStr));
            while (cursorCheck.moveToNext()){
                a = cursorCheck.getInt(cursorCheck.getColumnIndex("count(*)"));

                if(a == 0){

                    DBOperator.getInstance().execSQL(SQLCommand.USER_REGISTER, this.getRegParameter(ambidStr, evtNumStr, date, maxStr));
                    Toast.makeText(getApplicationContext(),"Register success!",Toast.LENGTH_SHORT).show();

                } else if (a > 0){
                    Toast.makeText(getApplicationContext(),"You have already registered for this event!",Toast.LENGTH_SHORT).show();

                }
            }

        }
    }

    public String[] getRegParameter(String ambID, String evtNum, String date, String reg_id){

        String args[] = new String[4];
        args[0]=ambID;
        args[1]=evtNum;
        args[2]=date;
        args[3]=reg_id;
        return args;

    }
    public String[] getParameterEvent(String ambID, String evtNum){
        String args[] = new String[2];
        args[0]=ambID;
        args[1]=evtNum;
        return args;
    }

}
