package awesome.is.alec.saedecimalconverter.model;

import java.util.ArrayList;
import java.util.List;

public class FractionStore {
    private List<ValueListener> listeners = new ArrayList<>();
    private FractionValue activeValue = new FractionValue(0, 128, Units.INCH);

    public void registerListener(ValueListener v){
        listeners.add(v);
    }

    protected void notifyListeners(){
        for (ValueListener listener :
                listeners) {
            listener.valueChanged(activeValue);
        }
    }

    public void setActiveValue(FractionValue newValue){
        activeValue = newValue;
        notifyListeners();
    }

    public FractionValue getActiveValue(){
        return activeValue;
    }

}
