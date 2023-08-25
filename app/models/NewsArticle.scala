package models

import java.util

package object NewsArticle {
  implicit var `@context`: String = null
  implicit var `@type`: String = null
  implicit var headline: String = null
  implicit var image: util.List[String] = null
  implicit var datePublished: String = null
  implicit var dateModified: String = null
  implicit var author: Array[Person] = null
  implicit var publisher: Organization = null
  implicit var description: String = null
  implicit var articleBody: String = null

}
