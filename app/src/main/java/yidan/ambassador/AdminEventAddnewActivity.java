package yidan.ambassador;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import yidan.ambassador.constant.SQLCommand;
import yidan.ambassador.util.DBOperator;

/**
 * Created by xyd on 16/4/25.
 */
public class AdminEventAddnewActivity extends Activity implements View.OnClickListener {

    EditText eventtopicEdit, eventdescEdit, eventambamountEdit;
    Spinner eventformatSpinner, eventdurationSpinner;
    DatePicker datePicker;
    TimePicker timePicker;
    Button submitBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event_addnew);

        eventtopicEdit = (EditText)this.findViewById(R.id.activity_admin_event_addnew_topic_editText);
        eventdescEdit = (EditText)this.findViewById(R.id.activity_admin_event_addnew_description_editText);
        eventambamountEdit = (EditText)this.findViewById(R.id.activity_admin_event_addnew_ambamount_editText);
        eventformatSpinner = (Spinner)this.findViewById(R.id.activity_admin_event_addnew_format_spinner);
        eventdurationSpinner = (Spinner)this.findViewById(R.id.activity_admin_event_addnew_duration_spinner);
        datePicker = (DatePicker)this.findViewById(R.id.activity_admin_event_addnew_datepicker);
        timePicker = (TimePicker)this.findViewById(R.id.activity_admin_event_addnew_timepicker);

        submitBtn = (Button) this.findViewById(R.id.activity_admin_event_addnew_submit_button);
        submitBtn.setOnClickListener(this);

    }


    public void onClick(View v) {
        int id=v.getId();
        String eventnum = null;
        String eventtopic = eventtopicEdit.getText().toString();
        String eventformat = eventformatSpinner.getSelectedItem().toString();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int newmonth = month + 1;
        int year = datePicker.getYear();
        String formatedDate = year + "-0" + newmonth + "-" + day;
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        String formatedTime = hour + ":" + minute;
        String eventduration = eventdurationSpinner.getSelectedItem().toString();
        String eventambamount =  eventambamountEdit.getText().toString();
        String eventdescription = eventdescEdit.getText().toString();

        if (id==R.id.activity_admin_event_addnew_submit_button) {
            if (eventtopic.matches("") || eventambamount.matches("") || eventdescription.matches("")) {
                Toast.makeText(getApplicationContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
                return;
            }else {
                DBOperator.getInstance().execSQL(SQLCommand.ADMIN_ADDNEWEVENT, this.getParameters(eventnum, formatedDate, formatedTime, eventduration, eventtopic, eventdescription, eventformat, eventambamount, eventnum));
                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,NewEventActivity.class);
                this.startActivity(intent);
            }
        }
    }
    public String[] getParameters(String eventnum, String date, String time, String duration, String topic, String description, String format, String ambamount,String event_id){
        String args[] = new String[9];
        args[0] = eventnum;
        args[1] = date;
        args[2] = time;
        args[3] = duration;
        args[4] = topic;
        args[5] = description;
        args[6] = format;
        args[7] = ambamount;
        args[8] = event_id;
        return args;
    }

}

