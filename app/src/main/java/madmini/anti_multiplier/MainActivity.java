package madmini.anti_multiplier;

import android.icu.math.BigDecimal;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private TextView resultField;
    private EditText numField;
    private EditText denomField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // enable dynamic resizing if keyboard is active
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // set result field
        this.resultField = (TextView) findViewById(R.id.resultText);

        // add event listeners
        (this.numField = (EditText) findViewById(R.id.editNumerator)).setOnFocusChangeListener(this);
        (this.denomField = (EditText) findViewById(R.id.editDenominator)).setOnFocusChangeListener(this);

        // call method manually
        this.onFocusChange(null,false);
    }

    private double divide (double num, double denom) {
        return num/denom;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            double num = Double.parseDouble(numField.getText().toString());
            double denom = Double.parseDouble(denomField.getText().toString());

            String result = "";
            if (denom == 0) {
                result = "= NaN";
            } else {
                // using regex to remove trailing zeroes
                result = "= " + Double.toString(divide(num, denom)).replaceAll("\\.0+$","");
            }

            this.resultField.setText(result);
        }
    }
}
