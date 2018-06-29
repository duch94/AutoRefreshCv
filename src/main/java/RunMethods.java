import com.codeborne.selenide.WebDriverRunner;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    2) Вписывать в моки из конфига логин, пароль, урл резюме (/)
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

    public static void main(String[] args) {
        long second = 1000;
        long minute = second * 60;
        long hour = minute * 60;
        
        long autorepeatTime = hour * 4 + minute;
        
        Timer timer = new Timer();
        MyScheduledTask task = new MyScheduledTask();
        timer.scheduleAtFixedRate(task, 0, autorepeatTime);
    }
    
}

class MyScheduledTask extends TimerTask {
    RunMethods runner = new RunMethods();

    @Override
    public void run() {
        String testsPath = "conf/jobs.yml";
        String userDataPath = "conf/userdata.yml";
        UserDataYaml userData = new UserDataYaml();
        userData.loadUserDataFromFile(userDataPath);
        List<String> tests = runner.getTests(testsPath);
        CvRefresh self = new CvRefresh();
        runner.runTestMethods(self, tests);
    }
}

class UserDataYaml {
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


    public void loadUserDataFromFile(String path) {
        File file = new File(path);
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        UserDataYaml parsedUserData = null;
        try {
            parsedUserData = mapper.readValue(file, UserDataYaml.class);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        System.out.println("Data parsed!");
        loadUserDataToMocks(parsedUserData);
    }

    private void loadUserDataToMocks(UserDataYaml data) {
        Mocks.Login.login = data.login;
        Mocks.Login.password = data.password;
        Mocks.Login.fullUserName = data.fullUserName;
        Mocks.CV.url = data.cvUrl;
        System.out.println("Data loaded!");
    }
}