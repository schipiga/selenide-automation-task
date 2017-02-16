package university.domain;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Student class is provided as a model for students participating in the course.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Student {

    @XmlAttribute
    private long id;
    private String fullName;

    @XmlElementWrapper(name = "grades")
    @XmlElement(name = "grade")
    private List<Grade> grades;

    @XmlTransient
    private Average averageComputationStrategy;

    /**
     * Constructs new student instance.
     */
    public Student() {
        grades = new ArrayList<Grade>();
        averageComputationStrategy = new WeightedAverage();
    }

    /**
     * Constructs new student instance with a full name provided.
     *
     * @param fullName the full name of a student
     */
    public Student(String fullName) {
        this();
        this.fullName = fullName;
    }

    /**
     * Enum that provides various options for sorting students.
     */
    public static enum Order implements Comparator<Student> {

        /**
         * Provides a sorting mechanism that reorders students in regard to their weighted average grade.
         */
        ByWeightedAverage() {
            @Override
            public int compare(Student st1, Student st2) {
                int result = st1.getWeightedAverageGrade().compareTo(st2.getWeightedAverageGrade());
                if (result == 0) {
                    if (ascending) {
                        return ByName.compare(st1, st2);
                    } else {
                        return ByName.compare(st2, st1);
                    }
                }
                return result;
            }
        },

        /**
         * Provides a sorting mechanism that reorders students in regard to their full name.
         */
        ByName() {
            @Override
            public int compare(Student st1, Student st2) {
                return st1.getFullName().compareTo(st2.getFullName());
            }
        };

        private static boolean ascending;

        /**
         * Compares two students together.
         *
         * @param st1 the first student to be compared
         * @param st2 the second student to be compared
         * @return a negative integer, zero, or a positive integer as the
         *         first argument is less than, equal to, or greater than the
         *         second.
         */
        public abstract int compare(Student st1, Student st2);

        /**
         * Returns the comparator instance that sorts students in ascending order.
         *
         * @return the comparator instance that sorts students in ascending order
         */
        public Comparator<Student> ascending() {
            ascending = true;
            return this;
        }

        /**
         * Returns the comparator instance that sorts students in descending order.
         *
         * @return the comparator instance that sorts students in descending order
         */
        public Comparator<Student> descending() {
            ascending = false;
            return Collections.reverseOrder(this);
        }
    }

    /**
     * Returns the weighted average grade for a student.
     *
     * @return the weighted average grade
     */
    public Double getWeightedAverageGrade() {
        return averageComputationStrategy.compute(grades);
    }

    /**
     * Returns the id of a student.
     *
     * @return the id of a student
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of a student.
     *
     * @param id the id of a student.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the full name of a student.
     *
     * @return the full name of a student
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name of a student.
     *
     * @param fullName the full name of a student
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Returns the list of grades for a student.
     *
     * @return the list of grades
     */
    public List<Grade> getGrades() {
        return grades;
    }

    /**
     * Sets the list of grades for a student.
     *
     * @param grades the list of grades
     */
    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }


    public Average getAverageComputationStrategy() {
        return averageComputationStrategy;
    }

    public void setAverageComputationStrategy(Average averageComputationStrategy) {
        this.averageComputationStrategy = averageComputationStrategy;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", grades=" + grades +
                '}';
    }
}
