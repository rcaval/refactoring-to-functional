package smells.fold

import play.api.libs.json.{ JsObject, JsValue, Json }

object JsonTrimmer {

  def replaceFields(fields: Seq[String], jsValue: JsValue, suffix: String): JsValue = {
    var jsObjectToReturn = jsValue.as[JsObject]
    fields.foreach(fieldName => jsObjectToReturn = replaceField(fieldName, jsObjectToReturn, suffix))
    jsObjectToReturn
  }

  private def replaceField(fieldName: String, jsObject: JsObject, suffix: String) = {
    val suffixedFieldName = fieldName + suffix
    if (jsObject.keys.contains(suffixedFieldName)) {
      val value = jsObject \ suffixedFieldName
      jsObject ++ Json.obj(fieldName -> value.get)
    } else {
      jsObject
    }
  }
}
