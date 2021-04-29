import java.util.*;

class TestConfig {
    
    private String username;
    private String password;
    private String downloadPath;
    private List<StaticPageTest> staticPages;

    public TestConfig() { }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public List<StaticPageTest> getStaticPages() {
        return staticPages;
    }
}