import org.junit.Test
import org.junit.Assert._
import java.io.File
import org.junit.Before
import org.scalatest.junit.AssertionsForJUnit
import org.scalatest.junit.JUnitSuite

import services.CodeCompiler
import model.CodeEntity

class CodeCompilerSuite extends JUnitSuite{
  
  val CODE_TEST_WORKING_DIRECTORY = System.getProperty("user.dir") + "/code/tests/"
  val OK_NAME = "Ok.scala"
  val WRONG_NAME = "Wrong.scala"
  
  var codeCompilerWithCorrectFile : CodeCompiler = _
  var mockCorrectFile : File = _
  var codeCompilerWithWrongFile : CodeCompiler = _ 
  
  
 @Before def initialize() {
   var correctCodeEntity : CodeEntity = new CodeEntity("Ok.scala",new File(CODE_TEST_WORKING_DIRECTORY+OK_NAME))
   codeCompilerWithCorrectFile  = new CodeCompiler(correctCodeEntity,new File(CODE_TEST_WORKING_DIRECTORY))
   var wrongCodeEntity: CodeEntity = new CodeEntity(WRONG_NAME,new File(CODE_TEST_WORKING_DIRECTORY))
   codeCompilerWithWrongFile  = new CodeCompiler(wrongCodeEntity,new File(CODE_TEST_WORKING_DIRECTORY))
  }
  
  @Test def testCorrectCompilation(){
    var file:File = codeCompilerWithCorrectFile.compile()
    assertNotNull(file)
  }
  
  @Test def testWrongCompilation(){
    var file:File = codeCompilerWithWrongFile.compile()
    assertNull(file)
  }
  
  
}
