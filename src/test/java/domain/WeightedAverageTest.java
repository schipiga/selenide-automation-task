package domain;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import university.domain.Average;
import university.domain.Grade;
import university.domain.WeightedAverage;

import java.util.Arrays;
import java.util.List;

public class WeightedAverageTest {

    private Average averageComputationStrategy;

    @Before
    public void setUp() {
        averageComputationStrategy = new WeightedAverage();
    }

    @Test
    public void testCompute() {
        List<Grade> grades = Arrays.asList(new Grade("Java", 0.2, 3), new Grade("OOD", 0.4, 5), new Grade("Webapps", 0.4, 3));
        Assert.assertEquals(3.8, averageComputationStrategy.compute(grades), 0.01);
    }
}
