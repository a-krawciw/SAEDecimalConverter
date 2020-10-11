package awesome.is.alec.saedecimalconverter;

import org.junit.Assert;
import org.junit.Test;

public class TestDecimalDivision {


    @Test
    public void TestDecimalReduction(){
        Assert.assertEquals(12, MainActivity.max_divisor(12, 24));
    }

}
