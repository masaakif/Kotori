/**
 * Created by masaakif on 2015/02/19.
 */

import util.parsing.combinator.RegexParsers

class KotoriLogic extends RegexParsers {
	override val skipWhitespace = false
  def lines = repsep(line, eol)
  def line = jiraLine | other
  def other = "[^\r\n]*".r
  def eol = "\r\n"|"\r"|"\n"
  def jiraLine = jiraID ~ jiraDesc ^^ {x => x._1 + x._2} | jiraID
  def jiraID = (("[" ~> """\w+-\d+""".r <~ "]") | """[A-Z]+-\d+""".r) ^^ {x => "=hyperlink(\"http://jira/browse/"+x+"\",\""+x+"\")"}
  def jiraDesc = (" - " | """\s+""".r) ~> ".+".r ^^  {"\t"+_}

	def parse(input: String) = parseAll(lines, input).get.filter(_.length>2).mkString("\r\n")
}
