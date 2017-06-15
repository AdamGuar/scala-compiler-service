package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import java.io.File
import play.api.libs.json._
import play.api.libs.functional.syntax._
import services.FilenameGenerator
import services.CodeCompiler
import model.CodeEntity
import services.FileRunner
import services.CodeComparator
import play.api.mvc.MultipartFormData.FilePart
import play.api.libs.Files._
import java.lang.Thread._
import scala.collection.mutable.ListBuffer
  /**
  * Controller Class
  */
@Singleton
class CodeController @Inject() extends Controller{
  
  val CODE_WORKING_DIRECTORY = System.getProperty("user.dir") + "/code/";

    /**
      * POST /code/upload endpoint
      *
      * @author Adam Woźniak i Karol Skóra
      *
      */
  def upload = Action(parse.multipartFormData) { 
    
    request => request.body.file("code").map { code =>
  
    val filename = code.filename
    val contentType = code.contentType
    //code.ref.moveTo(new File(s"/tmp/code/$filename"))
    val generator = new FilenameGenerator
    val fileID = generator.generateFileName()
    val file:File = new File(CODE_WORKING_DIRECTORY+fileID);
    code.ref.moveTo(file)
    val codeEntity = new CodeEntity(fileID,file);
    val compiler = new CodeCompiler(codeEntity,new File(System.getProperty("user.dir")))  
    val returnedFile=compiler.compile()
    if(returnedFile==null) BadRequest("Compilation Fail")
    println("Returned file = " + returnedFile.getAbsolutePath)
    val output = FileRunner.run(codeEntity, new File(System.getProperty("user.dir")))
    if(output._3 != 0) {
      BadRequest("Exit code != 0")
    } else {
      Ok(Json.obj(
        "status" ->"OK",
        "message" ->"File uploaded, compiled and run",
        "codeID" -> (fileID),
        "output" -> (Json.arr(output._1.toArray[String])),
        "similarities" -> (new CodeComparator(fileID).compare())       
      ))
    }
  }.getOrElse {
    BadRequest("File missing")
  }
}
  
  
  
      /**
      * POST /code/batchUpload endpoint
      *
      * @author Dawid Zych
      *
      */
  
    def handleFile = (file : FilePart[TemporaryFile], resultList : ListBuffer[Result]) => {
      val fileID = new FilenameGenerator().generateFileName()
      val realFile:File = new File(CODE_WORKING_DIRECTORY+fileID)  
      val codeEntity = new CodeEntity(fileID,realFile)
      
      var message = "File uploaded, compiled and run"
      var status = "OK"
      var codeID = fileID
      var output: (List[String], List[String], Int) = (List(""), List(""), -1)
     
      file.ref.moveTo(realFile)
      
      try {
         new CodeCompiler(codeEntity,new File(System.getProperty("user.dir"))).compile()
      } catch {
        case e: Exception => {
          status = "FAIL"
          message = "Compilation Error:" + e
        }
      }
    
      try {
        output = FileRunner.run(codeEntity, new File(System.getProperty("user.dir")))
      } catch {
        case e: Exception => {
          status = "FAIL"
          message = "Program execution error: " + e
        }} finally {
           resultList.synchronized {
           resultList += new Result(status, message, codeID, Json.arr(output._1.toArray[String]), new CodeComparator(fileID).compare())
         }
      }
    }
    /**
      * POST /code/upload endpoint
      *
      * @author Michał Suski
      *
      */
    case class Result(status: String, message: String, codeID: String, output: JsArray, similarities: JsValue)
  
    implicit val resultWrites = new Writes[Result] {
    def writes(result: Result) = Json.obj(
    "status" -> result.status,
    "message" -> result.message,
    "codeID" -> result.codeID,
    "output" -> result.output,
    "similarities" -> result.similarities
    )
    }
    /**
      * POST /code/upload endpoint
      *
      * @author Dawid Zych
      *
      */
    def batchUpload = Action(parse.multipartFormData) { 
      request => {
      var resultList = new ListBuffer[Result]()
      val files = request.body.files
      
      var threads = new Array[Thread](files.length);
      
      for ((file,index) <- files.zipWithIndex) { 
        threads(index) = new Thread{
          override def run() {
            handleFile(file, resultList)
          }
        }        
        threads(index).start();
      }   
      for(thread <- threads) {
        thread.join();
      }
      Ok(Json.toJson(resultList.toList))
    }
  }
}

