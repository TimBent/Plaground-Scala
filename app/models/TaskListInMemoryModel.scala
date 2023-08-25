package models

import scala.collection.mutable

object TaskListInMemoryModel {

  private val users = mutable.Map[String, String](("Timothy", "toyota=1"),("Jeanine", "aniyah1"))
  private val tasksInMemory = mutable.Map[String, List[String]]("Timothy" -> List("Get dressed in the morning.","Eat breakfast.","Brush your teeth."))
  def validateUser( username : String, password: String): Boolean = {
    users.get(username).exists(_ == password)
  }

  def createUser(username: String, password: String): Boolean = {
    if ( users.contains(username)) false else {
      users(username)=password
      true
    }
  }

  def getTasks(username :String) : Seq[String] = {
    tasksInMemory.get(username).getOrElse(List("Add a new task!"))
  }

  def addTask(username : String, task: String): Unit = {
    tasksInMemory(username) = task :: tasksInMemory.getOrElse(username, Nil)
  }

  def removeTask(username : String, index: Int) : Boolean = {
    if( index > 0 || tasksInMemory.get(username).isEmpty || index >= tasksInMemory(username).length) false
    else {
      tasksInMemory(username) = tasksInMemory(username).patch( index, Nil, 1 )
      true
    }
  }


}
