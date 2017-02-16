package university.domain;

import java.util.List;

/**
 * ArithmeticAverage class is responsible for computation of student's arithmetic average grade.
 */
public class ArithmeticAverage implements Average {

    public Double compute(List<Grade> grades) {
        double arithmeticAverageGrade = 0;
        for (Grade grade : grades) {
            arithmeticAverageGrade += grade.getValue();
        }
        return arithmeticAverageGrade / grades.size();
    }
}
