import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;


public class CvRefresh {
    public void userLogins() {
        open(Mocks.Login.url);
        $(Mocks.Login.loginCssSel).setValue(Mocks.Login.login);
        $(Mocks.Login.passwordCssSel).setValue(Mocks.Login.password);
        $(Mocks.Login.loginButtonCssSel).click();
        $(Mocks.Login.fullUserNameCssSel).shouldHave(text(Mocks.Login.fullUserName));
        System.out.printf("Name %s is on page\n", Mocks.Login.fullUserName);
    }

    public void userRefreshesCv() {
        userLogins();
        open(Mocks.CV.url);
        $(Mocks.CV.refreshButtonCssSel).click();
        $(Mocks.CV.refreshInfoCssSel).shouldHave(text(Mocks.CV.refreshInfo));
        String refreshInfo = $(Mocks.CV.refreshInfoCssSel).text();
        System.out.println("CV have been refreshed. Refresh info: " + refreshInfo);
    }
}
