package madmini.anti_multiplier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // enable dynamic resizing on keyboard activate
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // fetch fields
        TextView resultField = (TextView) findViewById(R.id.resultText);
        EditText numField = (EditText) findViewById(R.id.editNumerator);
        EditText denomField = (EditText) findViewById(R.id.editDenominator);

        // init divider and call manually to fill in sample result
        new Divider(resultField, numField, denomField).onTextChanged(null,0,0,0);
    }

    private class Divider implements TextWatcher {

        private final TextView resultField;
        private final EditText numField;
        private final EditText denomField;

        Divider(TextView resultField, EditText numField, EditText denomField) {
            this.resultField = resultField;
            (this.numField = numField).addTextChangedListener(this);
            (this.denomField = denomField).addTextChangedListener(this);
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String num = this.numField.getText().toString();
            String denom = this.denomField.getText().toString();

            this.resultField.setText(this.divide(num, denom));
        }

        private String divide (String numStr, String denomStr) {
            // Double.parseDouble() throws exceptions if there is anything wrong with the input.
            // could check input beforehand, but that would be tedious and prone to errors
            // see https://docs.oracle.com/javase/8/docs/api/java/lang/Double.html#valueOf-java.lang.String-
            try {
                double num = Double.parseDouble(numStr);
                double denom = Double.parseDouble(denomStr);

                // use regex to get rid of trailing zeroes like in 1.0
                return "= " + Double.toString(num / denom).replaceAll("\\.0+$", "");

            } catch (NumberFormatException e) {
                return getResources().getString(R.string.text_formatError);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void afterTextChanged(Editable s) {}
    }
}
