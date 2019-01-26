package RockPaperSmash;

public class FileResource {
    private String fileName;
    private boolean resource;

    public FileResource(String fileName) {
        this(fileName, true);
    }

    public FileResource(String fileName, boolean resource) {
        this.fileName = fileName;
        this.resource = resource;
    }

    public String getFileName() {
        return this.fileName;
    }

    public boolean getResource() {
        return this.resource;
    }
}
