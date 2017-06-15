package services

class FilenameGenerator {
  def generateFileName(): String = {
    def uuid = java.util.UUID.randomUUID.toString
    var name: String = uuid +".scala"
    name
  }
}