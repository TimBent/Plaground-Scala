package views

import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitKeyboard
import org.scalatestplus.play.{HtmlUnitFactory, OneBrowserPerSuite, PlaySpec}
import org.scalatestplus.play.guice.GuiceOneServerPerSuite

class TaskListPageSpec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory{

  "Task list" must {
    "Login and access funtions" in {
      go to s"http://localhost:$port/login"

      click on "username-login"
      textField("username-login").value = "Timothy"
      click on "password-login"
      textField("password-login").value = "toyota=1"
      submit()

    }
  }
}
