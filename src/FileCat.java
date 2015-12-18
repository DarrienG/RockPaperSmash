/**
 * Created by Darrien on 12/16/15.
 */

import java.io.*;

public class FileCat {
    private String file1;
    private String file2;
    private String extension;
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
    // Concatenates two files horizontally using default naming convention for output file
    public String LateralOp() {
        String line1, line2, tmpBuf1, tmpBuf2;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file1));
            BufferedReader br2 = new BufferedReader(new FileReader(file2));
            BufferedWriter bw = new BufferedWriter(new FileWriter("output" + processCount +  extension));
            do{
                line1 = br.readLine(); line2 = br2.readLine();
                tmpBuf1 = line1 == null ? "" : line1;
                tmpBuf2 = line2 == null ? "" : line2;

                bw.write(tmpBuf1 + tmpBuf2);
                bw.newLine();
            }while (line1!= null || line2 != null);
            bw.flush();
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
            BufferedReader br2 = new BufferedReader(new FileReader(file2));
            BufferedWriter bw = new BufferedWriter(new FileWriter(name));
            do{
                line1 = br.readLine(); line2 = br2.readLine();
                tmpBuf1 = line1 == null ? "" : line1;
                tmpBuf2 = line2 == null ? "" : line2;

                bw.write(tmpBuf1 + tmpBuf2);
                bw.newLine();
            }while (line1!= null || line2 != null);
            bw.flush();
        }
        catch (FileNotFoundException e) {
            System.err.println("ERROR: File does not exist. Please input a valid file.");
        }
        catch (IOException e){
            System.err.println("ERROR: Failure while reading from file.");
        }
        return name;
    }

    public String getDefaultFileName(){
        return "output" + processCount + extension;
    }
}
