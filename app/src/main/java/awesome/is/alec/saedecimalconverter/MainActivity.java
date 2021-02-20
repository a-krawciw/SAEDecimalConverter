package awesome.is.alec.saedecimalconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Spinner;

import java.math.BigInteger;
import java.util.Objects;

import awesome.is.alec.saedecimalconverter.model.FractionStore;
import awesome.is.alec.saedecimalconverter.model.FractionValue;
import awesome.is.alec.saedecimalconverter.model.Units;

public class MainActivity extends AppCompatActivity {

    private ResponsiveNumberText decimalInput;
    private FractionView fractionView;
    private Spinner denomSpinner;
    private Checkable checkable;
    private int largestDenominator = 128;

    private FractionStore fractionStore = new FractionStore();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        decimalInput = findViewById(R.id.decimalNumber);
        fractionView = findViewById(R.id.fraction);
        denomSpinner = findViewById(R.id.spinner);
        checkable = findViewById(R.id.checkBox);

        decimalInput.registerKeyListener(new KeyListener(decimalInput, Units.INCH));

        fractionStore.registerListener(decimalInput);
        fractionStore.registerListener(fractionView);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.SAE_Denoms, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        denomSpinner.setAdapter(adapter);
        denomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                largestDenominator = Integer.parseInt((String) denomSpinner.getSelectedItem());
                fractionStore.setActiveValue(fractionStore.getActiveValue().withMaxDenominator(largestDenominator));
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
            });
    }

    public void OnCheckBox(View v){
        if(checkable.isChecked()){
            fractionView.useMixedFractions();
        } else {
            fractionView.useImproperFractions();
        }
    }

    private class KeyListener implements KeyEvent.Callback{

        private EditText textView;
        private Units textViewUnits;

        public KeyListener(EditText watching, Units textViewUnits){
            textView = watching;
            this.textViewUnits = textViewUnits;
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            return false;
        }

        @Override
        public boolean onKeyLongPress(int keyCode, KeyEvent event) {
            return false;
        }

        @Override
        public boolean onKeyUp(int keyCode, KeyEvent event) {
            try{
                double value = Double.parseDouble(String.valueOf(textView.getText()));
                fractionStore.setActiveValue(new FractionValue(value, largestDenominator, textViewUnits));
            } catch (NumberFormatException e){
                //Ignore
            }
            return false;
        }

        @Override
        public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
            return false;
        }
    }

    static int max_divisor(int i1, int i2) {
        BigInteger b1 = BigInteger.valueOf(i1);
        BigInteger b2 = BigInteger.valueOf(i2);
        return b1.gcd(b2).intValue();
    }






}