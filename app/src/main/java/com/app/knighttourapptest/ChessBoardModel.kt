package com.app.knighttourapptest

class ChessBoardModel {
     var i: Int = 0
     var j: Int = 0
     var isVisited: Boolean = false
     var available: Boolean = false
     var selected: Boolean = false
     var count: Int = 0
     var position: Int = 0
     var parent: ChessBoardModel? = null
}