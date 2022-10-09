import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class EmployeeBook {
    private static EmployeeBook instance;
    private static Employee[] staffBook;
    private static String[] names = {"Акакий", "Бонифаций", "Герасим", "Исидор", "Никифор", "Христофор"};
    private static String[] surnames = {"Воронцов", "Лукин", "Герасимов", "Ширяев", "Никифоров", "Христофоров"};
    private static String[] patronymics = {"Акакиевич", "Валерьянович", "Герасимович", "Исидорович", "Никифорович", "Магомедович"};

    private EmployeeBook() {
        generateBook();
    }

    private void generateBook() {
        //Генерирует случайных сотрудников, чтобы не заполнять ручками.
        Random rnd = new Random();
        staffBook = new Employee[10];
        for (int i = 0; i < staffBook.length; i++) {
            staffBook[i] = new Employee(names[rnd.nextInt(names.length)],
                    surnames[rnd.nextInt(surnames.length)],
                    patronymics[rnd.nextInt(patronymics.length)],
                    (rnd.nextInt(5) + 1), ((1 + rnd.nextInt(10)) * 10_000));
        }
    }

    public void grow(int i) {
        //Увеличить штат на i человек
        staffBook = Arrays.copyOf(staffBook, staffBook.length + i);
    }

    public static EmployeeBook getInstance() {
        if (instance == null) {
            instance = new EmployeeBook();
        }
        return instance;
    }

    public void getViewStaff() {
        //Получить список всех сотрудников со всеми имеющимися по ним данными
        for (Employee employee : staffBook) {
            System.out.println(employee.toString());
        }
    }

    public double getMonthlyCosts() {
        //Посчитать сумму затрат на зарплаты в месяц.
        double result = 0;
        for (Employee employee : staffBook) {
            result += employee.getSalary();
        }
        return result;
    }

    public double getMonthlyCosts(int departmentNumber) {
        //Посчитать сумму затрат в отделе на зарплаты в месяц.
        double result = 0;
        Employee[] department = getDepartment(departmentNumber);
        if (department.length == 0) {
            System.out.println("Отдел пуст");
            return 0;
        }
        for (Employee employee : department) {
            result += employee.getSalary();
        }
        return result;
    }

    public Employee getCheapest() {
        //Найти сотрудника с минимальной зарплатой.
        Employee result = staffBook[0];
        for (int i = 1; i < staffBook.length; i++) {
            if (result.getSalary() >= staffBook[i].getSalary()) result = staffBook[i];
        }
        return result;
    }

    public Employee getCheapest(int departmentNumber) {
        //Найти сотрудника с минимальной зарплатой в отделе.
        Employee[] department = getDepartment(departmentNumber);
        if (department.length == 0) {
            System.out.println("Отдел пуст");
            return null;
        }
        Employee result = department[0];
        if (department.length > 1) {
            for (int i = 1; i < department.length; i++) {
                if (result.getSalary() >= department[i].getSalary()) result = department[i];
            }
        }
        return result;
    }

    public Employee getMostExpensive() {
        //Найти сотрудника с максимальной зарплатой.
        Employee result = staffBook[0];
        for (int i = 1; i < staffBook.length; i++) {
            if (result.getSalary() <= staffBook[i].getSalary()) result = staffBook[i];
        }
        return result;
    }

    public Employee getMostExpensive(int departmentNumber) {
        //Найти сотрудника с максимальной зарплатой в отделе.
        Employee[] department = getDepartment(departmentNumber);
        if (department.length == 0) {
            System.out.println("Отдел пуст");
            return null;
        }
        Employee result = department[0];
        for (int i = 1; i < department.length; i++) {
            if (result.getSalary() <= department[i].getSalary()) result = department[i];
        }
        return result;
    }

    public double getAverageSalary() {
        //Подсчитать среднее значение зарплат
        double result = getMonthlyCosts();
        int amount = (int) Arrays.stream(staffBook).filter(Objects::nonNull).count();
        return result / amount;
    }

    public double getAverageSalary(int departmentNumber) {
        //Подсчитать среднее значение зарплат в отделе.
        double result = getMonthlyCosts(departmentNumber);
        int amount = (int) Arrays.stream(staffBook)
                .filter(e -> e.getDepartmentNumber() == departmentNumber)
                .count();
        return result / amount;
    }

    public void getNames() {
        //Получить Ф. И. О. всех сотрудников (вывести в консоль).
        for (Employee employee : staffBook) {
            if (employee.getPatronymic().equals("unknown")) System.out.printf("%s %s \n",
                    employee.getSurname(), employee.getName());
            else System.out.printf("%s %s %s\n", employee.getSurname(),
                    employee.getName(), employee.getPatronymic());
        }
    }

    private void getNames(int departmentNumber) {
        Employee[] department = getDepartment(departmentNumber);
        if (department.length == 0) {
            System.out.println("Отдел пуст.");
            return;
        }
        for (Employee employee : department) {
            if (employee.getPatronymic().equals("unknown")) System.out.printf("%s %s \n",
                    employee.getSurname(), employee.getName());
            else System.out.printf("%s %s %s\n", employee.getSurname(),
                    employee.getName(), employee.getPatronymic());
        }
    }

    public void raiseSalaries(double percent) {
        //вызвать изменение зарплат у всех сотрудников на величину аргумента в %
        for (Employee employee : staffBook) {
            employee.setSalary(employee.getSalary() * (1 + percent / 100));
        }
    }

    public void raiseSalaries(double percent, int departmentNumber) {
        //вызвать изменение зарплат у всех сотрудников в отделе на величину аргумента в %
        Employee[] department = getDepartment(departmentNumber);
        for (Employee employee : department) {
            employee.setSalary(employee.getSalary() * (1 + percent / 100));
        }
    }

    private Employee[] getDepartment(int departmentNumber) {
        //восвращает массив с сотрудниками одного отдела
        return Arrays.stream(staffBook)
                .filter(e -> e.getDepartmentNumber() == departmentNumber)
                .toArray(Employee[]::new);
    }

    public void getDepartmentInfo(int departmentNumber) {
        //Напечатать всех сотрудников отдела (все данные, кроме отдела).
        Employee[] department = getDepartment(departmentNumber);
        if (department.length == 0) {
            System.out.println("Отдел пуст.");
            return;
        }
        for (Employee e : department) {
            if (e.getPatronymic().equals("unknown")) System.out.printf("%s %s зарплата %.0f рублей\n",
                    e.getSurname(), e.getName(), e.getSalary());
            else System.out.printf("%s %s %s зарплата %.0f рублей\n", e.getSurname(), e.getName(),
                    e.getPatronymic(), e.getSalary());
        }
    }

    public void getStaffCheaper(int salary) {
        //Найти всех сотрудников с зарплатой меньше числа
        Employee[] array = Arrays.stream(staffBook)
                .filter(e -> e.getSalary() < (double) salary)
                .toArray(Employee[]::new); //как цикл ниже вписать тут??? (.forEach(System.out::printf(?????))
        for (Employee e : array) {
            System.out.printf("id%d, %s %s %s зарплата %.0f рублей.\n", e.getID(), e.getSurname(),
                    e.getName(), e.getPatronymic(), e.getSalary());
        }
    }

    public void getStaffExtensive(int salary) {
        //Найти всех сотрудников с зарплатой больше числа
        Employee[] array = Arrays.stream(staffBook)
                .filter(e -> e.getSalary() >= (double) salary)
                .toArray(Employee[]::new); //как цикл ниже вписать тут??? (.forEach(System.out::printf(?????))
        for (Employee e : array) {
            System.out.printf("id %d, %s %s %s зарплата %.0f рублей.\n", e.getID(), e.getSurname(),
                    e.getName(), e.getPatronymic(), e.getSalary());
        }
    }

    private int search(long id) {
        for (int i = 0; i < staffBook.length; i++) {
            if (staffBook[i].getID() == id) return i;
        }
        return -1;
    }

    private int search(String fullName) {
        String[] array = fullName.split("\\s");
        if (array.length == 2) {
            array = Arrays.copyOf(array, array.length + 1);
            array[2] = "unknown";
        }
        for (int i = 0; i < staffBook.length; i++) {
            boolean b1 = array[0].equalsIgnoreCase(staffBook[i].getSurname());
            boolean b2 = array[1].equalsIgnoreCase(staffBook[i].getName());
            boolean b3 = array[2].equalsIgnoreCase(staffBook[i].getPatronymic());
            if (b1 && b2 && b3) return i;
        }
        return -1;
    }

    public void remove(long id) {
        //удалить сотрудника по id
        int index = search(id);
        if (index < 0) {
            System.out.println("Сотрудник не найден!");
            return;
        }
        staffBook[index] = null;
    }

    public void remove(String fullName) {
        //удалить сотрудника по Ф.И.О.
        int index = search(fullName);
        if (index < 0) {
            System.out.println("Сотрудник не найден!");
            return;
        }
        staffBook[index] = null;
    }

    public void add(Employee e) {
        int index = -1;
        for (int i = 0; i < staffBook.length; i++) {
            if (staffBook[i] == null) {
                index = i;
                break;
            }
        }
        if (index < 0) {
            System.out.println("Штат полон! Расширьте штат или увольте сотрудника.");
            return;
        }
        staffBook[index] = e;
    }

    public void setSalary(String fullName, double salary) {
        //назначить сотруднику зарплату
        int index = search(fullName);
        if (index < 0) {
            System.out.println("Сотрудник не найден!");
            return;
        }
        staffBook[index].setSalary(salary);
    }

    public void setDepartment(String fullName, int departmentNumber) {
        //назначить сотруднику отдел
        int index = search(fullName);
        if (index < 0) {
            System.out.println("Сотрудник не найден!");
            return;
        }
        staffBook[index].setDepartmentNumber(departmentNumber);
    }

    //Я не понял что надо вывести в последнем задании очень сложного уровня, по этому три варианта:
    public void sortedView() {
        Arrays.sort(staffBook);
        getViewStaff();
    }

    public void newSortedView() {
        for (int i = 1; i <= 5; i++) {
            System.out.printf("Отдел №%d:\n", i);
            getDepartmentInfo(i);
        }
    }

    public void newestSortedView() {
        for (int i = 1; i <= 5; i++) {
            System.out.printf("Отдел №%d:\n", i);
            getNames(i);
        }
    }
}
