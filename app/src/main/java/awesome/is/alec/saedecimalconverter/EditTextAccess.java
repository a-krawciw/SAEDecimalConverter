package awesome.is.alec.saedecimalconverter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

import java.security.Key;

public class EditTextAccess extends android.support.v7.widget.AppCompatEditText {

    private KeyEvent.Callback passEventTo;

    public EditTextAccess(Context context) {
        super(context);
    }

    public EditTextAccess(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextAccess(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
}
