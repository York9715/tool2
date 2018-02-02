package example;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;


/*

10,Australia,AU
11,Aus""tralia,AU
"12","Australia","AU"
"13","Aus""tralia","AU"
"14","A,us\"t,r\\alia","AU"
"15","Aus,tralia","AU"

*/

public class CSVReaderExample {

    public static void main(String[] args) {

        String csvFile = "C:\\york\\x\\country3.csv";

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
                System.out.println("Country [id= " + line[0] + ", code= " + line[1] + " , name=" + line[2] + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}


