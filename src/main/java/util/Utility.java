package util;

import database.DatabaseConnection;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utility {

    // TODO: Read Data From Excel Sheet
    public static String getExcelData(int RowNum, int ColNum, String SheetName) {
        XSSFWorkbook workBook;
        XSSFSheet sheet;
        String projectPath = System.getProperty("user.dir");
        String cellData = null;
        try {
            workBook = new XSSFWorkbook(projectPath + "/src/test/resources/data_driven/data.xlsx");
            sheet = workBook.getSheet(SheetName);
            cellData = sheet.getRow(RowNum).getCell(ColNum).getStringCellValue();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        return cellData;
    }

    public static String[][] readExcelData(String SheetName) {
        XSSFWorkbook workBook;
        XSSFSheet sheet;
        String projectPath = System.getProperty("user.dir");
        String[][] excelData = null;
        try {
            workBook = new XSSFWorkbook(projectPath + "/src/test/resources/data_driven/data.xlsx");
            sheet = workBook.getSheet(SheetName);
            int numberOfRows = sheet.getPhysicalNumberOfRows();
            int numberOfColumns = sheet.getRow(0).getLastCellNum();

            excelData = new String[numberOfRows - 1][numberOfColumns];
            for (int i = 1; i < numberOfRows; i++) {
                for (int j = 0; j < numberOfColumns; j++) {
                    excelData[i - 1][j] = sheet.getRow(i).getCell(j).getStringCellValue();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        return excelData;
    }

    public static String getSingleJsonData(String jsonFilePath, String jsonField) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader(jsonFilePath);
        Object obj = jsonParser.parse(fileReader);

        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject.get(jsonField).toString();
    }

    public static String[] readJson(String jsonFilePath, String jsonFieldArray, String field) throws IOException, ParseException {

        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader(jsonFilePath);
        Object obj = jsonParser.parse(fileReader);

        JSONObject jsonObject = (JSONObject) obj;
        JSONArray array = (JSONArray) jsonObject.get(jsonFieldArray);

        String arr[] = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            JSONObject users = (JSONObject) array.get(i);
            String fieldData = (String) users.get(field);

            arr[i] = fieldData;
        }
        return arr;
    }

    // TODO: connect to mysql database
    public static ResultSet getResultSet(String dbName, String port, String userName, String password, String query)
            throws SQLException {
        @SuppressWarnings("unused")
        DatabaseConnection databaseConnection = new DatabaseConnection(dbName, port, userName, password);
        // start connect
        DatabaseConnection.createDBConnection();
        // create statement
        DatabaseConnection.createStatement();
        // read data
        ResultSet resultSet = DatabaseConnection.dbResultSet(query);
        return resultSet;
    }

    // TODO: close connection to database
    public static void closeConnection() throws SQLException {
        DatabaseConnection.closedbConnection();
    }
    /*
     * simple way to use database connection try { ResultSet rs =
     * getResultSet("test", "3306", "root", "", "SELECT * FROM USERS WHERE ID=5");
     * while (rs.next()) System.out.println(rs.getInt(1) + "  " + rs.getString(2) +
     * "  " + rs.getString(3)); closeConnection(); } catch (SQLException e) { //
     * TODO Auto-generated catch block e.printStackTrace(); }
     */

    // TODO: use robot library for keyboard control
    public static void changeKeyBoard() throws AWTException {
        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_ALT);
        r.keyPress(KeyEvent.VK_SHIFT);
        r.keyRelease(KeyEvent.VK_ALT);
        r.keyRelease(KeyEvent.VK_SHIFT);
    }

    // TODO: generate random string for data driven test
    public static String generateString(int StringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = StringLength;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }

    // TODO: generate random character
    public static char generateCharacter() {

        return generateString(1).charAt(0);
    }

    // TODO: generate random integer number
    public static int generateInteger(int upperBound) {
        Random rand = new Random(); // instance of random class
        // generate random values from 0-24
        int int_random = rand.nextInt(upperBound);
        return int_random;
    }

    // TODO: generate integer of specific digits
    public static int generateRandomIntWithDigitSize(int digitSize) {
        Random random = new Random();
        int min = 100000000; // smallest 9-digit number
        int max = 999999999; // Largest 9-digit number
        return random.nextInt(max - min + 1) + min;
    }

    // TODO: generate random float number
    public static float generateFloat(int digitLength) {
        Random rand = new Random(); // instance of random class
        float float_random = rand.nextFloat();
        DecimalFormat df = new DecimalFormat(getDecimalFormat(digitLength));// format double digits
        float p = Float.parseFloat(df.format(float_random));
        return p;
    }

    // TODO: generate random double number
    public static double generateDouble(int digitLength) {
        Random rand = new Random(); // instance of random class
        double double_random = rand.nextDouble();// generate double number
        DecimalFormat df = new DecimalFormat(getDecimalFormat(digitLength));// format double digits
        double p = Double.parseDouble(df.format(double_random));
        return p;
    }

    // TODO: get decimal format
    public static String getDecimalFormat(int digitLength) {
        String doubleFormat = "#.";
        for (int i = 0; i < digitLength; i++) {
            doubleFormat += "#";
        }
        return doubleFormat;
    }

    // TODO: generate Random Plate Number
    public static String generateRandomPlateNumber() {
        StringBuilder plateNumber = new StringBuilder();

        // Generate 3 random uppercase letters
        for (int i = 0; i < 3; i++) {
            char randomLetter = (char) (new Random().nextInt(26) + 'A');
            plateNumber.append(randomLetter);
        }

        // Generate 4 random digits
        for (int i = 0; i < 4; i++) {
            int randomDigit = new Random().nextInt(10);
            plateNumber.append(randomDigit);
        }

        return plateNumber.toString();
    }

    // TODO: start html report
    public static void startHtmlReport(String reportDirName, String reportFileName) throws IOException {
        String path = System.getProperty("user.dir") + "/testReport.html";
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "cd " + reportDirName + " && " + reportFileName);
        builder.redirectErrorStream(true);
        Process p = builder.start();
    }

    // TODO: delete screenshots
    public static void deleteFilesInFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        boolean isDeleted = file.delete();
                        if (isDeleted) {
                            System.out.println("Deleted: " + file.getName());
                        } else {
                            System.out.println("Failed to delete: " + file.getName());
                        }
                    }
                }
            } else {
                System.out.println("The specified folder is empty or an error occurred.");
            }
        } else {
            System.out.println("The specified path is not a folder.");
        }
    }

    // TODO: Generate random unique int
    public static List<Integer> generateRandomUniqueInts(int size) {
        // List to store unique integers
        List<Integer> allNumbers = new ArrayList<>();

        // Add numbers from 1 to 6
        for (int i = 1; i <= 6; i++) {
            allNumbers.add(i);
        }

        // Shuffle the list to randomize the order
        Collections.shuffle(allNumbers);

        // Return a sublist of the first 'size' elements
        return allNumbers.subList(0, size);
    }

    // TODO: Support multi language on generated extend report and allure report
    public static void replaceLinesInExtendReportHtmlFile(String filePath) throws IOException {
        // Read the HTML file into a list of strings (each string is a line)
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        // Define the new content for the specific lines
        String line25 = "<style>\n" +
                "    .goog-te-gadget-simple{\n" +
                "    display:flex !important;\n" +
                "    width:100% !important;\n" +
                "    height:40px !important;\n" +
                "    }\n" +
                "    .goog-te-gadget-icon{\n" +
                "    padding-bottom:10px !important;\n" +
                "    }\n" +
                "    .goog-te-gadget-simple span{\n" +
                "    padding-top:15px !important;\n" +
                "    }\n" +
                "    .VIpgJd-ZVi9od-xl07Ob-lTBxed{\n" +
                "    height:20px!important;\n" +
                "    }\n";

        String line31 = "</style>\n" +
                "<script type=\"text/javascript\">// <![CDATA[\n" +
                "function googleTranslateElementInit() {\n" +
//                "    new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE,includedLanguages: 'az,br,de,en,ar,es,fr,he,isv,ja,ka,kr,nl,pl,ru,sv,tr,zh-CN'}, 'google_translate_element');\n" +
                "    new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE}, 'google_translate_element');\n" +
                "}\n" +
                "// ]]></script>\n" +
                "<script src=\"//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit\" type=\"text/javascript\"></script>";

        String line67 = "<div id=\"google_translate_element\" style=\"float:right;height: 43px!important;width: 150px;\"></div>";

        // Replace specific lines
        lines.set(24, line25); // Line 25 (index 24 in zero-indexed list)
        lines.set(30, line31); // Line 31 (index 30 in zero-indexed list)
        lines.set(66, line67); // Line 67 (index 66 in zero-indexed list)

        // Write the updated content back to the file
        Files.write(Paths.get(filePath), lines);
    }

    public static void replaceLinesInAllureReportHtmlFile(String filePath) throws IOException {
        // Read the HTML file into a list of strings (each string is a line)
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        // Define the new content for the specific lines
        String line5 = "<title>Allure Report</title><div id=\"google_translate_element\" style=\"float:right; height: 43px !important; width: auto !important;\"></div>\n" +
                "\n" +
                "<style>\n" +
                "    .goog-te-gadget-simple {\n" +
                "        display: flex !important;\n" +
                "        justify-content: flex-end !important; /* Align content to the right */\n" +
                "        width: 100% !important;\n" +
                "        height: 40px !important;\n" +
                "        align-items: center !important; /* Vertically center the content */\n" +
                "    }\n" +
                "    .goog-te-gadget-icon {\n" +
                "        padding-bottom: 10px !important;\n" +
                "    }\n" +
                "    .goog-te-gadget-simple span {\n" +
                "        padding-top: 15px !important;\n" +
                "    }\n" +
                "    .VIpgJd-ZVi9od-xl07Ob-lTBxed {\n" +
                "        height: 20px !important;\n" +
                "    }\n" +
                "</style>\n" +
                "\n" +
                "<script type=\"text/javascript\">// <![CDATA[\n" +
                "function googleTranslateElementInit() {\n" +
                "    new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE}, 'google_translate_element');\n" +
                "}\n" +
                "// ]]></script>\n" +
                "\n" +
                "<script src=\"//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit\" type=\"text/javascript\"></script>\n";

        // Replace specific lines
        lines.set(4, line5); // Line 5 (index 4 in zero-indexed list)

        // Write the updated content back to the file
        Files.write(Paths.get(filePath), lines);
    }

    // TODO: generate allure report after test finish as single html file
    public static void executeCommand(String command) throws IOException, InterruptedException {
        // Set default command if none is provided
        if (command == null || command.isEmpty()) {
            command = "allure generate --single-file target/allure-results";
        }
        // Create a ProcessBuilder instance
        ProcessBuilder processBuilder = new ProcessBuilder();

        // Split the command string into arguments (especially if it has multiple parts like the 'allure generate' command)
        processBuilder.command("bash", "-c", command);

        // Redirect error stream (optional, if you want to merge standard error with standard output)
        processBuilder.redirectErrorStream(true);

        // Start the process
        Process process = processBuilder.start();

        // Capture the output of the command
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // Print the output to the console (or handle it as needed)
            }
        }

        // Wait for the process to complete and get the exit value
        int exitCode = process.waitFor();
        System.out.println("Command executed with exit code: " + exitCode);
    }

    public static void copyFileToSrc() {
        // Define the source file path (file in child folder)
        Path sourcePath = Paths.get(System.getProperty("user.dir") + "/allure-report/index.html");

        // Define the destination file path (to the root of the project directory)
        Path destinationPath = Paths.get(System.getProperty("user.dir") + "/index.html");

        try (FileChannel sourceChannel = FileChannel.open(sourcePath, StandardOpenOption.READ);
             FileChannel destChannel = FileChannel.open(destinationPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {

            // Transfer the file from source to destination
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

            System.out.println("File copied successfully to: " + destinationPath);

        } catch (IOException e) {
            System.err.println("Error copying file: " + e.getMessage());
        }
    }

    public static void openBrowserNetworkTab() throws AWTException {
        // Create Robot instance
        Robot robot = new Robot();

        // Add a delay for setup (optional)
        robot.delay(2000); // Wait for the browser window to be active

        // Step 1: Press Ctrl+Shift+I to open Developer Tools
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_I);

        // Add a delay for Developer Tools to open
        robot.delay(1000);

        // release press buttons
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyRelease(KeyEvent.VK_I);

        // Step 2: Navigate to the Network tab
        // Use Right Arrow key multiple times to move to the Network tab
        for (int i = 0; i < 3; i++) {
            // Press and hold Ctrl
            robot.keyPress(KeyEvent.VK_CONTROL);

            // Press and release ]
            robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
            robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);

            // Release Ctrl
            robot.keyRelease(KeyEvent.VK_CONTROL);

            // Add a small delay between presses
            robot.delay(200);
        }
    }
}
