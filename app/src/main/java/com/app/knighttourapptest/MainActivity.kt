package com.app.knighttourapptest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnBoardClick {


    lateinit var rvBoards: RecyclerView
    lateinit var layoutManager: GridLayoutManager
    lateinit var adapter: ChessBoardsAdapter


    private val KnightsMovement = arrayOf(
        intArrayOf(1, -2),
        intArrayOf(2, -1),
        intArrayOf(2, 1),
        intArrayOf(1, 2),
        intArrayOf(-1, 2),
        intArrayOf(-2, 1),
        intArrayOf(-2, -1),
        intArrayOf(-1, -2)
    )


    internal var data = ArrayList<ChessBoardModel>()
    internal var count = 1
    internal var rondom: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvBoards = findViewById(R.id.rv_chess_boards)
        layoutManager = GridLayoutManager(applicationContext, 8)

        for (i in 0..7) {

            for (j in 0..7) {
                val model = ChessBoardModel()
                model.i = i
                model.j = j
                model.available = false
                model.isVisited = false
                model.count = 1
                model.position = ((i * 8) + (j + 1) - 1)
                data.add(model)
                //                stack.push(model);
            }
        }

        rondom = 0

        data[rondom].selected = true
//        data.get(rondom).setVisited(true);
        data[rondom].position = rondom
        data[rondom].parent = null
        data[rondom].count = 1
//        visited[data[rondom].i][data[rondom].j]
        adapter = ChessBoardsAdapter(data, applicationContext, this)
        rvBoards.adapter = adapter
        rvBoards.layoutManager = layoutManager
        adapter.notifyDataSetChanged()
        startKnightTour()

//        dfs(0)
//DFS()
    }

    override fun onChessBoardClick(position: Int, model: ChessBoardModel) {

        data[position].selected = true
        data[position].isVisited = true
        data[position].count = count
        if (getAvailables(model).size == 0) {
            count = 1
            for (i in 0..63) {
                data[i].isVisited = false
            }
            getAvailables(model)
        }
        count++

//        getAvailables(model)

//        Log.e("Availables" , )
    }

    override fun onAvailableBoardClick(position: Int, model: ChessBoardModel) {
        for (i in 0..63) {
            data[i].available = false
            data[i].selected = false
        }
//        data[position].available = false
//        data[position].count = data[position].parent!!.count+1
        data[position].isVisited = true
        onChessBoardClick(position, model)

//        data[position].
    }


    fun getAvailables(model: ChessBoardModel): ArrayList<ChessBoardModel> {
        val availables: ArrayList<ChessBoardModel> = ArrayList()

        for (m in KnightsMovement) {

            val x = model.i + m[0]
            val y = model.j + m[1]
            if (isLegalMove(x, y)) {
                for (i in 0..63) {
                    if (data[i].i == x && data[i].j == y) {
                        data[i].available = true
                        data[i].parent = model
                        availables.add(data[i])
                    }
                }
            }

        }
        adapter.notifyDataSetChanged()
        return availables
    }


    fun isLegalMove(x: Int, y: Int): Boolean {
        return !(x < 0 || x > 7 || y < 0 || y > 7)
    }


    val N = 8

    fun isValid(i: Int, j: Int, sol: Array<IntArray>): Boolean {
        if (i >= 1 && i <= N && j >= 1 && j <= N) {
            if (sol[i][j] == -1)
                return true
        }
        return false
    }

    fun knightTour(sol: Array<IntArray>, i: Int, j: Int, stepCount: Int, xMove: IntArray, yMove: IntArray): Boolean {
        if (stepCount == N * N)
            return true

        for (k in 0..7) {
            val nextI = i + xMove[k]
            val nextJ = j + yMove[k]

            if (isValid(nextI, nextJ, sol)) {
                sol[nextI][nextJ] = stepCount
                if (knightTour(sol, nextI, nextJ, stepCount + 1, xMove, yMove))
                    return true
                sol[nextI][nextJ] = -1 // backtracking
            }
        }

        return false
    }

    fun startKnightTour(): Boolean {
        val sol = Array(N + 1) { IntArray(N + 1) }

        for (i in 1..N) {
            for (j in 1..N) {
                sol[i][j] = -1
            }
        }

        val xMove = intArrayOf(2, 1, -1, -2, -2, -1, 1, 2)
        val yMove = intArrayOf(1, 2, 2, 1, -1, -2, -2, -1)




        sol[1][1] = 0 // placing knight at cell(1, 1)
        if (knightTour(sol, 1, 1, 1, xMove, yMove)) {
//            count = 1
            for (i in 1..N) {
                for (j in 1..N) {
                    val pos = (((i - 1) * 8) + ((j - 1) + 1) - 1)
//                    Log.e("POSITION" , pos.toString() + " VALUE  "  + sol[i][j])
                    data[pos].count = sol[i][j] + 1
                    data[pos].isVisited = true
                    data[pos].selected = false
                    if (sol[i][j] == 63) {
                        data[pos].selected = true
                    }
//                    print(sol[i][j].toString() + "\t")
                }

//                println("\n")
            }
            adapter.notifyDataSetChanged()
            return true
        }
        return false


    }


}
