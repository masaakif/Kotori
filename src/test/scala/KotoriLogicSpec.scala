/**
 * Created by masaakif on 2015/02/19.
 */

import org.specs2.mutable._

class KotoriLogicSpec extends Specification { override def is = s2"""
  This is a specification of KotoriLogic's conversion
    '[GUI-1234]' -> '=hyperlink("http://jira/browse/GUI-1234","[GUI-1234]")'                  $e1
    '[GUI-1234] - hogehoge' -> '=hyperlink("http://jira/browse/GUI-5311","GUI-5311")  hogehoge' $e2"""

	val kl = new KotoriLogic
	def e1 = kl.parse("[GUI-1234]") must_== """=hyperlink("http://jira/browse/GUI-1234","GUI-1234")"""
	def e2 = kl.parse("[GUI-1234] - hogehoge") must_== """=hyperlink("http://jira/browse/GUI-1234","GUI-1234")	hogehoge"""
}
