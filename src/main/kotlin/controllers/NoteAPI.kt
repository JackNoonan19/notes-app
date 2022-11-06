package controllers

import models.Note
import persistence.Serializer

class NoteAPI(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var notes = ArrayList<Note>()
    private var notesActive = ArrayList<Note>()
    fun add(note: Note): Boolean {
        return notes.add(note)
    }



   /*fun listAllNotes(): String {
        return if (notes.isEmpty()) {
            "No notes stored"
        }else {
            var listOfNotes = ""
            for (i in notes.indices){
                listOfNotes += "${i}: ${notes[i]} \n"
            }
            listOfNotes
        }
    }*/

    fun listAllNotes(): String =
        if  (notes.isEmpty()) "No notes stored"
        else formatListString(notes)

    /*fun listActiveNotes(): String {
        return if (numberOfActiveNotes()== 0) {
            "No notes stored are active"
        } else {
            var listOfActiveNotes = ""
            for (note in notes){
                if (!note.isNoteArchived){
                    listOfActiveNotes += "${notes.indexOf(note)}:$note \n"
                }
            }
            listOfActiveNotes
        }
    }*/

    fun listActiveNotes(): String =
        if  (numberOfActiveNotes() == 0)  "No active notes stored"
        else formatListString(notes.filter { note -> !note.isNoteArchived})

    /*fun listArchivedNotes(): String {
        return if (numberOfArchivedNotes() == 0) {
            "No archived notes stored"
        } else {
            var listOfArchivedNotes = ""
            for (note in notes) {
                if (note.isNoteArchived) {
                    listOfArchivedNotes +="${notes.indexOf(note)}: $note \n"
                }
            }
            listOfArchivedNotes
        }
    }*/


    fun listToDoNotes(): String =
    if  (numberOfToDoNotes() == 0)  "No notes marked TO DO"
    else formatListString(notes.filter { note -> !note.todoNote})

    fun listDoneNotes(): String =
        if  (numberOfDoneNotes() == 0)  "No notes marked DONE"
        else formatListString(notes.filter { note -> !note.isNoteDone})


    fun listDoingNotes(): String =
        if  (numberOfDoingNotes() == 0)  "No notes marked DOING"
        else formatListString(notes.filter { note -> !note.doingNote})




    fun numberOfArchivedNotes(): Int = notes.count { note: Note -> note.isNoteArchived }
    fun numberOfActiveNotes(): Int = notes.count { note: Note -> !note.isNoteArchived }
    fun numberOfFormalNotes(): Int = notes.count { note: Note -> note.isNoteFormal }
    fun numberOfNonFormalNotes(): Int = notes.count { note: Note -> !note.isNoteNonFormal }

    fun numberOfToDoNotes(): Int = notes.count { note: Note -> !note.todoNote }
    fun numberOfDoneNotes(): Int = notes.count { note: Note -> !note.isNoteDone }
    fun numberOfDoingNotes(): Int = notes.count { note: Note -> !note.doingNote }

    fun listNotesBySelectedPriority(priority: Int): String =
        if (notes.isEmpty()) "No notes stored"
        else {
            val listOfNotes = formatListString(notes.filter{ note -> note.notePriority == priority})
            if (listOfNotes.equals("")) "No notes with priority: $priority"
            else "${numberOfNotesByPriority(priority)} notes with priority $priority: $listOfNotes"
        }


    fun numberOfNotesByPriority(priority: Int): Int = notes.count { p: Note -> p.notePriority == priority }






    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes)) {
            notes[index]
        } else null
    }

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun deleteNote(indexToDelete: Int): Note? {
        return if (isValidListIndex(indexToDelete, notes)) {
            notes.removeAt(indexToDelete)
        } else null
    }

    fun updateNote(indexToUpdate: Int, note: Note?): Boolean {
        //find the note object by the index number
        val foundNote = findNote(indexToUpdate)

        //if the note exists, use the note details passed as parameters to update the found note in teh ArrayList
        if ((foundNote != null) && (note != null)) {
            foundNote.noteTitle = note.noteTitle
            foundNote.notePriority = note.notePriority
            foundNote.noteCategory = note.noteCategory
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }

    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, notes)
    }

    fun archiveNote(indexToArchive: Int): Boolean {
        if (isValidIndex(indexToArchive)) {
            val noteToArchive = notes[indexToArchive]
            if (!noteToArchive.isNoteArchived) {
                noteToArchive.isNoteArchived = true
                return true
            }
        }
        return false
    }

    @Throws(Exception::class)
    fun load() {
        notes = serializer.read() as ArrayList<Note>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(notes)
    }
    fun listArchivedNotes(): String =
        if  (numberOfArchivedNotes() == 0) "No archived notes stored"
        else formatListString(notes.filter { note -> note.isNoteArchived})

    fun listFormalNotes(): String =
        if  (numberOfFormalNotes() == 0) "No formal notes stored"
        else formatListString(notes.filter { note -> note.isNoteFormal})

    fun listNonFormalNotes(): String =
        if  (numberOfNonFormalNotes() == 0) "No non-formal notes stored"
        else formatListString(notes.filter { note -> note.isNoteNonFormal})


    fun makeNoteFormal(indexToFormal: Int): Boolean {
        if (isValidIndex(indexToFormal)) {
            val noteToFormal = notes[indexToFormal]
            if (!noteToFormal.isNoteFormal) {
                noteToFormal.isNoteFormal = true
                return true
            }
        }
        return false
    }
    fun makeNoteNonFormal(indexToNotNonFormal: Int): Boolean {
        if (isValidIndex(indexToNotNonFormal)) {
            val noteToNonFormal = notes[indexToNotNonFormal]
            if (!noteToNonFormal.isNoteNonFormal) {
                noteToNonFormal.isNoteNonFormal = true
                return true
            }
        }
        return false
    }



    fun makeNoteNotFormal(indexToNotFormal: Int): Boolean {
        if (isValidIndex(indexToNotFormal)) {
            val noteToNotFormal = notes[indexToNotFormal]
            if (noteToNotFormal.isNoteFormal) {
                noteToNotFormal.isNoteFormal = false
                return true
            }
        }
        return false
    }
    fun makeNoteNotNonFormal(indexToNonFormal: Int): Boolean {
        if (isValidIndex(indexToNonFormal)) {
            val noteToNotNonFormal = notes[indexToNonFormal]
            if (noteToNotNonFormal.isNoteNonFormal) {
                noteToNotNonFormal.isNoteNonFormal = false
                return true
            }
        }
        return false
    }





    fun makeNoteDoing(indexToDoing: Int): Boolean {
        if (isValidIndex(indexToDoing)) {
            val noteDoing = notes[indexToDoing]
            if (!noteDoing.doingNote) {
                noteDoing.doingNote = true
                return true
            }
        }
        return false
    }
    fun makeNoteTodo(indexToTodo: Int): Boolean {
        if (isValidIndex(indexToTodo)) {
            val noteToDo = notes[indexToTodo]
            if (!noteToDo.todoNote) {
                noteToDo.todoNote = true
                return true
            }
        }
        return false
    }
    fun makeNoteDone(indexToDone: Int): Boolean {
        if (isValidIndex(indexToDone)) {
            val noteDone = notes[indexToDone]
            if (!noteDone.isNoteDone) {
                noteDone.isNoteDone = true
                return true
            }
        }
        return false
    }


    fun makeNoteNotDoing(indexToDoing: Int): Boolean {
        if (isValidIndex(indexToDoing)) {
            val noteDoing = notes[indexToDoing]
            if (noteDoing.doingNote) {
                noteDoing.doingNote = false
                return true
            }
        }
        return false
    }
    fun makeNoteNotTodo(indexToTodo: Int): Boolean {
        if (isValidIndex(indexToTodo)) {
            val noteToDo = notes[indexToTodo]
            if (noteToDo.todoNote) {
                noteToDo.todoNote = false
                return true
            }
        }
        return false
    }
    fun makeNoteNotDone(indexToDone: Int): Boolean {
        if (isValidIndex(indexToDone)) {
            val noteDone = notes[indexToDone]
            if (!noteDone.isNoteDone) {
                noteDone.isNoteDone = false
                return true
            }
        }
        return false
    }



        fun numberOfNotes(): Int =
        notes.size


    fun searchByCategory (searchString : String) =
        formatListString(
            notes.filter { note -> note.noteCategory.contains(searchString, ignoreCase = true) })

   /* fun searchByPriority (searchString : String) =
        formatListString(
            notes.filter { note -> note.notePriority.contains(searchString, ignoreCase = true) })*/

    fun searchByTitle (searchString : String) =
        formatListString(
            notes.filter { note -> note.noteTitle.contains(searchString, ignoreCase = true) })

    private fun formatListString(notesToFormat : List<Note>) : String =
        notesToFormat
            .joinToString (separator = "\n") { note ->
                notes.indexOf(note).toString() + ": " + note.toString() }

}















