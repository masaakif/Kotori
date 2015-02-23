/**
 * Created by masaakif on 2015/02/19.
 */

import org.specs2.mutable._

class KotoriLogicSpec extends Specification {
  val kl = new KotoriLogic
  "JIRA line" should {
    "[GUI-1234] convert to =hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")" in {
      kl.parse("[GUI-1234]") must_== "=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")"
    }
    "'[GUI-1234] - hogehoge' convert to '=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")\thogehoge'" in {
      kl.parse("[GUI-1234] - hogehoge") must_== "=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")\thogehoge"
    }
    "'[GUI-1234] - [XILIX-1234] hogehoge' convert to '=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")\t[XILIX-1234] hogehoge'" in {
      kl.parse("[GUI-1234] - [XILIX-1234] hogehoge") must_== "=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")\t[XILIX-1234] hogehoge"
    }
    "'[XILIX-1234] - fugafuga' convert to '=hyperlink(\"http://jira/browse/XILIX-1234\",\"XILIX-1234\")\tfugafuga'" in {
      kl.parse("[XILIX-1234] - fugafuga") must_== "=hyperlink(\"http://jira/browse/XILIX-1234\",\"XILIX-1234\")\tfugafuga"
    }
    "'[GUI-1234] - hogehoge\r\n[XILIX-1234] - fugafuga' convert to '=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")\thogehoge'\r\n'=hyperlink(\"http://jira/browse/XILIX-1234\",\"XILIX-1234\")\tfugafuga'" in {
      kl.parse("[GUI-1234] - hogehoge\r\n[XILIX-1234] - fugafuga") must_== "=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")\thogehoge\r\n=hyperlink(\"http://jira/browse/XILIX-1234\",\"XILIX-1234\")\tfugafuga"
    }
  }
}
