public class EmployeeManagementSystem {

    Employee[] employees;
    int count;

    public EmployeeManagementSystem(int size) {
        employees = new Employee[size];
        count = 0;
    }

    // Add Employee
    public void addEmployee(Employee emp) {
        if (count < employees.length) {
            employees[count++] = emp;
            System.out.println("Employee Added Successfully");
        } else {
            System.out.println("Array is Full");
        }
    }

    // Search Employee
    public Employee searchEmployee(int employeeId) {
        for (int i = 0; i < count; i++) {
            if (employees[i].employeeId == employeeId) {
                return employees[i];
            }
        }
        return null;
    }

    // Traverse Employees
    public void traverseEmployees() {
        for (int i = 0; i < count; i++) {
            System.out.println(employees[i]);
        }
    }

    // Delete Employee
    public void deleteEmployee(int employeeId) {
        int index = -1;

        for (int i = 0; i < count; i++) {
            if (employees[i].employeeId == employeeId) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            for (int i = index; i < count - 1; i++) {
                employees[i] = employees[i + 1];
            }

            employees[count - 1] = null;
            count--;

            System.out.println("Employee Deleted Successfully");
        } else {
            System.out.println("Employee Not Found");
        }
    }

    public static void main(String[] args) {

        EmployeeManagementSystem ems =
                new EmployeeManagementSystem(10);

        ems.addEmployee(new Employee(101,
                "Sravani", "Developer", 50000));

        ems.addEmployee(new Employee(102,
                "Ravi", "Tester", 40000));

        ems.addEmployee(new Employee(103,
                "Priya", "Manager", 70000));

        System.out.println("\nEmployee Records:");
        ems.traverseEmployees();

        System.out.println("\nSearching Employee ID 102:");
        System.out.println(ems.searchEmployee(102));

        ems.deleteEmployee(102);

        System.out.println("\nAfter Deletion:");
        ems.traverseEmployees();
    }
}