package university.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import university.dao.GenericDao;
import university.dao.StudentDaoJAXBImpl;
import university.domain.Student;

import com.sun.jersey.api.json.JSONWithPadding;

/**
 * StudentsResource class is responsible for providing a RESTful-service
 * on the web that delivers students' information in JSONP format.
 */
@Path("/university")
public class StudentsResource {

    private GenericDao<Student> dao;
    private Comparator<Student> sortOrder;

    /**
     * Constructs a new instance of the resource.
     */
    public StudentsResource() {
        dao = new StudentDaoJAXBImpl();
        sortOrder = Student.Order.ByWeightedAverage.descending();
    }


    /**
     * Returns the response to a HTTP-request corresponding to the provided mapping.
     *
     * @param callback the name of a callback function wrapped around JSON
     * @return the response to a HTTP-request
     */
    @GET
    @Produces("application/x-javascript")
    @Path("/results")
    public Response getStudentsSorted(@QueryParam("callback") String callback) {

        List<Student> students = dao.findAll();
        Collections.sort(students, sortOrder);

        StringBuilder buffer = new StringBuilder("{\"students\":[");
        for (Student student : students) {
            buffer.append("{\"id\" : ").append("\"").append(student.getId()).append("\"").append(",")
                    .append(" \"fullname\" : ").append("\"").append(student.getFullName()).append("\"").append(",")
                    .append(" \"discipline1\" : ").append("\"").append(student.getGrades().get(0).getValue()).append("\"").append(",")
                    .append(" \"discipline2\" : ").append("\"").append(student.getGrades().get(1).getValue()).append("\"").append(",")
                    .append(" \"discipline3\" : ").append("\"").append(student.getGrades().get(2).getValue()).append("\"").append(",")
                    .append(" \"weighted\" : ").append("\"").append(student.getWeightedAverageGrade()).append("\"").append("},");
        }
        buffer.append("]}");
        
        System.out.println(buffer.toString());

        return Response.status(200).entity(new JSONWithPadding(buffer.toString(), callback)).build();
    }
}

