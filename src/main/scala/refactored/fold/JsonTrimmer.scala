package refactored.fold

import play.api.libs.json.{ JsObject, JsValue, Json }

object JsonTrimmer {

  def replaceFields(fields: Seq[String], jsValue: JsValue, suffix: String): JsValue = {
    fields.foldLeft(jsValue.as[JsObject])(replaceField(suffix))
  }

  private def replaceField(suffix: String)(jsObject: JsObject, fieldName: String) = {
    val suffixedFieldName = fieldName + suffix
    if (jsObject.keys.contains(suffixedFieldName)) {
      val value = jsObject \ suffixedFieldName
      jsObject ++ Json.obj(fieldName -> value.get)
    } else {
      jsObject
    }
  }
}
