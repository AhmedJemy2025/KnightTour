package com.app.knighttourapptest

 interface OnBoardClick {

    fun onChessBoardClick(position: Int, model: ChessBoardModel)
    fun onAvailableBoardClick(position: Int, model: ChessBoardModel)
}
