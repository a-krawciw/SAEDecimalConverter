package awesome.is.alec.saedecimalconverter.model;

public enum Units {
    METER(1),
    MILLIMETER(0.001),
    INCH(0.0254);


    private double toMeters;

    Units(double toMeters){
        this.toMeters = toMeters;
    }

    public double convertToUnit(Units newUnit, double value){
        return value*this.toMeters/newUnit.toMeters;
    }
}
