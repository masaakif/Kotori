/**
 * Created by masaakif on 2015/02/19.
 */

import util.parsing.combinator.RegexParsers

class KotoriLogic extends RegexParsers {
	override val skipWhitespace = false

  def lines = repsep(line, eol)
  def line = jiraLine | other
  def other = "[^\r\n]*".r ^^ {_=>""}
  def eol = "\r\n"|"\r"|"\n"
  def jiraLine = jiraID ~ jiraDesc ^^ {x => buildFormula(x._1) + x._2} | jiraID ^^ {buildFormula(_)}
  def jiraID = (("[" ~> """\w+-\d+""".r <~ "]") | """[A-Z]+-\d+""".r)
  def jiraDesc = (" - " | """\s+""".r) ~> ".+".r ^^  {"\t"+_}
	def parse(input: String):String = parseAll(lines, input).get.filter(_.length>2).mkString("\r\n")

  def buildFormula(id:String) = "=hyperlink(\""+buildUrl(id)+"\",\""+id+"\")"
  def buildUrl(id:String) = "http://jira/browse/"+id

  // Append routine
  def lines_a = repsep(line_a, eol)
  def line_a = jiraLine_a | other
  def jiraLine_a = jiraID ~ jiraDesc ^^ {x => buildLine(x._1, x._2)} | jiraID ^^ {buildLine(_)}
  def parseAndAppend(input:String):String = parseAll(lines_a, input).get.filter(_.length>2).mkString("\r\n")
  def buildLine(id:String,desc:String="") = {
    val htmlPicker = new HtmlPicker(buildUrl(id))
    Array(htmlPicker.getType,buildFormula(id) + desc,htmlPicker.getCustomer).mkString("\t")
  }
}

case class HtmlPicker(url:String) {
	import org.openqa.selenium.htmlunit.HtmlUnitDriver

  val driver = new HtmlUnitDriver{
	  get("http://jira/secure/Dashboard.jspa")
	  findElementByName("os_username").sendKeys("masaakif")
	  findElementByName("os_password").sendKeys("masaakif")
	  findElementById("login").click
	  get(url)
  }
  def getType:String = {
	  driver.findElementByXPath("//table[@id='issuedetails']/tbody/tr[2]/td[2]").getText
  }

  def getCustomer:String = {
	  driver.findElementByXPath("//tr[@id='rowForcustomfield_10010']/td[2]").getText
  }
}
