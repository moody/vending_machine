# Vending Machine

## How to Run

### 1. Clone the repo

```cmd
git clone <link>
```

```cmd
cd vending_machine
```

### 2. Package with Maven

```cmd
mvn clean package
```

### 3. Run

```cmd
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
interface for interacting with the machine. Finally, I added an `AuditLogger`
class for logging important actions to a file, and called upon it during those
actions.
