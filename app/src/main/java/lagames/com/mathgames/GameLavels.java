package lagames.com.mathgames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class GameLavels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelevels);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(GameLavels.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e){

                }
            }
        });

        TextView textView1 = findViewById(R.id.textView1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(GameLavels.this, Level1.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e){

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(GameLavels.this, MainActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){

        }
    }
}