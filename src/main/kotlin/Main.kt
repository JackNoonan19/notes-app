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
       >|   7) Search by Category |
       >|   8) Changes Notes      |
       >|   10)  Save             |
       >|   11) Load              |
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
            8 -> changeNotes()
           // 8 -> searchNotesPri()
            10 -> save()
            11 -> load()
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
    val isAdded = noteAPI.add(Note(noteTitle, notePriority, noteCategory, false, true,false,false,false, false))

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
                  > |   4) View FORMAL notes       |
                  > |   5) View NON-FORMAL notes   |
                  > |   6) View To DO Notes        |
                  > |   7) View Doing Notes        |
                  > |   8) View Done Notes         |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

            when (option) {
                1 -> listAllNotes();
                2 -> listActiveNotes();
                3 -> listArchivedNotes();
                4 -> listFormalNotes();
                5 -> listNonFormalNotes();
                6 -> listTodoNotes();
                7 -> listDoneNotes();
                8 -> listDoingNotes()
                else -> println("Invalid option entered: " + option);
            }
        } else {
            println("Option Invalid - No notes stored");
        }
    }




fun changeNotes(){
    //logger.info{"listNotes() function invoked"}
    if (noteAPI.numberOfNotes() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL notes          |
                  > |   2) Change Formality        |
                  > |   3) Change Status           |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllNotes();
            2 -> noteFormality();
            3 -> noteStatus();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Option Invalid - No notes stored");
    }
}

fun noteFormality(){
    if (noteAPI.numberOfNotes() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL notes          |
                  > |   2) Make Formal             |
                  > |   3) Make Not Formal         |
                  > |   4) Make Non Formal         |
                  > |   5) Make Not Non Formal     |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllNotes();
            2 -> makeNoteFormal();
            3 -> makeNoteNotFormal();
            4 -> makeNoteNonFormal();
            5 -> makeNoteNotNonFormal();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Option Invalid - No notes stored");
    }
}
fun noteStatus(){
    if (noteAPI.numberOfNotes() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL notes          |
                  > |   2) Make TO DO              |
                  > |   3) Make Not TO DO          |
                  > |   4) Make Doing              |
                  > |   5) Make Not Doing          |
                  > |   6) Make Done               |
                  > |   7) Make Not Done           |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllNotes();
            2 -> makeNoteToDo();
            3 -> makeNoteNotToDo();
            4 -> makeNoteDoing();
            5 -> makeNoteNotDoing();
            6 -> makeNoteDone();
            7 -> makeNoteNotDone();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Option Invalid - No notes stored");
    }
}

