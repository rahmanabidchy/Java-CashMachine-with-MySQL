# CashMachine
An example cash machine &lt;-> bank system implementation

## Task Brief
User can do the following tasks with the CashMachine
* Choose an account owned by the user
* Check summary of that account
* Witdraw money from account ( Overdraw if account type is checking)
* Deposit money
* Transfer money between accounts
* Write a memo of every transaction

## Database info
### User
* Each user has: id, name, email

### Account
* 2 Accounts: Checking, Saving
* Each account has: id, holder_id, account_Type

### Traansaction
* Each transaction has: account_id, amount, memo, date 
