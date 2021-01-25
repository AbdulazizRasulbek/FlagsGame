package uz.pdp.findtheword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MenuActivity extends AppCompatActivity {
    ImageView play, info, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        loadViews();
        setOnclick();
    }

    private void setOnclick() {
        play.setOnClickListener(this::onClick);
        info.setOnClickListener(this::onClick);
        exit.setOnClickListener(this::onClick);
    }

    void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_img:
                startActivity(new Intent(this,AboutActivity.class));
                break;

            case R.id.play_img:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.exit_img:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

        }
    }

    private void loadViews() {
        play = findViewById(R.id.play_img);
        info = findViewById(R.id.info_img);
        exit = findViewById(R.id.exit_img);
    }
}
