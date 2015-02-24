/**
 * Created by masaakif on 2015/02/19.
 */

import org.specs2.mutable._

class KotoriLogicSpec extends Specification {
  val kl = new KotoriLogic
  "JIRA line parse" should {
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
    "1234 convert to ''" in {
      kl.parse("1234") must_== ""
    }
    "'GUI-1234 hogehoge' convert to '=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")\thogehoge'" in {
      kl.parse("GUI-1234 hogehoge") must_== "=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")\thogehoge"
    }
    "'GUI-1234\\thogehoge' convert to '=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")\thogehoge'" in {
      kl.parse("GUI-1234\thogehoge") must_== "=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")\thogehoge"
    }
    "'GUI-1234 \\t hogehoge' convert to '=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")\thogehoge'" in {
      kl.parse("GUI-1234 \t hogehoge") must_== "=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")\thogehoge"
    }
    "'[GUI-1234] \\t hogehoge' convert to '=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")\thogehoge'" in {
      kl.parse("[GUI-1234] \t hogehoge") must_== "=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")\thogehoge"
    }
    "'\r\n' not converted" in {
      kl.parse("") must_== ""
    }
    "'[GUI-1234] - a\r\n' convert to '=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")" in {
      kl.parse("[GUI-1234] - a\r\n") must_== "=hyperlink(\"http://jira/browse/GUI-1234\",\"GUI-1234\")\ta"
    }
    "'1234\r\n\r\n\r\n\r\n1234' convert to ''" in {
      kl.parse("1234\r\n\r\n\r\n\r\n1234") must_== ""
    }
  }

  "hyperlink function should be append"  should {
    "[GUI-5408] append to 'Bug\t=hyperlink(\"http://jira/browse/GUI-5408\",\"GUI-5408\")\tCS,Mizuho'" in {
      kl.parseAndAppend("GUI-5408") must_== "Bug\t=hyperlink(\"http://jira/browse/GUI-5408\",\"GUI-5408\")\tCS, Mizuho"
    }
    "[GUI-5037] append to 'Improvement\t=hyperlink(\"http://jira/browse/GUI-5037\",\"GUI-5037\")\tKimco'" in {
      kl.parseAndAppend("GUI-5037") must_== "Improvement\t=hyperlink(\"http://jira/browse/GUI-5037\",\"GUI-5037\")\tKimco"
    }
	  ""
  }
}
