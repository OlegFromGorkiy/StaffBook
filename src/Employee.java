

public class Employee implements Comparable<Employee> {
    private static long count = 123;

    private String name;
    private String surname;
    private String patronymic;
    private int departmentNumber;
    private double salary;
    private final long ID;

    public Employee(String name, String surname, String patronymic, int departmentNumber, double salary) {
        //if (count == null)
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.departmentNumber = departmentNumber;
        this.salary = salary;
        ID = count;
        count++;
    }

    public Employee(String name, String surname, int departmentNumber, double salary) {
        //зачем я это делал??? А чтоб заебався...
        this.name = name;
        this.surname = surname;
        this.patronymic = "unknown";
        this.departmentNumber = departmentNumber;
        this.salary = salary;
        ID = count;
        count++;
    }

    public Employee(String fullName, int departmentNumber, double salary) {
        String[] args = fullName.split("\\h");
        if (args.length < 2 || args.length > 3) throw new RuntimeException("Error name");
        name = args[1];
        surname = args[0];
        patronymic = (args.length == 2) ? "unknown" : args[2];
        this.departmentNumber = departmentNumber;
        this.salary = salary;
        ID = count;
        count++;
    }

    @Override
    public int hashCode() {
        return 31 * (int) ID + 113;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Employee employee = (Employee) obj;
        return this.getID() == employee.getID();
    }

    @Override
    public String toString() {
        String template1 = "id%6$d %2$s %1$s %3$s отдел № %4$d. Заработная плата - %5$.0f рублей";
        String template2 = "id%5$d %2$s %1$s отдел № %3$d. Заработная плата - %4$.0f рублей";
        if (this.getPatronymic().equals("unknown"))
            return String.format(template2, this.getName(), this.getSurname(), this.getDepartmentNumber(),
                    this.getSalary(), this.getID());
        else return String.format(template1, this.getName(), this.getSurname(),
                this.getPatronymic(), this.getDepartmentNumber(), this.getSalary(), this.getID());
    }

    public void setDepartmentNumber(int departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public int getDepartmentNumber() {
        return departmentNumber;
    }

    public double getSalary() {
        return salary;
    }

    public long getID() {
        return ID;
    }

    @Override
    public int compareTo(Employee e) {
        int i = this.departmentNumber - e.getDepartmentNumber();
        if (i == 0) i = this.surname.compareTo(e.getSurname());
        if (i == 0) i = this.name.compareTo(e.getName());
        if (i == 0) i = this.patronymic.compareTo(e.patronymic);
        if (i == 0) i = (int) (this.salary - e.getSalary());
        return i;
    }
}
