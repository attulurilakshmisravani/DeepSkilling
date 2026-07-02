DECLARE
    v_age NUMBER;
BEGIN
    FOR cust IN (SELECT CustomerID, DOB FROM Customers) LOOP

        -- Calculate customer's age
        v_age := FLOOR(MONTHS_BETWEEN(SYSDATE, cust.DOB) / 12);

        -- If age is greater than 60, reduce interest rate by 1%
        IF v_age > 60 THEN
            UPDATE Loans
            SET InterestRate = InterestRate - 1
            WHERE CustomerID = cust.CustomerID;

            DBMS_OUTPUT.PUT_LINE('Interest rate updated for Customer ID: ' || cust.CustomerID);
        END IF;

    END LOOP;

    COMMIT;
END;
/

ALTER TABLE Customers
ADD IsVIP CHAR(1);

BEGIN
    FOR cust IN (SELECT CustomerID, Balance FROM Customers) LOOP

        IF cust.Balance > 10000 THEN
            UPDATE Customers
            SET IsVIP = 'Y'
            WHERE CustomerID = cust.CustomerID;

            DBMS_OUTPUT.PUT_LINE('Customer ' || cust.CustomerID || ' promoted to VIP');
        END IF;

    END LOOP;

    COMMIT;
END;
/

BEGIN
    FOR loan IN (
        SELECT LoanID, CustomerID, EndDate
        FROM Loans
        WHERE EndDate BETWEEN SYSDATE AND SYSDATE + 30
    ) LOOP

        DBMS_OUTPUT.PUT_LINE(
            'Reminder: Loan ID ' || loan.LoanID ||
            ' for Customer ID ' || loan.CustomerID ||
            ' is due on ' || TO_CHAR(loan.EndDate, 'DD-MON-YYYY')
        );

    END LOOP;
END;
/