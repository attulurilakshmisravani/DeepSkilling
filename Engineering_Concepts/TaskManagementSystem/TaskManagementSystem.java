public class TaskManagementSystem {

    // Node class
    class Node {
        Task task;
        Node next;

        Node(Task task) {
            this.task = task;
            this.next = null;
        }
    }

    Node head;

    // Add Task
    public void addTask(Task task) {
        Node newNode = new Node(task);

        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;

            while (temp.next != null) {
                temp = temp.next;
            }

            temp.next = newNode;
        }

        System.out.println("Task Added Successfully");
    }

    // Search Task
    public Task searchTask(int taskId) {
        Node temp = head;

        while (temp != null) {
            if (temp.task.taskId == taskId) {
                return temp.task;
            }
            temp = temp.next;
        }

        return null;
    }

    // Traverse Tasks
    public void traverseTasks() {
        Node temp = head;

        while (temp != null) {
            System.out.println(
                "Task ID: " + temp.task.taskId +
                ", Task Name: " + temp.task.taskName +
                ", Status: " + temp.task.status
            );
            temp = temp.next;
        }
    }

    // Delete Task
    public void deleteTask(int taskId) {

        if (head == null)
            return;

        if (head.task.taskId == taskId) {
            head = head.next;
            System.out.println("Task Deleted Successfully");
            return;
        }

        Node temp = head;

        while (temp.next != null &&
               temp.next.task.taskId != taskId) {
            temp = temp.next;
        }

        if (temp.next != null) {
            temp.next = temp.next.next;
            System.out.println("Task Deleted Successfully");
        } else {
            System.out.println("Task Not Found");
        }
    }

    public static void main(String[] args) {

        TaskManagementSystem tms =
                new TaskManagementSystem();

        tms.addTask(new Task(101,
                "Design Module", "Pending"));

        tms.addTask(new Task(102,
                "Coding Module", "In Progress"));

        tms.addTask(new Task(103,
                "Testing Module", "Completed"));

        System.out.println("\nTask List:");
        tms.traverseTasks();

        System.out.println("\nSearching Task ID 102:");
        Task task = tms.searchTask(102);

        if (task != null) {
            System.out.println(
                "Task ID: " + task.taskId +
                ", Task Name: " + task.taskName +
                ", Status: " + task.status
            );
        }

        tms.deleteTask(102);

        System.out.println("\nAfter Deletion:");
        tms.traverseTasks();
    }
}