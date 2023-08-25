package models

class ImageObject {

  // default constructor
  private var `@type`: String = null
  private var url: String = null

}

object ImageObject {
  def apply(json: String): ImageObject = {
    new ImageObject()
  }
}

