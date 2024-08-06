package playwright.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

public class App {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            BrowserType browserType = playwright.chromium();
            Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            String url = "https://www.ctruh.com/sign-up";
            String[] xpaths = {
            		   "xpath=/html/body/div[2]/div[2]/div/div[1]/div[1]", // Google
                       "xpath=/html/body/div[2]/div[2]/div/div[1]/div[2]", // Facebook
                       "xpath=/html/body/div[2]/div[2]/div/div[1]/div[3]", // GitHub
                       "xpath=/html/body/div[2]/div[2]/div/div[1]/div[4]", // LinkedIn
                       "xpath=//*[@id=\"test-7b9fdb1c-4151-4dfd-a98e-eefb882f0136\"]" // Manual
            };

            page.navigate(url);

            for (String xpath : xpaths) {
                clickButton(page, xpath);
                Thread.sleep(2000);
            }

            browser.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void clickButton(Page page, String xpath) {
        // Wait for the element to be visible
        Locator locator = page.locator(xpath);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        // Click the element using JavaScript to avoid interception
        locator.evaluate("element => element.click()");

        // Pause for 5 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Navigate back to the previous page
        page.goBack();
    }
}