package controllers

import models.{TaskListInMemoryModel, UserCredentials}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{AbstractController, ControllerComponents, MessagesAbstractController, MessagesControllerComponents}

import javax.inject._

case class SignUpData(username : String, password : String)
@Singleton
class TaskListController @Inject()( controllerComponents: MessagesControllerComponents) extends MessagesAbstractController(controllerComponents){

  val signUpForm = Form(mapping(
    "Username" -> text(3, 10),
    "Password" -> text(8)
  )(SignUpData.apply)(SignUpData.unapply))
  def login = Action { implicit request =>
    Ok(views.html.login(signUpForm))
  }

  def logout() = Action { implicit request =>
    Redirect(routes.TaskListController.login()).withNewSession
  }

  def taskList = Action { implicit request =>
    val usernameFromSession = request.session.data.get("username")
    usernameFromSession.map { user =>
      val tasks = TaskListInMemoryModel.getTasks(user)
      Ok(views.html.taskList(tasks))
    }.getOrElse(Redirect(routes.TaskListController.login()))

  }

  def addTask = Action { implicit request =>
    val usernameFromSession = request.session.data.get("username")
    usernameFromSession.map { user =>
      val postValues = request.body.asFormUrlEncoded
      postValues.map { args =>
        val tasks = args("newTask").head
        TaskListInMemoryModel.addTask(user, tasks)
        Redirect(routes.TaskListController.taskList())
      }.getOrElse(Redirect(routes.TaskListController.taskList()).flashing(("ERROR", "Cannot add that task.")))
    }.getOrElse(Redirect(routes.TaskListController.login()))
  }

  def deleteTask = Action { implicit request =>
    val usernameFromSession = request.session.data.get("username")
    usernameFromSession.map { user =>
      val postValues = request.body.asFormUrlEncoded
      postValues.map { args =>
        val index = args("index").head.toInt
        TaskListInMemoryModel.removeTask(user, index)
        Redirect(routes.TaskListController.taskList())
      }.getOrElse(Redirect(routes.TaskListController.taskList()).flashing(("ERROR", "Cannot add that task.")))
    }.getOrElse(Redirect(routes.TaskListController.login()))
  }

  def validateLoginPost() = Action { implicit request =>
    val postRequestDetails = request.body.asFormUrlEncoded
    postRequestDetails.map { args =>
      val username = args("username").headOption
      val password = args("password").headOption
      (username, password) match {
        case (Some(u), Some(p)) =>
              if(TaskListInMemoryModel.validateUser(u, p)) {
                Redirect(routes.TaskListController.taskList()).withSession( "username" -> u)
              } else {
                Redirect(routes.TaskListController.login()).flashing("Error!" -> "Invalid username or password.")
              }
        case _ => Redirect(routes.TaskListController.login()).flashing("Error!" -> "Invalid username or password.")
      }
            }.getOrElse( Redirect(routes.TaskListController.login() ))

  }

  def validateSignUpForm = Action { implicit request =>
    signUpForm.bindFromRequest.fold(
      formWithError => BadRequest(views.html.login(formWithError)),
      ld =>
        if (TaskListInMemoryModel.createUser(ld.username, ld.password)) {
          Redirect(routes.TaskListController.taskList()).withSession("username" -> ld.username)
        } else {
          Redirect(routes.TaskListController.login()).flashing("Error!" -> "Invalid username or password.")
        }
    )
  }

  def createUserPost() = Action { implicit request =>
    val postRequestDetails = request.body.asFormUrlEncoded
    postRequestDetails.map { args =>
      val username = args("username").headOption
      val password = args("password").headOption
      (username, password) match {
        case (Some(u), Some(p)) =>
          if (TaskListInMemoryModel.createUser(u, p)) {
            Redirect(routes.TaskListController.taskList()).withSession(("username", u))
          } else {
            Redirect(routes.TaskListController.login()).flashing(("Error!", "User already exists!."))
          }
        case _ => Redirect(routes.TaskListController.login()).flashing(("Error!", "Invalid username and Password!."))
      }
    }.getOrElse(Redirect(routes.TaskListController.login()))

  }

  val user1 = UserCredentials("Timothy", "toyota=1", "benttsc001")
  val user2 = UserCredentials("Jeanine", "aniyah1", "bentje001")
  var retries = 3
  val userList = List(user1, user2)

  def getUserByName(userName: String): Option[UserCredentials] = {
    userList.find(_.userName.equals(userName))
  }

  def isPasswordCorrect(user: UserCredentials, input: String): Boolean = {
    user.password.equals(input)
  }
  def validateLoginGet( userName : String, password : String ) = Action {
    if (retries == 0) {
      BadRequest("You have been locked out of your account!")
    }
    else {

    val optionalUser = getUserByName(userName)

    optionalUser match {
      case Some(a) => if (isPasswordCorrect(a, password)) Ok(s"Login Successful. Welcome back ${a.firstName}")
                      else BadRequest("Incorrect Password")
      case _ => BadRequest("User does not exist!")
    }

  }

  }

}
