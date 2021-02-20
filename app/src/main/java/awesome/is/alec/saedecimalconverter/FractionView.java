package awesome.is.alec.saedecimalconverter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import awesome.is.alec.saedecimalconverter.model.FractionValue;
import awesome.is.alec.saedecimalconverter.model.Units;
import awesome.is.alec.saedecimalconverter.model.ValueListener;

public class FractionView extends View implements ValueListener {

    private int aNumerator = 0;
    private int aDenominator = 1;

    private boolean mixed = false;

    private Paint textPaint;
    private Paint linePaint;

    private float textSize = 0;


    /**
     * Constructors
     * @param context
     */
    public FractionView(Context context) {
        super(context);
    }

    public FractionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FractionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FractionView,
                defStyleAttr, 0);

        try {
            aNumerator = a.getInteger(R.styleable.FractionView_numerator, 0);
            aDenominator = a.getInteger(R.styleable.FractionView_denominator, 0);
            textSize = a.getDimensionPixelSize(R.styleable.FractionView_textSize, 0);
            mixed = a.getBoolean(R.styleable.FractionView_mixed_fractions, false);

        } finally {
            a.recycle();
        }

        initPainting();
    }

    private void initPainting(){
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        if (textSize == 0) {
            textSize = textPaint.getTextSize();
        } else {
            textPaint.setTextSize(textSize);
        }

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(3.0f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mixed) {
            String num = "" + getNumerator();
            canvas.drawText(num, (float) (getWidth() / 2.0 - textPaint.measureText(num) / 2), (float) (getHeight() / 2.0 - textPaint.getTextSize() * 0.5), textPaint);


            String den = "" + getDenominator();
            canvas.drawText(den, (float) (getWidth() / 2.0 - textPaint.measureText(den) / 2), (float) (getHeight() / 2.0 + textPaint.getTextSize() * 1.5), textPaint);


            float max = Math.max(textPaint.measureText(den), textPaint.measureText(num));
            canvas.drawLine((float) (getWidth() / 2.0 - max * 0.6), (float) (getHeight() / 2.0), (float) (getWidth() / 2.0 + textPaint.measureText(den) * 0.6), (float) (getHeight() / 2.0), linePaint);
        } else {
            int wholeNum = aNumerator/aDenominator;
            int dispNum = aNumerator % aDenominator;

            String num = "" + dispNum;
            canvas.drawText(num, (float) (getWidth() * 2.0 / 3.0 - textPaint.measureText(num) / 2), (float) (getHeight() / 2.0 - textPaint.getTextSize() * 0.5), textPaint);


            String den = "" + getDenominator();
            canvas.drawText(den, (float) (getWidth() * 2.0 / 3.0 - textPaint.measureText(den) / 2), (float) (getHeight() / 2.0 + textPaint.getTextSize() * 1.5), textPaint);

            canvas.drawText("" + wholeNum, (float) (getWidth() * 1.0 / 3.0 - textPaint.measureText(num) / 2), (float) (getHeight() / 2.0 + textPaint.getTextSize() * 0.5), textPaint);


            float max = Math.max(textPaint.measureText(den), textPaint.measureText(num));
            canvas.drawLine((float) (getWidth() * 2.0 / 3.0 - max * 0.6), (float) (getHeight() / 2.0), (float) (getWidth() * 2.0 / 3.0 + textPaint.measureText(den) * 0.6), (float) (getHeight() / 2.0), linePaint);

        }

    }

    public int getNumerator() {
        return aNumerator;
    }

    public int getDenominator() {
        return aDenominator;
    }

    public void useMixedFractions(){
        mixed = true;
        invalidate();
    }

    public void useImproperFractions(){
        mixed = false;
        invalidate();
    }


    @Override
    public void valueChanged(FractionValue newValue) {
        newValue = newValue.toUnit(Units.INCH);
        aDenominator = newValue.getDenominator();
        aNumerator = newValue.getNumerator();
        invalidate();
    }
}
