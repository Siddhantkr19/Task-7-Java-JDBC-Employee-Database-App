import java.sql.*;
import java.util.Scanner;

public class EmployeeCrudApp {

    // ⬇️ I kept your specific password here
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/employee_db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Siddhant@123"; 

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("----------------------------------------------");
        System.out.println("-- Welcome to the Employee CRUD App --");
        System.out.println("----------------------------------------------");

        // 1. Load Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL JDBC Driver not found! Did you add the .jar to your classpath?");
            return;
        }

        // 2. Menu Loop
        while (true) {
            showMenu();
            int choice = -1;
            try {
                // We use nextLine() and parseInt to avoid scanner skipping issues
                String input = scanner.nextLine();
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    deleteEmployee();
                    break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    System.out.println("Thanks for using Employee Crud App. Goodbye! 👋");
                    System.exit(0); // This command stops the program
                    break;
                default:
                    System.out.println("⚠️ Invalid input. Please enter 1-5.");
            }
        }
    }

    // --- Helper Methods ---

    public static void showMenu() {
        System.out.println("\nSelect an Option:");
        System.out.println("1 : Add employee");
        System.out.println("2 : View employees");
        System.out.println("3 : Delete employee");
        System.out.println("4 : Update employee");
        System.out.println("5 : Exit");
        System.out.print("Enter Number: ");
    }

    public static void addEmployee() {
        System.out.print("Enter Employee Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Department: ");
        String dept = scanner.nextLine();
        System.out.print("Enter Salary: ");
        
        double salary = 0;
        try {
            salary = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
             System.out.println("❌ Invalid salary format.");
             return;
        }

        String sql = "INSERT INTO employees (name, department, salary) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement insertStmt = conn.prepareStatement(sql)) {
            
            insertStmt.setString(1, name);
            insertStmt.setString(2, dept);
            insertStmt.setDouble(3, salary);
            
            int rows = insertStmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Employee inserted successfully");
            
        } catch (SQLException e) {
            System.err.println("❌ Database error: " + e.getMessage());
        }
    }

    public static void viewEmployees() {
        String sql = "SELECT * FROM employees";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- 📋 Employee List ---");
            System.out.printf("%-5s %-20s %-15s %-10s%n", "ID", "Name", "Department", "Salary");
            System.out.println("-------------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String dept = rs.getString("department");
                double salary = rs.getDouble("salary");
                System.out.printf("%-5d %-20s %-15s $%-10.2f%n", id, name, dept, salary);
            }

            if (!hasData) {
                System.out.println("(No employees found in database)");
            }

        } catch (SQLException e) {
            System.err.println("❌ Error retrieving employees: " + e.getMessage());
        }
    }

    public static void deleteEmployee() {
        System.out.print("Enter the Employee Id to DELETE: ");
        int id = -1;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID.");
            return;
        }

        String sql = "DELETE FROM employees WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Employee deleted successfully!");
            } else {
                System.out.println("⚠️ Employee ID not found.");
            }

        } catch (SQLException e) {
            System.err.println("❌ Error deleting employee: " + e.getMessage());
        }
    }

    private static void updateEmployee() {
        System.out.print("Enter Employee ID to update: ");
        int id = -1;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
             System.out.println("❌ Invalid ID.");
             return;
        }

        System.out.print("Enter New Salary: ");
        double newSalary = 0;
        try {
            newSalary = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
             System.out.println("❌ Invalid Salary.");
             return;
        }

        String sql = "UPDATE employees SET salary = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newSalary);
            pstmt.setInt(2, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Employee updated successfully!");
            } else {
                System.out.println("⚠️ Employee ID not found.");
            }

        } catch (SQLException e) {
            System.err.println("❌ Error updating employee: " + e.getMessage());
        }
    }
}