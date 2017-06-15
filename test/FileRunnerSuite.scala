import org.junit.Test
import org.junit.Assert._
import java.io.File
import org.scalatest.junit.AssertionsForJUnit
import org.scalatest.junit.JUnitSuite
import services.FileRunner
import model.CodeEntity


/**
  * FileRunner Test class
  *
  * @author Karol Skóra
  *
  */
class FileRunnerSuite extends JUnitSuite{

  val codeEntity = new CodeEntity("test1", null)
  val incorectCodeEntity = new CodeEntity("test2", null)
  val appDirectory = new File(System.getProperty("user.dir")+"/test/DummyData")

  @Test
  def testShouldRunCorrectFile(){
    val output = FileRunner.run(codeEntity, appDirectory)
    assertEquals("run failed, exit code !=0 ", 0, output._3)
  }

  @Test
  def testShouldReturnNonZeroExitCodeForIncorectFile(){
    val output = FileRunner.run(incorectCodeEntity, appDirectory)
    assertEquals("Shoudl return 1 for incorrect file", 1, output._3)
  }

  @Test
  def testShouldReturnOutputListForCorrectFile(){
    val output = FileRunner.run(codeEntity, appDirectory)
    assertTrue("output is empty", output._1.length!=0)
  }

}