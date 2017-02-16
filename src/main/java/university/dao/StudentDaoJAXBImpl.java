package university.dao;

import university.domain.Student;
import university.domain.StudentList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * StudentDaoJAXBImpl class is responsible for interaction with persistent objects of Student
 * type using JAXB.
 */
public class StudentDaoJAXBImpl implements GenericDao<Student> {

    private JAXBContext jc;
    private Properties properties;

    /**
     * Constructs a new instance of student data access object.
     */
    public StudentDaoJAXBImpl() {
        properties = new Properties();
    }

    public List<Student> findAll() {

        StudentList studentList = null;
        InputStream is = StudentDaoJAXBImpl.class.getResourceAsStream("/config.properties");
        try {
            properties.load(is);
            jc = JAXBContext.newInstance(StudentList.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            studentList = (StudentList) unmarshaller.unmarshal(new File(properties.getProperty("filepath")));
        } catch (IOException e) {
            System.err.println("ERROR: while opening XML file");
        } catch (JAXBException e) {
            System.err.println("ERROR: while performing Object-XML binding");
        }

        return studentList.getStudents();
    }
}
