/**
 * Created by masaakif on 2015/02/19.
 */

import util.parsing.combinator.RegexParsers

class KotoriLogic extends RegexParsers {
	override val skipWhitespace = false
  def lines = repsep(line,eoli)
  def line = jiraLine
  def eoli = """(\r\n|\r|\n|\z)""".r
  def jiraLine = jiraID ~ jiraDesc ^^ {x => x._1 + x._2} | jiraID
	def jiraID = ("[" ~> """\w+-\d+""".r <~ "]") ^^ {x => "=hyperlink(\"http://jira/browse/"+x+"\",\""+x+"\")"}
  def jiraDesc = """ - .+""".r ^^  {_.replaceFirst(" - ", "\t")}

	def parse(input: String) = parseAll(lines, input).get.mkString("\r\n")
}
