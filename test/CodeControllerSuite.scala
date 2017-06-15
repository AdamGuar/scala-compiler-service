import org.junit.Test
import org.junit.Assert._
import java.io.File
import org.junit.Before
import org.scalatest.junit.AssertionsForJUnit
import org.scalatest.junit.JUnitSuite
import mocks.MockCodeCompiler
import mocks.MockFileRunner
import services.CodeCompiler
import model.CodeEntity
import controllers.CodeController

class CodeControllerSuite extends JUnitSuite {

  var codeController: CodeController

  @Before def initialize(){
    codeController = new CodeController
    codeController.compiler = new MockCodeCompiler
    codeController.fileRunner = new MockFileRunner

  }

  @Test def testUpload(){
    //Teraz trzeba stworzyc fake request i z tym requestem wywolac metode controllera upload()

}
