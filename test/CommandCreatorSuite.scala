import org.junit.Test
import org.junit.Assert._
import java.io.File
import org.scalatest.junit.AssertionsForJUnit
import org.scalatest.junit.JUnitSuite
import services.CommandCreator


/**
  * CommandCreator Test class
  *
  * @author Karol Skóra
  *
  */
class CommandCreatorSuite extends JUnitSuite{

  @Test
  def testShouldReturnCmdWithoutExtensionForNotWinSystem(){
    assertTrue("return file with .bat extension", !CommandCreator.createCommand("testCmd", "macos").contains(".bat"))
  }

  @Test
  def testShouldReturnCmdWithExtensionForWinSystem(){
    assertTrue("return file without .bat extension", CommandCreator.createCommand("testCmd", "win").contains(".bat"))
  }

}