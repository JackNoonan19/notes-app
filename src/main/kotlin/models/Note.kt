package models

data class Note(var noteTitle: String,
                var notePriority: Int,
                var noteCategory: String,
                var isNoteFormal: Boolean,
                var isNoteNonFormal: Boolean,
                var isNoteArchived: Boolean) {

}