fun updateNote(){
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note if notes exist
        val indexToUpdate = readNextInt("Enter the index of the note to update: ")
        if (noteAPI.isValidIndex(indexToUpdate)) {
            val noteTitle = readNextLine("Enter a title for the note: ")
            val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            val noteCategory = readNextLine("Enter a category for the note: ")

            //pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (noteAPI.updateNote(indexToUpdate, Note(noteTitle, notePriority, noteCategory, false, true,false,false,false, false))){
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


fun listFormalNotes() {
    println(noteAPI.listFormalNotes())
}

fun listNonFormalNotes() {
    println(noteAPI.listNonFormalNotes())
}

fun listTodoNotes() {
    println(noteAPI.listToDoNotes())
}

fun listDoingNotes() {
    println(noteAPI.listDoingNotes())
}

fun listDoneNotes() {
    println(noteAPI.listDoneNotes())
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

fun makeNoteFormal() {
    listFormalNotes()
        //only ask the user to choose the note to archive if active notes exist
        val indexToFormal = readNextInt("Enter the index of the note to make formal: ")
        //pass the index of the note to NoteAPI for archiving and check for success.
        if (noteAPI.makeNoteFormal(indexToFormal)) {
            println("Change Successful!")
        } else {
            println("Change NOT Successful")
        }
}

fun makeNoteNonFormal() {
    listNonFormalNotes()
        //only ask the user to choose the note to archive if active notes exist
        val indexToNonFormal = readNextInt("Enter the index of the note to make non formal: ")
        //pass the index of the note to NoteAPI for archiving and check for success.
        if (noteAPI.makeNoteNonFormal(indexToNonFormal)) {
            println("Change Successful!")
        } else {
            println("Change NOT Successful")
        }
}



fun makeNoteNotFormal() {
    listFormalNotes()
    //only ask the user to choose the note to archive if active notes exist
    val indexToNotFormal = readNextInt("Enter the index of the note to make formal: ")
    //pass the index of the note to NoteAPI for archiving and check for success.
    if (noteAPI.makeNoteNotFormal(indexToNotFormal)) {
        println("Change Successful!")
    } else {
        println("Change NOT Successful")
    }
}

fun makeNoteNotNonFormal() {
    listNonFormalNotes()
    //only ask the user to choose the note to archive if active notes exist
    val indexToNotNonFormal = readNextInt("Enter the index of the note to make non formal: ")
    //pass the index of the note to NoteAPI for archiving and check for success.
    if (noteAPI.makeNoteNotNonFormal(indexToNotNonFormal)) {
        println("Change Successful!")
    } else {
        println("Change NOT Successful")
    }
}





fun makeNoteToDo() {
    listTodoNotes()
    //only ask the user to choose the note to archive if active notes exist
    val indexToToDo = readNextInt("Enter the index of the note to make non formal: ")
    //pass the index of the note to NoteAPI for archiving and check for success.
    if (noteAPI.makeNoteTodo(indexToToDo)) {
        println("Change Successful!")
    } else {
        println("Change NOT Successful")
    }
}


fun makeNoteDone() {
    listDoneNotes()
    //only ask the user to choose the note to archive if active notes exist
    val indexToDone = readNextInt("Enter the index of the note to make non formal: ")
    //pass the index of the note to NoteAPI for archiving and check for success.
    if (noteAPI.makeNoteDone(indexToDone)) {
        println("Change Successful!")
    } else {
        println("Change NOT Successful")
    }
}

fun makeNoteDoing() {
    listDoingNotes()
    //only ask the user to choose the note to archive if active notes exist
    val indexToDoing = readNextInt("Enter the index of the note to make non formal: ")
    //pass the index of the note to NoteAPI for archiving and check for success.
    if (noteAPI.makeNoteDoing(indexToDoing)) {
        println("Change Successful!")
    } else {
        println("Change NOT Successful")
    }
}





fun makeNoteNotToDo() {
    listTodoNotes()
    //only ask the user to choose the note to archive if active notes exist
    val indexToNotToDo = readNextInt("Enter the index of the note to make non formal: ")
    //pass the index of the note to NoteAPI for archiving and check for success.
    if (noteAPI.makeNoteNotTodo(indexToNotToDo)) {
        println("Change Successful!")
    } else {
        println("Change NOT Successful")
    }
}


fun makeNoteNotDone() {
    listDoneNotes()
    //only ask the user to choose the note to archive if active notes exist
    val indexToNotDone = readNextInt("Enter the index of the note to make non formal: ")
    //pass the index of the note to NoteAPI for archiving and check for success.
    if (noteAPI.makeNoteNotDone(indexToNotDone)) {
        println("Change Successful!")
    } else {
        println("Change NOT Successful")
    }
}

fun makeNoteNotDoing() {
    listDoingNotes()
    //only ask the user to choose the note to archive if active notes exist
    val indexToNotDoing = readNextInt("Enter the index of the note to make non formal: ")
    //pass the index of the note to NoteAPI for archiving and check for success.
    if (noteAPI.makeNoteNotDoing(indexToNotDoing)) {
        println("Change Successful!")
    } else {
        println("Change NOT Successful")
    }
}