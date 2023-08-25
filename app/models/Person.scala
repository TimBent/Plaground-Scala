package models

class Person {

  // default constructor
  private var `@type`: String = null
  private var jobTitle: String = null
  private var name: String = null
  private var url: String = null

}

object Person {
  def apply(json: String): Person = {
    new Person()
  }
}