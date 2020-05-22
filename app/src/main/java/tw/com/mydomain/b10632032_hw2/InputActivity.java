package tw.com.mydomain.b10632032_hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                if (guestAmountEditText.getText().length() != 0 && guestNameEditText.getText().length() != 0) {
                    intentToReturn.putExtra("add", true);
                    intentToReturn.putExtra("guestName", guestNameEditText.getText().toString());
                    int guestAmount = Integer.parseInt(guestAmountEditText.getText().toString());
                    intentToReturn.putExtra("guestAmount", guestAmount);
                    InputActivity.this.finish();
                } else {
                    Toast toast = Toast.makeText(InputActivity.this, "欄位請勿空白", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InputActivity.this.finish();
            }
        });
    }
}
