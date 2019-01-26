package RockPaperSmash;

import java.lang.RuntimeException;
import java.io.*;
import java.nio.Buffer;
import java.io.InputStream;

public class FileCat {
    private FileResource file1;
    private FileResource file2;
    private String extension;
    private String spacer;
    private int spaceCount;
    private static int mProcCount = 0;

    /**
     * Basic constructor. Takes names of two files to be concatenated.
     *
     * @param file1 First file name.
     * @param file2 Second file name.
     */
    public FileCat(FileResource file1, FileResource file2) {
        this.file1 = file1;
        this.file2 = file2;
        extension = ".txt";
    }

    /**
     * Basic constructor. Takes names of two files to be concatenated, and extension of output files.
     *
     * @param file1 First file name.
     * @param file2 Second file name.
     * @param extension Extension of output file.
     */
    public FileCat(FileResource file1, FileResource file2, String extension) {
        this.file1 = file1;
        this.file2 = file2;
        this.extension = "." + extension;
    }

    /**
     * Basic constructor. Takes name of one file, the string it will be spaced with, and the number of spaces desired.
     *
     * @param file1 Name of first file.
     * @param spacer String String containing characters to be spaced with.
     * @param spaceCount Number of spaces desired.
     */
    public FileCat(FileResource file1, String spacer, int spaceCount) {
        this.file1 = file1;
        this.file2 = null;
        this.spacer = spacer;
        this.spaceCount = spaceCount;
        extension = ".txt";
    }

    /**
     * Basic constructor. Takes name of one file, the string it will be spaced with, number of spaces desired, and the
     * file extension.
     *
     * @param file1 Name of first file.
     * @param spacer String String containing characters to be spaced with.
     * @param spaceCount Number of spaces desired.
     * @param extension Extension of output file.
     */
    public FileCat(FileResource file1, String spacer, int spaceCount, String extension) {
        this.file1 = file1;
        this.file2 = null;
        this.spacer = spacer;
        this.spaceCount = spaceCount;
        this.extension = extension;
    }

    /**
     * Concatenates two files horizontally using default naming convention for output file.
     * @return String containing name of the file.
     */
    public String LateralOp() {
        String line1, line2, tmpBuf1, tmpBuf2;

        try {
            BufferedReader br = FileReader.loadFile(file1);
            BufferedWriter bw = new BufferedWriter(new FileWriter("output" + mProcCount +  extension));

            // Working with two files
            if (file2 != null) {
                BufferedReader br2 = FileReader.loadFile(file2);

                do {
                    line1 = br.readLine(); line2 = br2.readLine();
                    tmpBuf1 = line1 == null ? "" : line1;
                    tmpBuf2 = line2 == null ? "" : line2;

                    bw.write(tmpBuf1 + tmpBuf2);
                    bw.newLine();
                } while (line1!= null || line2 != null);
                bw.flush();
            }
            // Working with spacers
            else{
                line2 = "";
                for (int i = 0; i < spaceCount; ++i) {
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
        catch (IOException e) {
            System.err.println("ERROR: Failure while reading from file." + e);
        }
        ++mProcCount;
        return "output" + mProcCount + extension;
    }

    /**
     * Concatenates two files horizontally using custom name for output file. Name must include extension if it is
     * desired.
     *
     * @param name Custom name of output file.
     * @return Name of output file.
     */
    public String LateralOp(String name) {
        String line1, line2, tmpBuf1, tmpBuf2;
        try {
            BufferedReader br = FileReader.loadFile(file1);
            BufferedWriter bw = new BufferedWriter(new FileWriter(name));

            // Working with two files
            if (file2 != null) {
                BufferedReader br2 = FileReader.loadFile(file2);

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
                for (int i = 0; i < spaceCount; ++i) {
                    line2 += spacer;
                }
                while((line1 = br.readLine()) != null) {
                    bw.write(line1 + line2);
                    bw.newLine();
                }
                bw.flush();
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("ERROR: File does not exist. Please input a valid file." + e);
            e.printStackTrace();
            throw new RuntimeException(":(");
        }
        catch (IOException e) {
            System.err.println("ERROR: Failure while reading from file." + e);
        }
        return name;
    }

    /**
     * Concatenates two files vertically using default naming convention for output file. Name must include extension
     * if it is desired.
     *
     * @return String containing name of file.
     */
    public String VerticalOp() {
        String line;
        try {
            BufferedReader br = FileReader.loadFile(file1);
            BufferedReader br2 = FileReader.loadFile(file2);
            BufferedWriter bw = new BufferedWriter(new FileWriter("output" + mProcCount +  extension));

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
        catch(FileNotFoundException e) {
            System.out.println("ERROR: File does not exist. Please input a valid input file." + e);
        }
        catch(IOException e) {
            System.out.println("ERROR: Failure while reading from file." + e);
        }
        ++mProcCount;
        return "output" + mProcCount + extension;
    }

    //

    /**
     * Concatenates two files vertically using custom name for output file.
     *
     * @param name Custom name of output file.
     * @return String containing name of file.
     */
    public String VerticalOp(String name) {
        String line;
        try {
            BufferedReader br = FileReader.loadFile(file1);
            BufferedReader br2 = FileReader.loadFile(file2);
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
        catch(FileNotFoundException e) {
            System.out.println("ERROR: File does not exist. Please input a valid input file." + e);
        }
        catch(IOException e) {
            System.out.println("ERROR: Failure while reading from file." + e);
        }
        ++mProcCount;
        return name;
    }

    /**
     * Gets the current default file name.
     *
     * @return Current default file name.
     */
    public String getDefaultFileName() {
        return "output" + mProcCount + extension;
    }
}
