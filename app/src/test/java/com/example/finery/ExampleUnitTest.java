package com.example.finery;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    ProductCustomerMoreDetails productCustomerMoreDetails;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    //Test cases to calculate the offered price (price after calculate with offer)
    @Before
    public void calcDuePrice (){productCustomerMoreDetails = new ProductCustomerMoreDetails();}

    @Test
    public void testCalcDuePrice (){
        int result = productCustomerMoreDetails.calculateOffer(100, 10);
        assertEquals(90, result);
    }
}