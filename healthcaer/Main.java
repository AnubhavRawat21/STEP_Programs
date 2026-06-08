package healthcaer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

class Patient {
    private String patientId;
    private String name;
    private int age;
    private String emergencyLevel;
    private LocalDateTime registrationDate;

    public Patient(String patientId, String name, int age, String emergencyLevel) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.emergencyLevel = emergencyLevel;
        this.registrationDate = LocalDateTime.now();
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmergencyLevel() {
        return emergencyLevel;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        return patientId + ": " + name + " (" + age + ")";
    }
}



class PatientRegistrationSystem {

    private ArrayList<Patient> patients;

    public PatientRegistrationSystem() {
        patients = new ArrayList<>();
    }

    // Register Patient
    public void registerPatient(Patient patient) {

        for (Patient p : patients) {
            if (p.getPatientId().equals(patient.getPatientId())) {
                System.out.println("✗ Duplicate! "
                        + patient.getPatientId()
                        + " already exists");
                return;
            }
        }

        patients.add(patient);

        System.out.println("✓ "
                + patient.getPatientId()
                + ": "
                + patient.getName()
                + " ("
                + patient.getAge()
                + ") registered");
    }

    // Linear Search
    public Patient findPatientById(String id) {

        for (Patient p : patients) {
            if (p.getPatientId().equals(id)) {
                return p;
            }
        }

        return null;
    }

    // Newest First
    public List<Patient> getPatientsByRegistrationOrder() {

        ArrayList<Patient> sortedList =
                new ArrayList<>(patients);

        sortedList.sort(
                Comparator.comparing(
                        Patient::getRegistrationDate)
                        .reversed());

        return sortedList;
    }

    // Remove by ID
    public boolean dischargePatient(String id) {

        Patient patient = findPatientById(id);

        if (patient != null) {
            patients.remove(patient);
            return true;
        }

        return false;
    }
}


class EmergencyQueue {

    private LinkedList<Patient> queue;

    public EmergencyQueue() {
        queue = new LinkedList<>();
    }

    // FIFO Insertion
    public void addEmergencyPatient(Patient patient) {
        queue.offer(patient);
    }

    // Poll and Remove
    public Patient getNextPatient() {
        return queue.poll();
    }

    // Current Size
    public int getWaitingCount() {
        return queue.size();
    }

    // View Front
    public Patient peekNextPatient() {
        return queue.peek();
    }
}

public class Main {

    public static void main(String[] args) {

        PatientRegistrationSystem prs =
                new PatientRegistrationSystem();

        EmergencyQueue emergencyQueue =
                new EmergencyQueue();

        Patient p1 =
                new Patient("PAT-001",
                        "John Smith",
                        45,
                        "Level 1 - Critical");

        Patient p2 =
                new Patient("PAT-002",
                        "Alice Brown",
                        32,
                        "Level 2 - Serious");

        Patient p3 =
                new Patient("PAT-001",
                        "Duplicate John",
                        50,
                        "Level 3");

        // Registration
        prs.registerPatient(p1);
        prs.registerPatient(p2);
        prs.registerPatient(p3);

        // Emergency Queue
        emergencyQueue.addEmergencyPatient(p1);
        emergencyQueue.addEmergencyPatient(p2);

        Patient next =
                emergencyQueue.peekNextPatient();

        System.out.println(
                "Next patient: "
                        + next.getName()
                        + " ("
                        + next.getEmergencyLevel()
                        + ")");

        emergencyQueue.getNextPatient();

        System.out.println(
                "Remaining in queue: "
                        + emergencyQueue.getWaitingCount());
    }
}