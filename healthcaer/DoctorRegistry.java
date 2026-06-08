package healthcaer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.TreeMap;

class doctor {
    private int doctorId;
    private String name;
    private String specialization;

    doctor(int doctorId, String name, String specialization) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        }
    
    public String getName() {
    return name;}

    public int getDoctorId() {
        return doctorId; }

    public String getSpecialization() {
    return specialization;
}
}

class doctordirectory {
    private HashMap<Integer, doctor> doctors;

    public doctordirectory() {
        doctors = new HashMap<>();
    }

    public void addDoctor(doctor doc) {
        doctors.put(doc.getDoctorId(), doc);
    }

    public doctor findDoctorById(int id) {
        return doctors.get(id);
    }
    
    public doctor removeDoctorById(int id) {
        return doctors.remove(id);
    }
    
    public doctor getdoctorBySpecialization(String specialization) {
        for (doctor doc : doctors.values()) {
            if (doc.getSpecialization().equals(specialization)) {
                return doc;
            }
        }
        return null; // No doctor found with the given specialization
    }
}

class appointmentScheduler {
    // This class can be implemented to manage appointments between doctors and patients
    private TreeMap<String, doctor> appointmentSchedule;

    private LocalDateTime appointmentTime;

    appointmentScheduler() {
        appointmentSchedule = new TreeMap<>();
    }
    public doctor getAppointmentByDateTime(LocalDateTime dateTime) {
        return appointmentSchedule.get(dateTime.toString());
    }

    

    
}

public class DoctorRegistry {
    public static void main(String[] args) {
        doctordirectory directory = new doctordirectory();
        
        doctor doc1 = new doctor(1, "Dr. Smith", "Cardiology");
        doctor doc2 = new doctor(2, "Dr. Johnson", "Neurology");
        doctor doc3 = new doctor(3, "Dr. Lee", "Pediatrics");
        
        directory.addDoctor(doc1);
        directory.addDoctor(doc2);
        directory.addDoctor(doc3);
        
        doctor foundDoctor = directory.findDoctorById(1);
        if (foundDoctor != null) {
            System.out.println("Doctor found: " + foundDoctor.getName());
        } else {
            System.out.println("Doctor not found.");
        }

        doctor removedDoctor = directory.removeDoctorById(2);
        if (removedDoctor != null) {
            System.out.println("Doctor removed: " + removedDoctor.getName());
        } else {
            System.out.println("Doctor not found for removal.");
        }

        doctor cardiologist = directory.getdoctorBySpecialization("Cardiology");
        if (cardiologist != null) {
            System.out.println("Cardiologist found: " + cardiologist.getName());
        } else {
            System.out.println("No cardiologist found.");
        }

    }
}