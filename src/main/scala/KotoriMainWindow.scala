import java.awt._
import java.awt.event._
import javax.swing.{BoxLayout, UIManager}

/**
 * Created by masaakif on 2015/02/23.
 */

object MyEvent extends WindowAdapter {
  override def windowClosing(e: WindowEvent):Unit = e.getWindow.dispose
}

class KotoriMainWindow extends WindowAdapter {
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  object TextFieldFocusListener extends FocusAdapter {
    override def focusLost(e:FocusEvent): Unit = {
      println(input.getText)
      output.setText(convert(input.getText))
    }
  }

  val input = new TextArea{addFocusListener(TextFieldFocusListener)}
  val output = new TextArea{setEditable(false)}

  private def convert(input:String):String = new KotoriLogic().parse(input)

  val frame = new Frame {
    setTitle("Kotori")
    setMinimumSize(new Dimension(800,600))
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS))
    addWindowListener(MyEvent)

    add(input)
    add(output)
  }

  frame.setVisible(true)
}
