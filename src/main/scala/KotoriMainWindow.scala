import java.awt._
import java.awt.event._
import java.util
import javax.swing.{ImageIcon, BoxLayout, UIManager}

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
    override def focusLost(e:FocusEvent): Unit = output.setText(convert(input.getText))
  }

  val input = new TextArea{addFocusListener(TextFieldFocusListener)}
  val output = new TextArea{setEditable(false)}

  private def convert(input:String):String = new KotoriLogic().parseAndAppend(input)

  val frame = new Frame {
    setTitle("Kotori - to convert JIRA ID to URL style")
    setMinimumSize(new Dimension(800,600))
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS))
    addWindowListener(MyEvent)

    add(input)
    add(output)
  }

  frame.setIconImages(Icon.getIcons)
  frame.setVisible(true)
}
