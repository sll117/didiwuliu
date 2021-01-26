package com.example.didi;

import org.junit.Test;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        HttpUrl url=HttpUrl.parse("http://192.168.50.78:8080");
        System.out.println(url.host());
        Cookie cookie=Cookie.parse(url, "JSESSIONID=923CA75C46E9180FE3B39FF338AD6280");
        System.out.println(cookie);
    }
}