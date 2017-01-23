package yidan.ambassador;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import yidan.ambassador.constant.SQLCommand;
import yidan.ambassador.util.DBOperator;

/**
 * Created by xyd on 16/4/22.
 */
public class AmbasadorDetailActivity extends Activity implements View.OnClickListener {

    Button deleteBtn;
    TextView nameTxt, cityTxt, gradTxt, majorTxt, phoneTxt, workTxt, expTxt, emailTxt,
            skill1Txt, skill2Txt, skill3Txt, skill4Txt, skill5Txt;
    TextView avgTxt;
    int ambID,ambMajor,ambGrad,ambWork,a_id;
    String ambName,ambCity,ambPhone,ambEmail,ambExp,ambGradStr,workHrStr;
    Intent intent;
    Cursor cursor2,cursor3;
    String sql1,sql2;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amb_detail_layout);

        deleteBtn = (Button)this.findViewById(R.id.amb_delete_button);
        deleteBtn.setOnClickListener(this);

        nameTxt = (TextView) this.findViewById(R.id.amb_name_content);
        cityTxt = (TextView) this.findViewById(R.id.amb_city_content);
        gradTxt = (TextView) this.findViewById(R.id.amb_gradYr_content);
        majorTxt = (TextView) this.findViewById(R.id.amb_major_content);
        phoneTxt = (TextView) this.findViewById(R.id.amb_phone_content);
        workTxt = (TextView) this.findViewById(R.id.amb_workHr_content);
        expTxt = (TextView) this.findViewById(R.id.amb_workExp_content);
        emailTxt = (TextView) this.findViewById(R.id.amb_email_content);
        skill1Txt = (TextView) this.findViewById(R.id.amb_skill1_content);
        skill2Txt = (TextView) this.findViewById(R.id.amb_skill2_content);
        skill3Txt = (TextView) this.findViewById(R.id.amb_skill3_content);
        skill4Txt = (TextView) this.findViewById(R.id.amb_skill4_content);
        skill5Txt = (TextView) this.findViewById(R.id.amb_skill5_content);
        avgTxt = (TextView) this.findViewById(R.id.amb_avg_content);

        intent = this.getIntent();
        ambID = intent.getIntExtra("AmbID", 0);
        ambName = intent.getStringExtra("AmbName");
        ambCity = intent.getStringExtra("AmbCity");
        ambMajor = intent.getIntExtra("AmbMajor", 0);
        ambGrad = intent.getIntExtra("AmbGrad", 0);
        ambWork = intent.getIntExtra("AmbWork", 0);
        ambPhone = intent.getStringExtra("AmbPhone");
        ambEmail = intent.getStringExtra("AmbEmail");
        ambExp = intent.getStringExtra("AmbExp");
        a_id = intent.getIntExtra("_id",0);

        nameTxt.setText(ambName);
        cityTxt.setText(ambCity);
        ambGradStr = Integer.toString(ambGrad);
        gradTxt.setText(ambGradStr);
        switch (ambMajor) {
            case 1:
                majorTxt.setText("MSIT");
                break;
            case 2:
                majorTxt.setText("MSOAM");
                break;
            case 3:
                majorTxt.setText("MSMTI");
                break;
            case 4:
                majorTxt.setText("MBA");
                break;
            case 5:
                majorTxt.setText("MSDM");
                break;
        }
        phoneTxt.setText(ambPhone);
        workHrStr = Integer.toString(ambWork);
        workTxt.setText(workHrStr);
        expTxt.setText(ambExp);
        emailTxt.setText(ambEmail);

        cursor2 = DBOperator.getInstance().execQuery(SQLCommand.AMB_SKILL_LIST);
        skill1Txt.setText("");
        skill2Txt.setText("");
        skill3Txt.setText("");
        skill4Txt.setText("");
        skill5Txt.setText("");

        int count = 0;
        String[] skill = {"VIDEO MAKING", "EVENT PLANNING",
                "GRAPHIC DESIGNING", "GRAPHIC DESIGNING",
                "POSTER MAKING"};
        while (cursor2.moveToNext()) {

            int ambId = cursor2.getInt(cursor2.getColumnIndex("Amb_ID"));
            int skillNum = cursor2.getInt(cursor2.getColumnIndex("Skill_Num"));
            int ambSkill_id = cursor2.getInt(cursor2.getColumnIndex("_id"));

            if (ambID==ambId ) {
                count++;

                switch (count) {
                    case 1:
                        skill1Txt.setText(skill[skillNum - 1]);
                        break;
                    case 2:
                        skill2Txt.setText(skill[skillNum - 1]);
                        break;
                    case 3:
                        skill3Txt.setText(skill[skillNum - 1]);
                        break;
                    case 4:
                        skill4Txt.setText(skill[skillNum - 1]);
                        break;
                    case 5:
                        skill5Txt.setText(skill[skillNum - 1]);
                        break;
                }
            }
        }

        sql2 = "select Amb_ID, Event_Num, Admin_ID, " +
                "Criteria_Num, Perform_Score, _id from Performance where Amb_ID = " + Integer.toString(ambID);
        cursor3 = DBOperator.getInstance().execQuery(sql2);
        int count1 = 0;
        int sum = 0;
        double avg = 0.0;
        avgTxt.setText("");

        while (cursor3.moveToNext()) {
            int ambPerfID = cursor3.getInt(cursor3.getColumnIndex("Amb_ID"));
            int score = cursor3.getInt(cursor3.getColumnIndex("Perform_Score"));
            if (ambID == ambPerfID) {
                count1++;
                sum = sum + score;
                avg = (sum / count1);
            }
            avgTxt.setText(Double.toString(avg));
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.amb_delete_button) {
            db = this.openOrCreateDatabase("ambassador_11",MODE_PRIVATE,null);
            sql1 = "delete from Ambassador where _id = " + Integer.toString(a_id);
            DBOperator.getInstance().execSQL(sql1);
            nameTxt.setText("");
            cityTxt.setText("");
            gradTxt.setText("");
            majorTxt.setText("");
            phoneTxt.setText("");
            workTxt.setText("");
            expTxt.setText("");
            emailTxt.setText("");
            skill1Txt.setText("");
            skill2Txt.setText("");
            skill3Txt.setText("");
            skill4Txt.setText("");
            skill5Txt.setText("");
            avgTxt.setText("");
            Intent intent2 = new Intent(getApplicationContext(),AmbassadorActivity.class);
            this.startActivity(intent2);
        }
    }
}