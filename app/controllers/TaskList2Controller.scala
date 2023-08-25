package controllers

import models.{TaskListInMemoryModel, UserCredentials}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject._
@Singleton
class TaskList2Controller @Inject()(controllerComponents: ControllerComponents) extends AbstractController(controllerComponents) {

  def load = Action { implicit request =>
    Ok(views.html.version2Main())
  }

  def login = Action { implicit request =>
    Ok(views.html.login2())
  }

  def validate2(username: String, password: String) = Action { implicit request => {
    if (TaskListInMemoryModel.validateUser(username, password)) {
      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username))).withSession("username" -> username)
    } else {
      Ok(views.html.login2()).flashing("Error!" -> "Invalid username or password.")
    }
  }
  }

  def createUser(username: String, password: String) = Action { implicit request => {
    if (TaskListInMemoryModel.createUser(username, password)) {
      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username))).withSession("username" -> username)
    } else {
      Redirect(routes.TaskList2Controller.login).flashing(("Error!", "User already exists!."))
    }
   }
  }

  def deleteTask(index: Int) = Action { implicit request => {
    val usernameFromSession = request.session.data.get("username")
    usernameFromSession.map { username =>
        TaskListInMemoryModel.removeTask(username, index)
      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
      }.getOrElse(Redirect(routes.TaskListController.taskList()).flashing(("ERROR", "Cannot add that task.")))
  }}
}
