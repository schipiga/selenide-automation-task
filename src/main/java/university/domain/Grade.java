package university.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Grade class is provided as a model for grades received during the course.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Grade {

    private String discipline;
    private double weight;
    private int value;

    /**
     * Constructs new grade instance.
     */
    public Grade() {
    }

    /**
     * Constructs new grade instance with discipline's name, value and weight provided.
     *
     * @param discipline the discipline's name
     * @param weight     the weight of a grade
     * @param value      the value of a grade
     */
    public Grade(String discipline, double weight, int value) {
        this.discipline = discipline;
        this.weight = weight;
        this.value = value;
    }

    /**
     * Returns the name of a discipline.
     *
     * @return the name of a discipline
     */
    public String getDiscipline() {
        return discipline;
    }

    /**
     * Sets the name of a discipline.
     *
     * @param discipline the name of a discipline
     */
    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    /**
     * Returns the weight of a grade.
     *
     * @return the weight of a grade
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight of a grade.
     *
     * @param weight the weight of a grade
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Returns the value of a grade.
     *
     * @return the value of a grade
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of a grade.
     *
     * @param value the value of a grade
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "discipline='" + discipline + '\'' +
                ", weight=" + weight +
                ", value=" + value +
                '}';
    }
}
