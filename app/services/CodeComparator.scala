package services
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.charset.StandardCharsets
import java.util.Arrays
import java.util
import info.debatty.java.stringsimilarity.JaroWinkler
import java.lang.System
import play.api.libs.json._
import java.util.ArrayList
import scala.collection.mutable.ListBuffer

/**
  * Class takes a codeID and compare file with given id with all other codes
  *
  * @author Michał Suski i Karol SKó®a
  * @param codeID: id of code to compare
  *
  */
class CodeComparator(id: String) {
  
  
  case class Sim(id: String, similarity: Double)
  
  implicit val simWrites = new Writes[Sim] {
  def writes(sim: Sim) = Json.obj(
    "id" -> sim.id,
    "value" -> sim.similarity
  )
}
  
  
  private var codeID = id
  private val folder = new File(System.getProperty("user.dir")+"/code") 
  
  def compare(): JsValue/*: Map[String, Float] */= {
    
    
   // var result = "";
    
    var simList = new ListBuffer[Sim]()
    
    var resultArrJson = new JsArray();
   
    val fileNames = folder.listFiles()
    val nl = new JaroWinkler()
    val initIdList = Files.readAllLines(Paths.get(folder+"/"+id),StandardCharsets.UTF_8).toArray()
    var initIdString = "";
    
    for (line <- initIdList) {
      initIdString += line
    }
    
    for (fileName <- fileNames) {
      
        var fileString = ""
        val fileStringList = Files.readAllLines(fileName.toPath(),StandardCharsets.UTF_8).toArray()
        
        for (line <- fileStringList) {
           fileString += line
        }
        
        simList += (new Sim(fileName.getName(), nl.similarity(initIdString, fileString)))
        
        //resultArrJson.append(Json.obj("ID" -> fileName.getName(), "Val" -> nl.similarity(initIdString, fileString)))

      //  resultArr.add(new Sim(fileName.getName(), nl.similarity(initIdString, fileString)))
        
       // result += "Id: " + fileName.getName() + " Similarity: " + nl.similarity(initIdString, fileString) + System.lineSeparator()
    }
    
   // resultJsonArr :+ Json.obj("status" ->"OK");
    
  
    
    return Json.toJson(simList.toList)
    
  }
}