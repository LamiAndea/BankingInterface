# BankingInterface

This program is a simple banking system that allows users to create accounts, manage their balances, and perform transactions such as deposits and withdrawals. It uses object-oriented principles to handle customer data and banking operations efficiently.

Key Functionalities

Account Management:
Users can create accounts by providing their first name, last name, and a password.
Each account is assigned a unique 16-digit account number.

Transactions:
Users can withdraw money, deposit funds, or check their account balance.
The system validates transactions, ensuring sufficient balance for withdrawals.
Customer Data Persistence:

Customer data, including account details and balances, is stored using serialization.
The program saves data to a file (customerData.ser) upon exit and reloads it when the program starts, ensuring all customer data is retained across sessions.
Error Handling:

The program gracefully handles scenarios such as insufficient funds, corrupted data files, and missing data.
This project demonstrates the integration of core Java concepts such as file I/O, serialization, error handling, and object-oriented programming in building a functional banking system.
