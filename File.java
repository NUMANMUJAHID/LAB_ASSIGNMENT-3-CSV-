import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class File {

    public static ArrayList<String[]> readCSV(String filePath) {
        ArrayList<String[]> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                dataList.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    public static void writeIntoCSV(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
             PrintWriter pw = new PrintWriter(bw)) {

            Scanner scanner = new Scanner(System.in);


            System.out.print("Enter Login Email: ");
            String loginEmail = scanner.nextLine();

            System.out.print("Enter Identifier: ");
            String identifier = scanner.nextLine();

            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();

            // Construct CSV line
            String csvLine = String.format("%s;%s;%s;%s", loginEmail, identifier, firstName, lastName);

            // Write CSV line to file
            pw.println(csvLine);

            System.out.println("Data has been successfully written to " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void serializeData(String filePath, ArrayList<String[]> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
            System.out.println("Serialization successful. Data saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<String[]> deserializeData(String filePath) {
        ArrayList<String[]> dataList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            dataList = (ArrayList<String[]>) ois.readObject();
            System.out.println("Deserialization successful. Data loaded from " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static void main(String[] args) {
        String csvFilePath = "email.csv";

        ArrayList<String[]> csvData = readCSV(csvFilePath);

        for (String[] row : csvData) {
            for (String value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }


        writeIntoCSV(csvFilePath);


        serializeData("serialized_data.dat", csvData);


        ArrayList<String[]> deserializedData = deserializeData("serialized_data.dat");
        for (String[] row : deserializedData) {
            for (String value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
