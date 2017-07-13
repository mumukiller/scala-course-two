package controllers


import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.Future

/**
  * Created by g.gerasimov on 18.03.2017.
  */
trait RestController extends Controller {

  def ok[A](obj: A)(implicit w: Writes[A]): Result = Ok(Json.toJson(obj))
  def notFound: Result = NotFound(Json.obj("result" -> "Not found"))
  def created[A](obj: A)(implicit w: Writes[A]): Result = Created(Json.toJson(obj))

  def badRequest(obj: JsError): Result = BadRequest("Detected error:" + JsError.toJson(obj))
}
