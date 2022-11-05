import controllers.NoteAPI
import models.Note
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.lang.System.exit
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

//private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
private val noteAPI = NoteAPI(JSONSerializer(File("notes.json")))
fun mainMenu() : Int {
    return ScannerInput.readNextInt("""
       >---------------------------
       >|    NOTE KEEPER APP      |
       >---------------------------
       >|NOTE MENU                |
       >|   1) Add a note         |
       >|   2) List all notes     |
       >|   3) Update a note      |
       >|   4) Delete a note      |
       >|   5) Archive a note     |
       >|   6) Search note        |
       >|   6) Search by Category |
       >|   6) Search by Priority |
       >|   7) Save               |
       >|   8) Load               |
       >---------------------------
       >|   0) Exit               |
       >---------------------------
   ==>> """.trimMargin(">"))
}

fun runMenu(){
    do {
        val option = mainMenu()
        when (option) {
            1 -> addNote()
            2 -> listNotes()
            3 -> updateNote()
            4 -> deleteNote()
            5 -> archiveNote()
            6 -> searchNotes()
            7 -> searchNotesCat()
           // 8 -> searchNotesPri()
            9 -> save()
            10 -> load()
            0 -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun addNote(){
    //logger.info{"addNote() function invoked"}
    val noteTitle = readNextLine("Enter a title for the note:")
    val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val noteCategory = readNextLine("Enter a category for the note: ")
    val isAdded = noteAPI.add(Note(noteTitle, notePriority, noteCategory, false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}
fun listNotes(){
    //logger.info{"listNotes() function invoked"}
        if (noteAPI.numberOfNotes() > 0) {
            val option = readNextInt(
                """
                  > --------------------------------
                  > |   1) View ALL notes          |
                  > |   2) View ACTIVE notes       |
                  > |   3) View ARCHIVED notes     |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

            when (option) {
                1 -> listAllNotes();
                2 -> listActiveNotes();
                3 -> listArchivedNotes();
                else -> println("Invalid option entered: " + option);
            }
        } else {
            println("Option Invalid - No notes stored");
        }
    }
fun updateNote(){
    //logger.info{"updateNote() function invoked"}
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note if notes exist
        val indexToUpdate = readNextInt("Enter the index of the note to update: ")
        if (noteAPI.isValidIndex(indexToUpdate)) {
            val noteTitle = readNextLine("Enter a title for the note: ")
            val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            val noteCategory = readNextLine("Enter a category for the note: ")

            //pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (noteAPI.updateNote(indexToUpdate, Note(noteTitle, notePriority, noteCategory, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}
fun deleteNote(){
    //logger.info{"deleteNote() function invoked"}
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the notes to delrete if notes exist
        val indexToDelete = readNextInt("Enter the index of the note to delete: ")
        //pass the index of the note to NoteAPI for deleting and check for success.
        val noteToDelete = noteAPI.deleteNote(indexToDelete)
        if (noteToDelete != null) {
            println("Delete Successful! Deleted Note: ${noteToDelete.noteTitle}")
        } else {
            println("Delete NOTE Successful")
        }
    }
}
fun exitApp(){
    logger.info{"exitApp() function invoked"}
    exit(0)
}



private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    runMenu()
}


fun save() {
    try {
        noteAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        noteAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun listAllNotes() {
    println(noteAPI.listAllNotes())
}

fun listArchivedNotes() {
    println(noteAPI.listArchivedNotes())
}

fun listActiveNotes() {
    println(noteAPI.listActiveNotes())
}

fun archiveNote() {
    listActiveNotes()
    if (noteAPI.numberOfActiveNotes() > 0) {
        //only ask the user to choose the note to archive if active notes exist
        val indexToArchive = readNextInt("Enter the index of the note to archive: ")
        //pass the index of the note to NoteAPI for archiving and check for success.
        if (noteAPI.archiveNote(indexToArchive)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}

fun searchNotes() {
    val searchTitle = readNextLine("Please enter a description to search notes using: ")
    val searchResults = noteAPI.searchByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}
fun searchNotesCat() {
    val searchCategory = readNextLine("Please enter a Category to search notes using: ")
    val searchResults = noteAPI.searchByCategory(searchCategory)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}


/*fun searchNotesPri() {
    val searchPriority = readNextLine("Please enter a priority level | 1-(low), 2, 3, 4, 5-(high) |: ")
    val searchResults = noteAPI.searchByPriority(searchPriority)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}
*/















