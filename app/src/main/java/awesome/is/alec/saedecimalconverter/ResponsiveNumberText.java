package awesome.is.alec.saedecimalconverter;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

import java.security.Key;
import java.util.Objects;

import awesome.is.alec.saedecimalconverter.model.FractionValue;
import awesome.is.alec.saedecimalconverter.model.Units;
import awesome.is.alec.saedecimalconverter.model.ValueListener;

public class ResponsiveNumberText extends android.support.v7.widget.AppCompatEditText implements ValueListener {

    private KeyEvent.Callback passEventTo;
    private final Units fieldUnit;

    public ResponsiveNumberText(Context context) {
        super(context);
        fieldUnit = Units.METER;
    }

    public ResponsiveNumberText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResponsiveNumberText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ResponsiveNumberText,
                defStyleAttr, 0);

        fieldUnit = Units.valueOf(a.getString(R.styleable.ResponsiveNumberText_unit));
        a.recycle();
    }

    public void registerKeyListener(KeyEvent.Callback listener){
        passEventTo = listener;
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (passEventTo != null){
            passEventTo.onKeyUp(keyCode, event);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (passEventTo != null){
            passEventTo.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void valueChanged(FractionValue newValue) {
        if (!hasFocus()) {
            setText(""+newValue.toUnit(fieldUnit).getValue());
        }
    }

    public Units getUnits(){
        return fieldUnit;
    }


}
