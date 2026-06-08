import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Patient {
    private String patientId;
    private String name;
    private int age;
    private LocalDateTime registrationDate;

    public Patient(String patientId, String name, int age) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
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

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        return "Patient ID: " + patientId +
                ", Name: " + name +
                ", Age: " + age +
                ", Registered: " + registrationDate;
    }
}

class HospitalManagementSystem {

    // 1.1 Patient Registration
    private ArrayList<Patient> patients;

    // 1.2 Emergency Queue
    private Queue<Patient> emergencyQueue;

    public HospitalManagementSystem() {
        patients = new ArrayList<>();
        emergencyQueue = new LinkedList<>();
    }

    public boolean registerPatient(Patient patient) {

        for (Patient p : patients) {
            if (p.getPatientId().equals(patient.getPatientId())) {
                System.out.println("Duplicate Patient ID found!");
                return false;
            }
        }

        patients.add(patient);
        System.out.println("Patient Registered Successfully.");
        return true;
    }

    // =====================================
    // findPatientById()
    // Linear Search
    // =====================================
    public Patient findPatientById(String id) {

        for (Patient p : patients) {
            if (p.getPatientId().equals(id)) {
                return p;
            }
        }

        return null;
    }

    // =====================================
    // getPatientsByRegistrationOrder()
    // Newest First
    // =====================================
    public List<Patient> getPatientsByRegistrationOrder() {

        List<Patient> sortedPatients = new ArrayList<>(patients);

        sortedPatients.sort(
                Comparator.comparing(Patient::getRegistrationDate)
                          .reversed());

        return sortedPatients;
    }

    // =====================================
    // dischargePatient()
    // Remove Patient by ID
    // =====================================
    public boolean dischargePatient(String id) {

        for (Patient p : patients) {

            if (p.getPatientId().equals(id)) {
                patients.remove(p);
                System.out.println("Patient Discharged.");
                return true;
            }
        }

        System.out.println("Patient Not Found.");
        return false;
    }

    // =====================================
    // addEmergencyPatient()
    // FIFO Insertion
    // =====================================
    public void addEmergencyPatient(Patient patient) {

        emergencyQueue.offer(patient);

        System.out.println(
                patient.getName() +
                " added to Emergency Queue.");
    }

    // =====================================
    // getNextPatient()
    // Poll and Remove
    // =====================================
    public Patient getNextPatient() {

        return emergencyQueue.poll();
    }

    // =====================================
    // getWaitingCount()
    // =====================================
    public int getWaitingCount() {

        return emergencyQueue.size();
    }

    // =====================================
    // peekNextPatient()
    // =====================================
    public Patient peekNextPatient() {

        return emergencyQueue.peek();
    }

    // =====================================
    // Display All Patients
    // =====================================
    public void displayAllPatients() {

        if (patients.isEmpty()) {
            System.out.println("No Patients Registered.");
            return;
        }

        for (Patient p : patients) {
            System.out.println(p);
        }
    }
}

// ==========================
// Main Class
// ==========================
public class PatientRegistrationSystem {

    public static void main(String[] args) {

        HospitalManagementSystem hospital =
                new HospitalManagementSystem();

        // Register Patients
        Patient p1 = new Patient("P101", "Anubhav", 21);
        Patient p2 = new Patient("P102", "Ayushman", 22);
        Patient p3 = new Patient("P103", "Dominic", 23);

        hospital.registerPatient(p1);
        hospital.registerPatient(p2);
        hospital.registerPatient(p3);

        // Duplicate ID Test
        Patient duplicate =
                new Patient("P101", "Test", 30);

        hospital.registerPatient(duplicate);

        System.out.println("\n----- All Patients -----");
        hospital.displayAllPatients();

        // Search
        System.out.println("\n----- Search Patient -----");
        Patient found =
                hospital.findPatientById("P102");

        if (found != null) {
            System.out.println(found);
        } else {
            System.out.println("Patient Not Found.");
        }

        // Emergency Queue
        System.out.println("\n----- Emergency Queue -----");

        hospital.addEmergencyPatient(p1);
        hospital.addEmergencyPatient(p2);
        hospital.addEmergencyPatient(p3);

        System.out.println(
                "Waiting Count: "
                        + hospital.getWaitingCount());

        System.out.println(
                "Next Patient: "
                        + hospital.peekNextPatient());

        System.out.println(
                "Treating Patient: "
                        + hospital.getNextPatient());

        System.out.println(
                "Waiting Count After Treatment: "
                        + hospital.getWaitingCount());

        // Registration Order
        System.out.println(
                "\n----- Registration Order (Newest First) -----");

        List<Patient> sorted =
                hospital.getPatientsByRegistrationOrder();

        for (Patient p : sorted) {
            System.out.println(p);
        }

        // Discharge
        System.out.println("\n----- Discharge -----");
        hospital.dischargePatient("P102");

        System.out.println("\nRemaining Patients:");
        hospital.displayAllPatients();
    }
}