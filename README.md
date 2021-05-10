# Vending Machine

## How to Run

### 1. Clone the repo

```txt
git clone https://github.com/moody/vending_machine.git
```

```txt
cd vending_machine
```

### 2. Package with Maven

```txt
mvn clean package
```

### 3. Run

```txt
java -jar target\vending_machine-1.0-SNAPSHOT.jar
```

## Approach

My approach to solving this challenge was fairly simple. First, I made some
assumptions about the JSON input data:

- `config.rows` will be between 1 and 26 (A-Z)
- `config.columns` will be greater than 0
- `items` array will be mapped to IDs (e.g. "A1"-"Zn") in the order they appear
- Length of `items` will not exceed `config.rows * config.columns`

With this in mind, I created two simple data structures for storing the JSON data: `VendingItem` and `VendingMachine`.

- `VendingItem` provides fields for the `name`, `amount`, and `price` of an item
- `VendingMachine` provides fields for `rows`, `columns`, and a list of `items`

At this point, I began to write two unit tests for `VendingMachine`:

- `fromJson(String json)` - initializes an instance of `VendingMachine` from a JSON string
- `getItem(String id)` - retrieves an item by ID (e.g. "A1") from the underlying `items` list

I felt that if I could get these tests to pass, then the biggest challenges
would be solved: loading from JSON, and mapping array indexes to alphanumeric
IDs.

Once I got these unit tests to pass, I started to work on a command-line
interface for interacting with the machine. Once that was done, I created
an `AuditLogger` class for logging important actions to a file, and called upon
it during those actions.

At this point, I believe all of the requirements for the challenge are met;
however, there are some details left unexplored, such as the machine's funds
when dispensing change.

## Example Output

```txt
-- Vending Machine

+---------+----------------------------+
| Command | Description                |
+---------+----------------------------+
| load, l | load data from a JSON file |
| view, v | view available items       |
|  buy, b | buy an item                |
| help, h | display this message       |
| quit, q | exit the program           |
+---------+----------------------------+

Enter a command: load input.json

Data loaded successfully.

+----+-----------------------+----------+-------+
| ID | Name                  | In Stock | Price |
+----+-----------------------+----------+-------+
| A1 | Snickers              |    10    | $1.35 |
| A2 | Hersheys              |    10    | $2.25 |
| A3 | Hersheys Almond       |    10    | $1.80 |
| A4 | Hersheys Special Dark |    10    | $1.75 |
| A5 | Reese's               |    10    | $1.05 |
| A6 | Nutrageous            |    10    | $1.30 |
| A7 | Baby Ruth             |    10    | $2.50 |
| A8 | Milky Way             |    10    | $1.00 |
| B1 | M&M                   |    10    | $1.25 |
+----+-----------------------+----------+-------+

Enter a command: buy a1

+----+----------+-------+
| ID | Name     | Price |
+----+----------+-------+
| A1 | Snickers | $1.35 |
+----+----------+-------+

Continue buying this item? (y/N): y
Enter payment (USD): 1.35

Thank you for your purchase.

+-------+-------------+------------+
| Total | Amount Paid | Change Due |
+-------+-------------+------------+
| $1.35 |       $1.35 |      $0.00 |
+-------+-------------+------------+

Dispensing Snickers...

Enter a command: q

Shutting down...
```
