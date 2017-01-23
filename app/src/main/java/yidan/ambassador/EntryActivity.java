package yidan.ambassador;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by xyd on 16/4/21.
 */
public class EntryActivity extends Activity implements View.OnClickListener{
    ImageButton entryBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_layout);

        entryBtn = (ImageButton)this.findViewById(R.id.entry_imgBtn);
        entryBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.entry_imgBtn){
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        }
    }
}
