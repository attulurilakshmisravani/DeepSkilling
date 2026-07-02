CREATE OR REPLACE FUNCTION CalculateAge (
    p_dob IN DATE
)
RETURN NUMBER
IS
    v_age NUMBER;
BEGIN
    v_age := FLOOR(MONTHS_BETWEEN(SYSDATE, p_dob) / 12);
    RETURN v_age;
END;
/

SELECT CalculateAge(TO_DATE('1985-05-15','YYYY-MM-DD')) AS Age
FROM DUAL;
/

CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment (
    p_loan_amount IN NUMBER,
    p_interest_rate IN NUMBER,
    p_years IN NUMBER
)
RETURN NUMBER
IS
    v_monthly_installment NUMBER;
BEGIN
    v_monthly_installment :=
    (p_loan_amount + (p_loan_amount * p_interest_rate * p_years / 100))
    / (p_years * 12);

    RETURN v_monthly_installment;
END;
/

SELECT CalculateMonthlyInstallment(5000,5,5) AS Monthly_Installment
FROM DUAL;
/

CREATE OR REPLACE FUNCTION HasSufficientBalance (
    p_account_id IN NUMBER,
    p_amount IN NUMBER
)
RETURN VARCHAR2
IS
    v_balance NUMBER;
BEGIN
    SELECT Balance
    INTO v_balance
    FROM Accounts
    WHERE AccountID = p_account_id;

    IF v_balance >= p_amount THEN
        RETURN 'TRUE';
    ELSE
        RETURN 'FALSE';
    END IF;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 'ACCOUNT NOT FOUND';
END;
/

SELECT HasSufficientBalance(1,500) AS Result
FROM DUAL;
/