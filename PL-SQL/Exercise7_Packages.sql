CREATE OR REPLACE PACKAGE CustomerManagement AS
    PROCEDURE AddNewCustomer(
        p_customerid NUMBER,
        p_name VARCHAR2,
        p_dob DATE,
        p_balance NUMBER
    );

    PROCEDURE UpdateCustomerDetails(
        p_customerid NUMBER,
        p_name VARCHAR2,
        p_balance NUMBER
    );

    FUNCTION GetCustomerBalance(
        p_customerid NUMBER
    ) RETURN NUMBER;
END CustomerManagement;
/

CREATE OR REPLACE PACKAGE BODY CustomerManagement AS

    PROCEDURE AddNewCustomer(
        p_customerid NUMBER,
        p_name VARCHAR2,
        p_dob DATE,
        p_balance NUMBER
    ) IS
    BEGIN
        INSERT INTO Customers
        VALUES (p_customerid, p_name, p_dob, p_balance, SYSDATE, NULL);
        COMMIT;
    END;

    PROCEDURE UpdateCustomerDetails(
        p_customerid NUMBER,
        p_name VARCHAR2,
        p_balance NUMBER
    ) IS
    BEGIN
        UPDATE Customers
        SET Name = p_name,
            Balance = p_balance
        WHERE CustomerID = p_customerid;
        COMMIT;
    END;

    FUNCTION GetCustomerBalance(
        p_customerid NUMBER
    ) RETURN NUMBER IS
        v_balance NUMBER;
    BEGIN
        SELECT Balance
        INTO v_balance
        FROM Customers
        WHERE CustomerID = p_customerid;

        RETURN v_balance;
    END;

END CustomerManagement;
/

CREATE OR REPLACE PACKAGE EmployeeManagement AS
    PROCEDURE HireEmployee(
        p_employeeid NUMBER,
        p_name VARCHAR2,
        p_position VARCHAR2,
        p_salary NUMBER,
        p_department VARCHAR2,
        p_hiredate DATE
    );

    PROCEDURE UpdateEmployee(
        p_employeeid NUMBER,
        p_salary NUMBER
    );

    FUNCTION CalculateAnnualSalary(
        p_employeeid NUMBER
    ) RETURN NUMBER;
END EmployeeManagement;
/

CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS

    PROCEDURE HireEmployee(
        p_employeeid NUMBER,
        p_name VARCHAR2,
        p_position VARCHAR2,
        p_salary NUMBER,
        p_department VARCHAR2,
        p_hiredate DATE
    ) IS
    BEGIN
        INSERT INTO Employees
        VALUES (
            p_employeeid,
            p_name,
            p_position,
            p_salary,
            p_department,
            p_hiredate
        );
        COMMIT;
    END;

    PROCEDURE UpdateEmployee(
        p_employeeid NUMBER,
        p_salary NUMBER
    ) IS
    BEGIN
        UPDATE Employees
        SET Salary = p_salary
        WHERE EmployeeID = p_employeeid;
        COMMIT;
    END;

    FUNCTION CalculateAnnualSalary(
        p_employeeid NUMBER
    ) RETURN NUMBER IS
        v_salary NUMBER;
    BEGIN
        SELECT Salary
        INTO v_salary
        FROM Employees
        WHERE EmployeeID = p_employeeid;

        RETURN v_salary * 12;
    END;

END EmployeeManagement;
/

CREATE OR REPLACE PACKAGE AccountOperations AS
    PROCEDURE OpenAccount(
        p_accountid NUMBER,
        p_customerid NUMBER,
        p_accounttype VARCHAR2,
        p_balance NUMBER
    );

    PROCEDURE CloseAccount(
        p_accountid NUMBER
    );

    FUNCTION GetTotalBalance(
        p_customerid NUMBER
    ) RETURN NUMBER;
END AccountOperations;
/

CREATE OR REPLACE PACKAGE BODY AccountOperations AS

    PROCEDURE OpenAccount(
        p_accountid NUMBER,
        p_customerid NUMBER,
        p_accounttype VARCHAR2,
        p_balance NUMBER
    ) IS
    BEGIN
        INSERT INTO Accounts
        VALUES (
            p_accountid,
            p_customerid,
            p_accounttype,
            p_balance,
            SYSDATE
        );
        COMMIT;
    END;

    PROCEDURE CloseAccount(
        p_accountid NUMBER
    ) IS
    BEGIN
        DELETE FROM Accounts
        WHERE AccountID = p_accountid;
        COMMIT;
    END;

    FUNCTION GetTotalBalance(
        p_customerid NUMBER
    ) RETURN NUMBER IS
        v_total NUMBER;
    BEGIN
        SELECT SUM(Balance)
        INTO v_total
        FROM Accounts
        WHERE CustomerID = p_customerid;

        RETURN NVL(v_total,0);
    END;

END AccountOperations;
/

BEGIN
    CustomerManagement.AddNewCustomer(3,'David',TO_DATE('1995-05-20','YYYY-MM-DD'),12000);
END;
/

BEGIN
    EmployeeManagement.HireEmployee(3,'Tom','Analyst',50000,'Finance',SYSDATE);
END;
/

BEGIN
    AccountOperations.OpenAccount(3,1,'Savings',5000);
END;
/

SELECT CustomerManagement.GetCustomerBalance(1) AS Balance FROM DUAL;

SELECT EmployeeManagement.CalculateAnnualSalary(1) AS AnnualSalary FROM DUAL;

SELECT AccountOperations.GetTotalBalance(1) AS TotalBalance FROM DUAL;
/