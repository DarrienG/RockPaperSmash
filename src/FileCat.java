/**
 * Created by Darrien on 12/16/15.
 */

import java.io.*;
import java.nio.Buffer;

public class FileCat {
    private String file1;
    private String file2;
    private String extension;
    private String spacer;
    private int spaceCount;
    private static int processCount = 0;

    public FileCat(String file1, String file2){
        this.file1 = file1;
        this.file2 = file2;
        extension = ".txt";
    }

    public FileCat(String file1, String file2, String extension) {
        this.file1 = file1;
        this.file2 = file2;
        this.extension = "." + extension;
    }

    public FileCat(String file1, String spacer, int spaceCount){
        this.file1 = file1;
        this.file2 = null;
        this.spacer = spacer;
        this.spaceCount = spaceCount;
        extension = ".txt";

    }

    // Concatenates two files horizontally using default naming convention for output file
    public String LateralOp() {
        String line1, line2, tmpBuf1, tmpBuf2;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file1));
            BufferedWriter bw = new BufferedWriter(new FileWriter("output" + processCount +  extension));

            // Working with two files
            if (file2 != null){
                BufferedReader br2 = new BufferedReader(new FileReader(file2));

                do{
                    line1 = br.readLine(); line2 = br2.readLine();
                    tmpBuf1 = line1 == null ? "" : line1;
                    tmpBuf2 = line2 == null ? "" : line2;

                    bw.write(tmpBuf1 + tmpBuf2);
                    bw.newLine();
                }while (line1!= null || line2 != null);
                bw.flush();
            }
            // Working with spacers
            else{
                line2 = "";
                for (int i = 0; i < spaceCount; ++i){
                    line2 += spacer;
                }
                do{
                    line1 = br.readLine();
                    bw.write(line1 + line2);
                    bw.newLine();
                }while(line1 != null);
                bw.flush();
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("ERROR: File does not exist. Please input a valid file.");
        }
        catch (IOException e){
            System.err.println("ERROR: Failure while reading from file.");
        }
        ++processCount;
        return "output" + processCount + extension;
    }

    // Concatenates two files horizontally using custom name for output file
    public String LateralOp(String name) {
        String line1, line2, tmpBuf1, tmpBuf2;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file1));
            BufferedWriter bw = new BufferedWriter(new FileWriter(name));

            // Working with two files
            if (file2 != null){
                BufferedReader br2 = new BufferedReader(new FileReader(file2));

                do{
                    line1 = br.readLine(); line2 = br2.readLine();
                    tmpBuf1 = line1 == null ? "" : line1;
                    tmpBuf2 = line2 == null ? "" : line2;

                    bw.write(tmpBuf1 + tmpBuf2);
                    bw.newLine();
                }while (line1!= null || line2 != null);
                bw.flush();
            }
            // Working with spacers
            else{
                line2 = "";
                for (int i = 0; i < spaceCount; ++i){
                    line2 += spacer;
                }
                while((line1 = br.readLine()) != null){
                    bw.write(line1 + line2);
                    bw.newLine();
                }
                bw.flush();
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("ERROR: File does not exist. Please input a valid file.");
        }
        catch (IOException e){
            System.err.println("ERROR: Failure while reading from file.");
        }
        return name;
    }

    // Concatenates two files vertically using default naming convention for output file
    public String VerticalOp(){
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file1));
            BufferedReader br2 = new BufferedReader(new FileReader(file2));
            BufferedWriter bw = new BufferedWriter(new FileWriter("output" + processCount +  extension));

            do{
                line = br.readLine();
                bw.write(line);
            }while(line != null);

            do{
                line = br2.readLine();
                bw.write(line);
                bw.newLine();
            }while(line != null);
            bw.flush();
        }
        catch(FileNotFoundException e){
            System.out.println("ERROR: File does not exist. Please input a valid input file.");
        }
        catch(IOException e){
            System.out.println("ERROR: Failure while reading from file.");
        }
        ++processCount;
        return "output" + processCount + extension;
    }

    // Concatenates two files vertically using custom name for output file
    public String VerticalOp(String name){
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file1));
            BufferedReader br2 = new BufferedReader(new FileReader(file2));
            BufferedWriter bw = new BufferedWriter(new FileWriter(name));

            do{
                line = br.readLine();
                bw.write(line);
            }while(line != null);

            do{
                line = br2.readLine();
                bw.write(line);
            }while(line != null);
            bw.flush();
        }
        catch(FileNotFoundException e){
            System.out.println("ERROR: File does not exist. Please input a valid input file.");
        }
        catch(IOException e){
            System.out.println("ERROR: Failure while reading from file.");
        }
        ++processCount;
        return name;
    }


    public String getDefaultFileName(){
        return "output" + processCount + extension;
    }
}
