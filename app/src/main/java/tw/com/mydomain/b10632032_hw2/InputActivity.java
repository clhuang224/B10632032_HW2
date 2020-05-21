package tw.com.mydomain.b10632032_hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputActivity extends AppCompatActivity {

    Button submitButton, cancelButton;
    EditText guestNameEditText, guestAmountEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        submitButton = findViewById(R.id.b_submitButton);
        cancelButton = findViewById(R.id.b_cancelButton);
        guestNameEditText = findViewById(R.id.et_guestName);
        guestAmountEditText = findViewById(R.id.et_guestAmount);

        final Intent intentToReturn = new Intent();
        intentToReturn.putExtra("add", false);
        InputActivity.this.setResult(RESULT_OK, intentToReturn);

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intentToReturn.putExtra("add", true);
                intentToReturn.putExtra("guestName", guestNameEditText.getText());
                intentToReturn.putExtra("guestAmount", guestAmountEditText.getText());
                InputActivity.this.finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InputActivity.this.finish();
            }
        });
    }
}
