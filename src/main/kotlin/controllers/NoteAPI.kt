package controllers

import models.Note

class NoteAPI {
    private var notes = ArrayList<Note>()
    private var notesActive = ArrayList<Note>()
    fun add(note: Note): Boolean {
        return notes.add(note)
    }



    fun listAllNotes(): String {
        return if (notes.isEmpty()) {
            "No notes stored"
        }else {
            var listOfNotes = ""
            for (i in notes.indices){
                listOfNotes += "${i}: ${notes[i]} \n"
            }
            listOfNotes
        }
    }


    fun listActiveNotes(): String {
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
    }

    fun listArchivedNotes(): String {
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
    }

    fun numberOfArchivedNotes(): Int {
        //helper method to determine how many archived notes there are
        var counter = 0
            for (note in notes) {
              if (note.isNoteArchived) {
                counter++
            }
        }
     return counter
    }

    fun numberOfActiveNotes(): Int {
        //helper method to determine how many active notes there are
        var counter = 0
            for (note in notes) {
              if (!note.isNoteArchived) {
                counter++
            }
        }
     return counter
    }
    fun listNotesBySelectedPriority(priority: Int): String {
        return if (notes.isEmpty()) {
            "No notes stored"
        } else {
            var listOfNotes = ""
            for (i in notes.indices) {
                if (notes[i].notePriority == priority) {
                    listOfNotes +=
                        """$i: ${notes[i]}
                        """.trimIndent()
                }
            }
            if (listOfNotes.equals("")) {
                "No notes with priority: $priority"
            } else {
                "${numberOfNotesByPriority(priority)} notes with priority $priority: $listOfNotes"
            }
        }
    }

    fun numberOfNotesByPriority(priority: Int): Int {
        var counter = 0
        for (note in notes) {
            if (note.notePriority == priority) {
                counter++
            }
        }
        return counter
    }


    fun numberOfNotes(): Int {
        return notes.size
    }

    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes)) {
            notes[index]
        } else null
    }

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
}















