package models

data class Note(var noteTitle: String,
                var notePriority: Int,
                var noteCategory: String,
                var isNoteFormal: Boolean,
                var isNoteNonFormal: Boolean,
                var todoNote: Boolean,
                var doingNote: Boolean,
                var isNoteDone: Boolean,
                var isNoteArchived: Boolean) {

}