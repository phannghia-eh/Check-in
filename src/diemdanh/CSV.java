/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diemdanh;

import entities.SinhVien;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Phan Nghia
 */
public class CSV {
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    
    //Student attributes index
    private static final int HS_ID_IDX = 0;
    private static final int HS_TEN_IDX = 1;

    public static void readCsvFile(List<SinhVien> ds,String fileName) {

            BufferedReader fileReader = null;

    try {


        String line = "";

        //Create the file reader
        fileReader = new BufferedReader(new FileReader(fileName));

        //Read the CSV file header to skip it
        fileReader.readLine();

        //Read the file line by line starting from the second line
        while ((line = fileReader.readLine()) != null) {
            //Get all tokens available in line
            String[] tokens = line.split(COMMA_DELIMITER);
            if (tokens.length > 0) {
                    //Create a new student object and fill his  data
                    String masv = tokens[HS_ID_IDX];
                    String ten = tokens[HS_TEN_IDX];
                    SinhVien sv = new SinhVien(masv,masv,ten);
                    ds.add(sv);

                            }
        }
        System.out.println("Danh sach hoc sinh tu file CSV !!!");
        //Print the new student list

    } 
    catch (IOException e) {
            System.out.println("Error in CsvFileReader !!!");
        e.printStackTrace();
    } finally {
        try {
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Error while closing fileReader !!!");
            e.printStackTrace();
        }
    }

}

    //CSV file header
    private static final String FILE_HEADER = "Mã sinh viên,Tên";

    public static void writeCsvFile(List<SinhVien> dssv,String fileName) {

            //Create new students objects

            FileWriter fileWriter = null;

            try {
                    fileWriter = new FileWriter(fileName);

                    //Write the CSV file header
                    fileWriter.append(FILE_HEADER.toString());

                    //Add a new line separator after the header
                    fileWriter.append(NEW_LINE_SEPARATOR);

                    //Write a new student object list to the CSV file
                    for (SinhVien sv : dssv) {
                            fileWriter.append(String.valueOf(sv.getMaSv()));
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(sv.getTenSv());
                            fileWriter.append(NEW_LINE_SEPARATOR);
                    }
                    Path currentRelativePath = Paths.get("");
                    String s = currentRelativePath.toAbsolutePath().toString();
                    JOptionPane.showMessageDialog(null, "Tạo file CSV "+fileName+" thành công\nĐường dẫn:\n" + s +"\\"+ fileName, "INFO", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                     JOptionPane.showMessageDialog(null, "Lỗi đọc file!!\n"+ e, "INFO", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
            } finally {

                    try {
                            fileWriter.flush();
                            fileWriter.close();
                    } catch (IOException e) {
                         JOptionPane.showMessageDialog(null, "Lỗi khi đóng file\n"+ e, "INFO", JOptionPane.ERROR_MESSAGE);
                        System.out.println("Error while flushing/closing fileWriter !!!");
            e.printStackTrace();
                    }

            }
    }
}
