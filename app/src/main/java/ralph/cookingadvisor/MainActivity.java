package ralph.cookingadvisor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


/**
 * Created by rafaelchris on 5/11/17.
 */

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;

    private void startMyActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, RecipeFeed.class);
                startMyActivity(myIntent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, RecipeAdvice.class);
                startActivity(myIntent);
            }
        });
    }


}
