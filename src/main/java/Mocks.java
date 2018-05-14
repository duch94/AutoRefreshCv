public class Mocks {
    /*
    TO-DO:

    1) Вынести логин, пароль, урл резюме в отдельный конфиг
    2) Вписывать в моки из конфига логин, пароль, урл резюме
    3) Добавить планировщик запуска

     */
    public static class Login {
        public static String url = "https://hh.ru/";
        public static String loginCssSel = "body > div.index-dashboard.index-dashboard_domain-hh-ru > div > div.bloko-column.bloko-column_s-0.bloko-column_m-4.bloko-column_l-4 > div > div > div:nth-child(2) > form > label:nth-child(1) > input";
        public static String passwordCssSel = "body > div.index-dashboard.index-dashboard_domain-hh-ru > div > div.bloko-column.bloko-column_s-0.bloko-column_m-4.bloko-column_l-4 > div > div > div:nth-child(2) > form > label:nth-child(2) > input";
        public static String loginButtonCssSel = "body > div.index-dashboard.index-dashboard_domain-hh-ru > div > div.bloko-column.bloko-column_s-0.bloko-column_m-4.bloko-column_l-4 > div > div > div:nth-child(2) > form > div.login-submit-form > input";
        public static String login = ""; // user login
        public static String password = ""; // user password
        public static String fullUserName = "Виктор Титов";
        public static String fullUserNameCssSel = "body > div.navi.HH-VacancyMainSearchInfoTip-Navi > div.HH-Navi-Menu-Menu.navi__menu > div > div > div > ul";
    }

    public static class CV {
        public static String url = ""; // user's cv url
        public static String refreshButtonCssSel = "body > div.HH-MainContent > div.bloko-columns-wrapper > div > div > div > div.resume-wrapper > div:nth-child(2) > div > div.resume-sidebar > div > div > div > div:nth-child(2) > div:nth-child(7) > div.bloko-gap.bloko-gap_top > button";
        public static String refreshInfoCssSel = "body > div.HH-MainContent > div.bloko-columns-wrapper > div > div > div > div.resume-wrapper > div:nth-child(2) > div > div.resume-sidebar > div > div > div > div:nth-child(2) > div:nth-child(7) > div.resume-sidebar-link.HH-Resume-UpdateResume-Message";
        public static String refreshInfo = "Обновить можно через";
    }
}
