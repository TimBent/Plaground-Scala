package models

class Organization {

  // default constructor
  private var `@type`: String = null
  private var name: String = null
  private var logo: ImageObject = null
  private var url: String = null

}

object Organization {
  def apply(json: String): Organization = {
    new Organization()
  }
}
