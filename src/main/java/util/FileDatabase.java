package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDatabase {

    private static final String DATA_DIR = getDataDirectory();

    private static String getDataDirectory() {
        File dataDir = new File("data");
        if (dataDir.exists() && dataDir.isDirectory()) {
            return dataDir.getAbsolutePath() + File.separator;
        }
        return "C:\\Users\\USER\\OneDrive\\Desktop\\Toy Galaxyfinal\\Toy Galaxy\\data\\";
    }


    public static int safeParseInt(String s) {
        if (s == null) return 0;
        try { return Integer.parseInt(s.trim()); }
        catch (NumberFormatException e) { return 0; }
    }


    public static long safeParseLong(String s) {
        if (s == null) return 0L;
        try { return Long.parseLong(s.trim()); }
        catch (NumberFormatException e) { return 0L; }
    }

    
    public static double safeParseDouble(String s) {
        if (s == null) return 0.0;
        try { return Double.parseDouble(s.trim()); }
        catch (NumberFormatException e) { return 0.0; }
    }

    public static List<String[]> readAll(String fileName) {
        List<String[]> data = new ArrayList<>();
        File file = new File(DATA_DIR + fileName);
        if (!file.exists()) return data;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    data.add(line.split("\\|", -1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static boolean addRecord(String fileName, String[] record) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_DIR + fileName, true))) {
            writer.write(String.join("|", record));
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Failed to write to " + DATA_DIR + fileName + ": " + e.getMessage());
            return false;
        }
    }

    public static boolean updateRecord(String fileName, String id, String[] newRecord) {
        List<String[]> allRecords = readAll(fileName);
        boolean updated = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_DIR + fileName))) {
            for (String[] record : allRecords) {
                if (record[0].trim().equals(id.trim())) {
                    writer.write(String.join("|", newRecord));
                    updated = true;
                } else {
                    writer.write(String.join("|", record));
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to update " + DATA_DIR + fileName + ": " + e.getMessage());
            return false;
        }
        return updated;
    }

    public static boolean deleteRecord(String fileName, String id) {
        List<String[]> allRecords = readAll(fileName);
        boolean deleted = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_DIR + fileName))) {
            for (String[] record : allRecords) {
                if (record[0].trim().equals(id.trim())) {
                    deleted = true;
                    continue;
                }
                writer.write(String.join("|", record));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to delete from " + DATA_DIR + fileName + ": " + e.getMessage());
            return false;
        }
        return deleted;
    }

    public static int getNextId(String fileName) {
        List<String[]> records = readAll(fileName);
        int maxId = 0;
        for (String[] record : records) {
            try {
                int id = Integer.parseInt(record[0].trim());
                if (id > maxId) maxId = id;
            } catch (NumberFormatException ignored) {}
        }
        return maxId + 1;
    }
}
