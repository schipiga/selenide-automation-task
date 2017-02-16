package domain;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import university.domain.ArithmeticAverage;
import university.domain.Average;
import university.domain.Grade;

import java.util.Arrays;
import java.util.List;

public class ArithmeticAverageTest {

    private Average averageComputationStrategy;

    @Before
    public void setUp() {
        averageComputationStrategy = new ArithmeticAverage();
    }

    @Test
    public void testCompute() throws Exception {
        List<Grade> grades = Arrays.asList(new Grade("Java", 0.2, 3), new Grade("OOD", 0.4, 5), new Grade("Webapps", 0.4, 3));
        Assert.assertEquals(3.66, averageComputationStrategy.compute(grades), 0.01);
    }
}
