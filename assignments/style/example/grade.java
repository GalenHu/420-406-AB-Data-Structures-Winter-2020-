

/*
* Represent a student grade
 */
public class Grade implements CompareTo<Grade>  {
    private long id;
    private double grade;
    private String firstName;
    private String lastName;
    /*
    * Parse a grade from the input line
    * @param line the string representatopm f this grad in this format:ID, FIRSTNAME,LASTNAME
     */
    Grade(String line) {
        //Split the line using he comma delimiter
        //tmp store each part
        String[] s = line.split(",");
        id = Long.parseLong(s[0]);
        String[] s2 = s[1].split("_");
        firstName = s2[0];
        lastName = s2[1];
        grade = Double.parseDouble(s[2]);
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int compareTo(Grade o) {
        return (int) (grade - o.grade);
    }

    @Override
    public String toString() {
        return String.format("%d %s %s %f", id, firstName, lastName, grade);
    }
}
