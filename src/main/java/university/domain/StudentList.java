package university.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentList class is provided as a model for a list of students participating in the course.
 */
@XmlRootElement(name = "students")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentList {

    @XmlElement(name = "student")
    private List<Student> students;

    /**
     * Constructs a new instance of students list.
     */
    public StudentList() {
        students = new ArrayList<Student>();
    }

    /**
     * Returns the list of students.
     *
     * @return the list of students.
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Sets the list of students.
     *
     * @param students the list of students
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
