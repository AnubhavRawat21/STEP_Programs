package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Student;
import model.SubjectMarks;
import util.DBConnection;

public class StudentDAOImpl implements StudentDAO {
	public void addStudent(Student student) {

      // Queries

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps =
                    con.prepareStatement(studentSql,
                            Statement.RETURN_GENERATED_KEYS);

         // set the data 
            
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

          // iterate rs and get the data

            PreparedStatement markPs =
                    con.prepareStatement(marksSql);

          // set the values
            
            markPs.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Student getStudentById(int id) {

      // Queries

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

          // iterate rs and set values to student obj

               

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<Student>();

       // query

        try (Connection con = DBConnection.getConnection()) {

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

              // iterate and set values
            	
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    public void updateStudent(Student student) {

       // queries
    	
        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps =
                    con.prepareStatement(studentSql);

            //get the values

            ps.executeUpdate();

            SubjectMarks mark = student.getSubjects()[0];

            PreparedStatement markPs =
                    con.prepareStatement(marksSql);

           //set values

            markPs.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {

     //query 
    	
        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps =
                    con.prepareStatement(sql);

          //set values

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
