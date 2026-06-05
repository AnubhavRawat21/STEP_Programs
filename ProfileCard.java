public class ProfileCard {public static void main(String[] args) {String name = "Your Name Here";int age = 0;double height = 0.0;
char bloodGroup = 'O';boolean isStudent = true;System.out.println("===== PROFILE CARD =====");System.out.println("Name: " + name);System.out.println("Age: " + age);System.out.println("Height: " + height);System.out.println("Blood: " + bloodGroup);System.out.println("Student:" + isStudent);// CHALLENGE: Calculate birth yearint currentYear = 2026;
int birthYear = currentYear - age;
System.out.println("Born: " + birthYear);   
}
}