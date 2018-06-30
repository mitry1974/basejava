import java.io.FileInputStream;
import java.io.InputStream;

public class Config {
    private static final Config INSTANCE = new Config();

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try(InputStream is = new FileInputStream("./config/resumes.properties")){}
    }
}
