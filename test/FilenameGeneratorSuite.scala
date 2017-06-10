import org.junit.Test
import org.junit.Assert._
import java.io.File
import org.junit.Before
import org.scalatest.junit.AssertionsForJUnit
import org.scalatest.junit.JUnitSuite

import services.CodeCompiler
import services.FilenameGenerator


class FilenameGeneratorSuite extends JUnitSuite{
  var generator: FilenameGenerator = _
  
  @Before def initialize(){
    generator = new FilenameGenerator()
  }
  
  @Test def testIfEndsWithScala(){
    var filename: String = generator.generateFileName()
    var parts = filename.split("\\.");
    assertEquals("scala",parts(1))
  }
  
  @Test def testIfSumCorrect(){
    var filename1:String = generator.generateFileName()
    var filename2:String = generator.generateFileName()
    assertEquals(filename1.length(),filename2.length())
  }
  
}