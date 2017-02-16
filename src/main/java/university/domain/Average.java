package university.domain;

import java.util.List;

/**
 * An interface for average computation.
 */
public interface Average {

    /**
     * Returns the average grade.
     *
     * @param grades the list of grades
     * @return the average grade
     */
    Double compute(List<Grade> grades);
}
