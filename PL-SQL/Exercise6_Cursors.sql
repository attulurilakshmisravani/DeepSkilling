DECLARE
    CURSOR GenerateMonthlyStatements IS
        SELECT CustomerID, AccountID, TransactionDate, Amount, TransactionType
        FROM Transactions t
        JOIN Accounts a
        ON t.AccountID = a.AccountID
        WHERE EXTRACT(MONTH FROM TransactionDate) = EXTRACT(MONTH FROM SYSDATE)
        AND EXTRACT(YEAR FROM TransactionDate) = EXTRACT(YEAR FROM SYSDATE);

BEGIN
    FOR rec IN GenerateMonthlyStatements LOOP
        DBMS_OUTPUT.PUT_LINE(
            'Customer ID: ' || rec.CustomerID ||
            ', Account ID: ' || rec.AccountID ||
            ', Date: ' || TO_CHAR(rec.TransactionDate,'DD-MON-YYYY') ||
            ', Amount: ' || rec.Amount ||
            ', Type: ' || rec.TransactionType
        );
    END LOOP;
END;
/

DECLARE
    CURSOR ApplyAnnualFee IS
        SELECT AccountID
        FROM Accounts;

BEGIN
    FOR acc IN ApplyAnnualFee LOOP
        UPDATE Accounts
        SET Balance = Balance - 100
        WHERE AccountID = acc.AccountID;
    END LOOP;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Annual fee applied successfully.');
END;
/

DECLARE
    CURSOR UpdateLoanInterestRates IS
        SELECT LoanID, InterestRate
        FROM Loans;

BEGIN
    FOR loan IN UpdateLoanInterestRates LOOP
        UPDATE Loans
        SET InterestRate = InterestRate + 0.5
        WHERE LoanID = loan.LoanID;
    END LOOP;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Loan interest rates updated successfully.');
END;
/