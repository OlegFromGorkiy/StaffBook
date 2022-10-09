public class Main {
    public static void main(String[] args) {
        //создаем базу сотрудников. При создании базы в нее добавляется 10 случайных сотрудников.
        EmployeeBook staffBook = EmployeeBook.getInstance();

        System.out.println("Список всех сотрудников:");
        staffBook.getViewStaff();
        delimiter();

        System.out.printf("В месяц компания тратит на зарплаты %.2f рублей.\n", staffBook.getMonthlyCosts());
        delimiter();

        System.out.println("Сотрудник с минимальной зарплатой: " + staffBook.getCheapest());
        delimiter();

        System.out.println("Сотрудник с максимальной зарплатой: " + staffBook.getMostExpensive());
        delimiter();

        System.out.printf("В среднем зарплата в компании составляет %.2f рублей.\n", staffBook.getAverageSalary());
        delimiter();

        System.out.println("ФИО всех сотрудников:");
        staffBook.getNames();
        delimiter();
        //Проиндексируем зарплату
        System.out.println("Проиндексируем зарплату всех сотрудников на 7.3%:");
        staffBook.raiseSalaries(7.3);
        System.out.printf("В месяц компания стала тратить на зарплаты %.2f рублей.\n",
                staffBook.getMonthlyCosts());
        delimiter();

        System.out.println("Сотрудник с минимальной зарплатой в отделе №3:");
        System.out.println(staffBook.getCheapest(3));
        delimiter();

        System.out.println("Сотрудник с максимальной зарплатой в отделе №3:");
        System.out.println(staffBook.getMostExpensive(5));
        delimiter();
        //Сумму затрат на зарплату по отделу.
        System.out.printf("В месяц на зарплаты в отделе №3 уходит %.2f рублей.\n",
                staffBook.getMonthlyCosts(3));
        delimiter();
        //Среднюю зарплату по отделу
        System.out.printf("В среднем зарплата в отделе №3 составляет %.2f рублей.\n",
                staffBook.getAverageSalary(3));
        delimiter();
        //Проиндексировать зарплату всех сотрудников отдела
        System.out.println("Проиндексируем зарплату всех сотрудников в отделе №3 на 7.3%:");
        staffBook.raiseSalaries(7.3,3);
        System.out.printf("В месяц на зарплаты в отделе №3 стало уходить %.2f рублей.\n",
                staffBook.getMonthlyCosts(3));
        delimiter();

        System.out.println("Список сотрудников отдела №3:");
        staffBook.getDepartmentInfo(3);
        delimiter();

        System.out.println("Список сотрудников с зарплатой меньше 50 000 рублей:");
        staffBook.getStaffCheaper(50_000);
        delimiter();

        System.out.println("Список сотрудников с зарплатой больше 50 000 рублей:");
        staffBook.getStaffExtensive(50_000);
        delimiter();

        Employee chapaev = new Employee("Чапаев Василий Иванович", 1, 125_000);
        Employee rasputin = new Employee("Распутин Гришка", 5, 10_000);
        Employee rogozin = new Employee("Рогозин батутный клоун", 5, 1_000_000_000);

        System.out.println("Попробуем добавить нового сотрудника:");
        staffBook.add(chapaev);
        System.out.println("\nУвеличим штат и попробуем снова!");
        staffBook.grow(1);
        staffBook.add(chapaev);
        staffBook.getViewStaff();
        System.out.println("\nКажется получилось! Теперь уволим самого бесполезного и дешевого и наймем эфективного менеджера!");
        staffBook.remove(staffBook.getCheapest().getID());
        staffBook.add(rogozin);
        staffBook.getViewStaff();

        System.out.println("\nКакой то он оказался странный. Лучше избавиться от него и наймем компанейского парня.");
        staffBook.remove("Рогозин батутный клоун");
        staffBook.add(rasputin);
        staffBook.getViewStaff();
        delimiter();
        System.out.println("Поднимем Грише зарплату и переведем в отдел к Чапаеву:");
        staffBook.setSalary("Распутин Гришка", 50_000);
        staffBook.setDepartment("Распутин Гришка",1);
        staffBook.getDepartmentInfo(1);
        delimiter();
        System.out.println("Список сотрудников отсортированный по отделам:");
        staffBook.sortedView();
        delimiter();
        System.out.println("Получить данные сотрудников по отделам:");
        staffBook.newSortedView();
        delimiter();
        System.out.println("Получить Ф.И.О. сотрудников по отделам:");
        staffBook.newestSortedView();
    }
    static void delimiter(){
        System.out.println("\n*** *** ***\n");
    }
}
