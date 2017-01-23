package yidan.ambassador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//     Button adminBtn,ambassadorBtn;
     ImageButton adminBtn,ambassadorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        adminBtn = (ImageButton) this.findViewById(R.id.main_administrator_entry_button);
        adminBtn.setOnClickListener(this);
        ambassadorBtn = (ImageButton) this.findViewById(R.id.main_ambassador_entry_button);
        ambassadorBtn.setOnClickListener(this);
    }
    public void onClick(View v){
        int id = v.getId();
        if (id == R.id.main_ambassador_entry_button){
            Intent intent = new Intent(this,UserLoginActivity.class);
            this.startActivity(intent);
        } else if(id==R.id.main_administrator_entry_button){
            Intent intent = new Intent(this,AdminLoginActivity.class);
            this.startActivity(intent);
        }
    }

}
