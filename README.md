# BasketSplitter

This program is designed to efficiently split a list of items into baskets for different types of delivery. The main class `BasketSplitter` takes a configuration file path and uses a greedy algorithm to allocate items into baskets according to the specified delivery types.

## How to Use

1. **Configuration File**: Ensure you have a configuration file (`config.json`) that maps each item to its possible delivery types. Fill in the absolute path to this file in the `absolutePathToConfigFile` variable within `BasketSplitterTest` class.

2. **Testing**: The provided tests in `BasketSplitterTest` demonstrate how to use the `BasketSplitter` class. Simply run these tests to verify the functionality of the basket splitting algorithm.

3. **Customization**: If you wish to modify the behavior or add more delivery types, you can adjust the configuration file (`config.json`) accordingly.

4. **Integration**: This program can be integrated into larger systems for optimizing delivery processes based on item types and delivery preferences.

## Dependencies

- TestNG (`org.testng:testng:7.1.0`)
- JUnit BOM (`org.junit:junit-bom:5.9.1`)
- JUnit Jupiter (`org.junit.jupiter:junit-jupiter`)
- Jackson Databind (`com.fasterxml.jackson.core:jackson-databind:2.12.5`)

## Build

This project utilizes Gradle for building. Ensure you have Gradle installed, and then you can build the library using the following command:

```
./gradlew shadowJar
```

## Note

- Ensure the correctness of the configuration file and its mapping to items and delivery types for accurate basket splitting.
- Results may vary if different configuration files are used.