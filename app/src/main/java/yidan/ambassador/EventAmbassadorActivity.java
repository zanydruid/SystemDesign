package yidan.ambassador;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.lang.reflect.AccessibleObject;

import yidan.ambassador.constant.SQLCommand;
import yidan.ambassador.util.DBOperator;

/**
 * Created by xyd on 16/4/22.
 */
public class EventAmbassadorActivity extends Activity {
    int eventNum;
    String event_Num;
    ListView evtAmbListView;
    Cursor cursor,cursor1;
    String totalStr;
    int total;
    TextView totalTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_amb_list_layout);

        Intent intent = this.getIntent();
        eventNum = intent.getIntExtra("EventNum", 0);
        event_Num = Integer.toString(eventNum);

        evtAmbListView = (ListView) this.findViewById(R.id.admin_event_amb_list);
        evtAmbListView.setOnItemClickListener(new ItemClickListener());

        totalTxt = (TextView)this.findViewById(R.id.event_amb_total);

        cursor = DBOperator.getInstance().execQuery(SQLCommand.AMB_REGISTER,this.getParameters(event_Num));
        Cursor cursor2 = DBOperator.getInstance().execQuery("select Major_Name,Amb_Name,Register._id,Ambassador._id,Major._id from Ambassador,Register,Major " +
                "where Ambassador.Amb_ID = Register.Amb_ID and " +
                "Ambassador.Major_Num = Major.Major_Num and Event_Num = 1 " +
                "group by Major_Name,Amb_Name,Register._id");
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.activity_event_amb_list_item,
                cursor2,
                new String[]{"Major_Name","Amb_Name"},
                new int[]{R.id.event_amb_major,R.id.event_amb_name},
                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE
                );
        evtAmbListView.setAdapter(adapter);
        cursor1 = DBOperator.getInstance().execQuery(SQLCommand.AMB_REGISTER_TOTAL, this.getParameters(event_Num));
        while (cursor1.moveToNext()) {
            total = cursor1.getInt(cursor1.getColumnIndex("count(Amb_ID)"));
            totalStr = Integer.toString(total);
            totalTxt.setText(totalStr);
        }
        totalTxt.setText("5");
    }

    private String[] getParameters(String num){
        String args[]=new String[1];
        args[0] = num;
        return args;
    }

    class ItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}