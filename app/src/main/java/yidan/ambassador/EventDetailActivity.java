package yidan.ambassador;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import yidan.ambassador.constant.SQLCommand;
import yidan.ambassador.util.DBOperator;

/**
 * Created by xyd on 16/4/21.
 */
public class EventDetailActivity extends AppCompatActivity implements View.OnClickListener{

        public static String sql;
        ImageButton deleteBtn,readBtn,editBtn;
        TextView dateTxt,timetxt, durationTxt, topicTxt, formatTxt,descTxt,amountTxt;
        int eventNum,eventDuration,eventAmount;
        String eventDate,eventTime,eventTopic,eventDesc,eventFormat;
        Intent intent1;


        @Override
        protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_event_detail_layout);

                deleteBtn = (ImageButton)this.findViewById(R.id.event_delete_button);
                deleteBtn.setOnClickListener(this);
                readBtn = (ImageButton)this.findViewById(R.id.event_readmore_button);
                readBtn.setOnClickListener(this);
                editBtn = (ImageButton)this.findViewById(R.id.event_edit_button);
                editBtn.setOnClickListener(this);

                dateTxt = (TextView)this.findViewById(R.id.event_date_content);
                timetxt = (TextView)this.findViewById(R.id.event_time_content);
                topicTxt = (TextView)this.findViewById(R.id.event_topic_content);
                durationTxt = (TextView)this.findViewById(R.id.event_duration_content);
                formatTxt = (TextView)this.findViewById(R.id.event_format_content);
                descTxt = (TextView)this.findViewById(R.id.event_desc_content);
                amountTxt = (TextView)this.findViewById(R.id.event_needed_content);

                intent1 = this.getIntent();
                eventNum = intent1.getIntExtra("Event_Num", 0);
                eventDate = intent1.getStringExtra("Event_Date");
                eventTime = intent1.getStringExtra("Event_Time");
                eventDuration = intent1.getIntExtra("Event_Duration", 0);
                eventTopic = intent1.getStringExtra("Event_Topic");
                eventDesc = intent1.getStringExtra("Event_Desc");
                eventFormat = intent1.getStringExtra("Event_Format");
                eventAmount = intent1.getIntExtra("Event_AmbAmount", 0);

                dateTxt.setText(eventDate);
                timetxt.setText(eventTime);
                topicTxt.setText(eventTopic);
                durationTxt.setText(Integer.toString(eventDuration));
                formatTxt.setText(eventFormat);
                amountTxt.setText(Integer.toString(eventAmount));
                descTxt.setText(eventDesc);

        }

        @Override
        public void onClick(View v) {
                int id = v.getId();
                if(id == R.id.event_delete_button){
                        sql = "delete from Event where Event_Num = " + Integer.toString(eventNum);
                        DBOperator.getInstance().execSQL(sql);
                        Intent intent = new Intent(this,NewEventActivity.class);
                        this.startActivity(intent);
                }else if(id == R.id.event_readmore_button){
                        Intent intent = new Intent(getApplicationContext(),EventAmbassadorActivity.class);
                        intent.putExtra("Event_Num",eventNum);
                        this.startActivity(intent);
                }else if(id == R.id.event_edit_button){
                        Intent intent = new Intent(getApplicationContext(),EventEditActivity.class);
                        intent.putExtra("Event_Num",eventNum);
                        this.startActivity(intent);
                }
        }
}