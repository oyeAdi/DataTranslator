# Data Translator

## Overview
The Data Translator application processes data files in `.tsv` format, translating columns and identifiers based on specified mappings. It processes large files in chunks using parallel processing.

## Configuration

1. **Create a `config.properties` file** in the root directory of your project with the following content:

    ```properties
    # Path to the data folder
    data.folder.path=./data

    # Path to the column mapping file
    column.mapping.file=./data/column_mapping.tsv

    # Path to the identifier mapping file
    identifier.mapping.file=./data/identifier_mapping.tsv

    # Chunk size for processing
    chunk.size=1000
    ```

2. **Ensure the data folder contains the following files**:
    - `column_mapping.tsv`: The column mapping configuration file.
    - `identifier_mapping.tsv`: The identifier mapping configuration file.
    - `dataFile0.tsv`, `dataFile1.tsv`, etc.: The data files to be processed.

## Build and Run Instructions

### Prerequisites
- Java 8 or higher
- Maven

### Build the Application
1. Navigate to the project root directory.
2. Run the following command to build the project:
    ```sh
    mvn clean install
    ```

### Run the Application
1. Navigate to the target directory:
    ```sh
    cd target
    ```
2. Run the application with the following command:
    ```sh
    java -jar DataTranslator-1.0-SNAPSHOT.jar
    ```
3.	Run the Test:
      •	You can run the test from your IDE or using Maven with the following command:
      ```sh
       mvn test
4. Packaging the Project
   - Ensure All Files Are Included: Verify that all source files, test files, configuration files, and documentation are present.
   - Create a Zip Archive:
         •	On Windows: Right-click the project folder and select “Send to > Compressed (zipped) folder”.
         •	On macOS/Linux: Use the zip command in the terminal:
   ```sh
       zip -r DataTranslator.zip DataTranslator/

## Logging and Error Handling
- The application uses `LoggerUtil` for logging information and errors.
- Logs are printed to the console for easy monitoring.
- Ensure that the log messages provide sufficient context for debugging.

## Code Structure
- `DataTranslatorApplication`: Main application class that loads configurations and initiates the translation process.
- `DataTranslator`: Handles the processing of data files in chunks using parallel processing.
- `DataFileProcessor`: Processes individual chunks of data.
- `DataFileWriter`: Writes the processed data to the output file.
- `ConfigurationLoader`: Loads column and identifier mappings from configuration files.
- `LoggerUtil`: Utility class for logging information and errors.

## Contributio
   - Aditya Raj 
   - Feel free to contribute to this project by submitting issues and pull requests.

## License
© 2024 aytidA.org License. All rights reserved.
This poc project is licensed under the aytidA.org License. You may not use this project except in compliance with the License.

You may obtain a copy of the License at:

    http://www.aytidA.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.