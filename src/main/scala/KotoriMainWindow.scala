import java.awt._
import java.awt.datatransfer.StringSelection
import java.awt.event._
import java.util
import javax.swing._

/**
 * Created by masaakif on 2015/02/23.
 */

object MyEvent extends WindowAdapter {
  override def windowClosing(e: WindowEvent):Unit = e.getWindow.dispose
}

class KotoriMainWindow extends WindowAdapter {
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  object Icon {
    def getImage(url:String): Image = new ImageIcon(getClass.getResource(url)).getImage
    val il = new util.ArrayList[Image]
    def getIcons = il

    il.add(getImage("kotori_16x16.jpg"))
    il.add(getImage("kotori_32x32.jpg"))
    il.add(getImage("kotori_48x48.jpg"))
  }

  object TextFieldFocusListener extends FocusAdapter {
    override def focusLost(e:FocusEvent): Unit = {
	    try {
		    output.setText(convert(input.getText))
		    output.setCaretPosition(0)
		    val txt = new StringSelection(output.getText)
		    Toolkit.getDefaultToolkit.getSystemClipboard.setContents(txt,txt)
	    } catch {
		    case e:Exception => println(e)
	    }
    }
  }

	def getNoWrapScrollPane(jComponent: JComponent): JScrollPane = {
		val noWrapPane = new JPanel(new BorderLayout)
		noWrapPane.add(jComponent)
		new JScrollPane(noWrapPane)
	}

	class MyText extends JTextPane {
		setFont(new Font("MS Gothic",Font.PLAIN,12))
	}

  val input = new MyText{addFocusListener(TextFieldFocusListener) }
  val output = new MyText{setEditable(false) }

  private def convert(input:String):String = new KotoriLogic().parseAndAppend(input)

  val frame = new JFrame {
    setTitle("Kotori - to convert JIRA ID to URL style")
	  setMinimumSize(new Dimension(400,300))
	  getContentPane.setLayout(new BoxLayout(getContentPane, BoxLayout.X_AXIS))
    addWindowListener(MyEvent)

	  val sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT){
		  setLeftComponent(getNoWrapScrollPane(input))
		  setRightComponent(getNoWrapScrollPane(output))
		  setResizeWeight(.5)
	  }
	  getContentPane.add(sp, BorderLayout.CENTER)//, BoxLayout.X_AXIS)
  }

  frame.setIconImages(Icon.getIcons)
  frame.setVisible(true)
}
