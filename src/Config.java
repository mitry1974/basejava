import java.io.*;

public class Config {
    private static final Config INSTANCE = new Config();
    private static final File PROPS = new File(".\\storage\\resumes.properties");

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try(InputStream is = new FileInputStream(PROPS)){
        } catch (IOException e) {
            throw new IllegalStateException("invalid config file name" + PROPS.getAbsolutePath());
        }
    }
}
