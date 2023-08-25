package models

import models.TaskListInMemoryModel
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class TaskListInMemoryModelSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "TaskListInMemoryModel" should {

    "accept correct login details for an existing user" in {
      TaskListInMemoryModel.validateUser("Timothy","toyota=1") mustBe true
    }

    "reject incorrect password details for an existing user login" in {
      TaskListInMemoryModel.validateUser("Timothy", "popcorn=1") mustBe false
    }

    "reject incorrect username details for a user login" in {
      TaskListInMemoryModel.validateUser("Bradley", "toyota=1") mustBe false
    }

    "reject incorrect useername and password details for a user login" in {
      TaskListInMemoryModel.validateUser("Timothy", "popcorn=1") mustBe false
    }

    "create a user which is not already in the list" in {
      TaskListInMemoryModel.createUser("Aniyah", "toyota=1") mustBe true
    }

    "not create a user which is already in the list" in {
      TaskListInMemoryModel.createUser("Timothy", "toyota=1") mustBe false
    }

    "return correct list of Tasks" in {
      TaskListInMemoryModel.getTasks("Timothy") mustBe List("Get dressed in the morning.","Eat breakfast.","Brush your teeth.")
    }

    "create a user with an introduction task" in {
      TaskListInMemoryModel.createUser("Jonathan", "toyota=1") mustBe true
      TaskListInMemoryModel.getTasks("Jonathan") mustBe List("Add a new task!")
    }

    "remove a task from a user's list" in {
      TaskListInMemoryModel.removeTask("Timothy", TaskListInMemoryModel.getTasks("Jonathan").indexOf("Get dressed in the morning.")) mustBe true
      TaskListInMemoryModel.getTasks("Timothy") must not contain "Get dressed in the morning."
    }

  }
}
