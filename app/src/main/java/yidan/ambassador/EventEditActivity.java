package yidan.ambassador;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import yidan.ambassador.constant.SQLCommand;
import yidan.ambassador.util.DBOperator;

/**
 * Created by xyd on 16/4/25.
 */
public class EventEditActivity extends Activity implements View.OnClickListener{

    ImageButton saveBtn;
    EditText topicEdit,amountEdit,descEdit;
    Spinner formatSpinner,durationSpinner;
    DatePicker datePicker;
    TimePicker timePicker;
    int duration,amount;
    String date,time,topic,format,desc;
    int eventNum;
    String eventNumStr;
    String sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_event_layout);

        saveBtn = (ImageButton)this.findViewById(R.id.activity_admin_event_edit_submit_button);
        saveBtn.setOnClickListener(this);

        topicEdit = (EditText)this.findViewById(R.id.activity_admin_event_edit_topic_editText);
        formatSpinner = (Spinner)this.findViewById(R.id.activity_admin_event_edit_format_spinner);
        datePicker = (DatePicker)this.findViewById(R.id.activity_admin_event_edit_datepicker);
        timePicker = (TimePicker)this.findViewById(R.id.activity_admin_event_edit_timepicker);
        durationSpinner = (Spinner)this.findViewById(R.id.activity_admin_event_edit_duration_spinner);
        amountEdit = (EditText)this.findViewById(R.id.activity_admin_event_edit_ambamount_editText);
        descEdit = (EditText)this.findViewById(R.id.activity_admin_event_edit_description_editText);


        Intent intent = getIntent();
        eventNum = intent.getIntExtra("Event_Num", 0);
        eventNumStr = Integer.toString(eventNum);

        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.EVENT_EDIT,this.getParameter(eventNumStr));
        while(cursor.moveToNext()) {
            date = cursor.getString(cursor.getColumnIndex("Event_Date"));
            time = cursor.getString(cursor.getColumnIndex("Event_Time"));
            duration = cursor.getInt(cursor.getColumnIndex("Event_Duration"));
            topic = cursor.getString(cursor.getColumnIndex("Event_Topic"));
            format = cursor.getString(cursor.getColumnIndex("Event_Format"));
            amount = cursor.getInt(cursor.getColumnIndex("Event_AmbAmount"));
            desc = cursor.getString(cursor.getColumnIndex("Event_Desc"));
        }

        topicEdit.setText(topic);
        amountEdit.setText(Integer.toString(amount));
        descEdit.setText(desc);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int newmonth = month + 1;
        int year = datePicker.getYear();
        String formatedDate = year + "-0" + newmonth + "-" + day;

        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        String formatedTime = hour + ":" + minute;

        String eventformat = formatSpinner.getSelectedItem().toString();
        String eventduration = durationSpinner.getSelectedItem().toString();
        String eventtopic = topicEdit.getText().toString();
        String eventambamount =  amountEdit.getText().toString();
        String eventdescription = descEdit.getText().toString();

        if(id == R.id.activity_admin_event_edit_submit_button){
            sql = "update Event set Event_Date = '" + formatedDate+ "', " +
                    "Event_Time = '" + formatedTime+ "'," +
                    "Event_Duration = '"+eventduration+"'," +
                    "Event_Topic = '"+eventtopic+"'," +
                    "Event_Format = '"+eventformat+"'," +
                    "Event_AmbAmount = '"+eventambamount+"'," +
                    "Event_Desc = '"+eventdescription+"' where Event_Num = "+ eventNumStr;
            DBOperator.getInstance().execSQL(sql);
            Toast.makeText(getApplicationContext(), "Save success!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(),NewEventActivity.class);
            intent.putExtra("Event_Num",0);
            this.startActivity(intent);

        }

    }

    public String[] getParameter(String num){
        String args[]= new String[1];
        args[0]=num;
        return args;
    }
}