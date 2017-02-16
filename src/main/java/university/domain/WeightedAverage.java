package university.domain;

import java.util.List;

/**
 * WeightedAverage class is responsible for computation of student's weighted average grade.
 */
public class WeightedAverage implements Average {


    public Double compute(List<Grade> grades) {
        double weightedAverageGrade = 0;
        for (Grade grade : grades) {
            weightedAverageGrade += grade.getWeight() * grade.getValue();
        }
        return weightedAverageGrade;
    }
}
