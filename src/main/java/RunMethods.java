import com.codeborne.selenide.WebDriverRunner;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.fasterxml.jackson.dataformat.yaml.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

    /*
    TO-DO:

    1) Вынести логин, пароль, урл резюме в отдельный конфиг (/)
    2) Вписывать в моки из конфига логин, пароль, урл резюме
    3) Добавить планировщик запуска (/)
     */

public class RunMethods {

    public static List<String> getTests(String path) {
        Scanner in = null;
        List<String> tests = new ArrayList<String>();
        String temp = "";
        try {
            in = new Scanner(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        while (true) {
            if (!in.hasNext()) {
                break;
            }
            temp = in.nextLine();
            try {
                tests.add(temp);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        return tests;
    }

    public static void runTestMethods(Object object, List<String> tests) {
        for (String testName: tests) {
            System.out.println("Test " + testName + " started");
            try {
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(false);
                ChromeDriver wd = new ChromeDriver(options);
                WebDriverRunner.setWebDriver(wd);

                object.getClass().getDeclaredMethod(testName).invoke(object);

                WebDriverRunner.closeWebDriver();
            } catch (NoSuchMethodException e) {
                System.out.println(e.getCause().toString());
            } catch (IllegalAccessException e) {
                System.out.println(e.getCause().toString());
            } catch (InvocationTargetException e) {
                System.out.println(e.getCause().toString());
            } finally {
                WebDriverRunner.closeWebDriver();
            }
            System.out.println("Test " + testName + " ended\n");
        }
    }

    public class UserDataYaml {
        @JsonProperty
        public String login;
        @JsonProperty
        public String password;
        @JsonProperty
        public String fullUserName;
        @JsonProperty
        public String cvUrl;

        public UserDataYaml() {
            login = "";
            password = "";
            fullUserName = "";
            cvUrl = "";
        }

        public void setLogin(String newLogin) {
            login = newLogin;
        }
        public void setPassword(String newPassword) {
            password = newPassword;
        }
        public void setFullUserName(String newFullUserName) {
            fullUserName = newFullUserName;
        }
        public void setCvUrl(String newCvUrl) {
            cvUrl = newCvUrl;
        }

        public String getLogin() {
            return login;
        }
        public String getPassword() {
            return password;
        }
        public String getFullUserName() {
            return fullUserName;
        }
        public String getCvUrl() {
            return cvUrl;
        }
    }

    public static UserDataYaml getUserData(String path) {
        File file = new File(path);
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        UserDataYaml parsedUserData = null;
        try {
            parsedUserData = mapper.readValue(file, UserDataYaml.class);
        } catch (java.io.IOException e) {
            System.out.println(e.getCause().toString());
        }
        return parsedUserData;
    }

    public static void main(String[] args) {
        long second = 1000;
        long minute = second * 60;
        long hour = minute * 60;
        
        long autorepeatTime = hour * 4 + minute;
        
        Timer timer = new Timer();
        MyScheduledTask task = new MyScheduledTask();
        timer.scheduleAtFixedRate(task, 0, autorepeatTime);
    }
    
    class MyScheduledTask extends TimerTask {
        @Override
        public void run() {
            String testsPath = "conf/jobs.yml";
            String userDataPath = "conf/userdata.yml";
            UserDataYaml userData = getUserData(userDataPath);
            List<String> tests = getTests(testsPath);
            CvRefresh self = new CvRefresh();
            runTestMethods(self, tests);
        }
    }
    
}
