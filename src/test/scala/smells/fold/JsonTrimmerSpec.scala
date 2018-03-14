package smells.fold

import org.scalatest.{ Matchers, WordSpec }
import play.api.libs.json.{ JsValue, Json }

class JsonTrimmerSpec extends WordSpec with Matchers {


  "JsonTrimmer.replaceFields" should {

    "replace all fields with suffix" in {
      val suffix = "_real"
      val jsValue: JsValue = Json.parse(
        """
          |{
          | "product": "123321",
          | "score": 0,
          | "score_real": 2,
          | "count": 0,
          | "count_real": 3
          |}
        """.stripMargin
      )

      val result = JsonTrimmer.replaceFields(Seq("score", "count"), jsValue, suffix)

      (result \ "product").as[String] should be("123321")
      (result \ "score").as[Int] should be(2)
      (result \ "count").as[Int] should be(3)
    }

    "not replace when there aren't any suffixed fields" in {
      val suffix = "_real"
      val jsValue: JsValue = Json.parse(
        """
          |{
          | "product": "123321",
          | "score": 0,
          | "count": 1
          |}
        """.stripMargin
      )

      val result = JsonTrimmer.replaceFields(Seq("score", "count"), jsValue, suffix)

      (result \ "product").as[String] should be("123321")
      (result \ "score").as[Int] should be(0)
      (result \ "count").as[Int] should be(1)
    }
  }


}
