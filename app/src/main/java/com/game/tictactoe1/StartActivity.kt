package com.game.tictactoe1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import android.widget.GridLayout
import androidx.core.content.ContextCompat

class StartActivity : AppCompatActivity() {

    private lateinit var statusText: TextView
    private lateinit var restButton: Button
    private lateinit var gridLayout: GridLayout
    private val buttons = Array(3) { arrayOfNulls<Button>(3) }
    private var gameBoard = Array(3) { arrayOfNulls<String>(3) }
    private var gameActive = true
    private var roundCount = 0
    private var currentPlayer = "X"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start_button)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        statusText = findViewById(R.id.statusText)
        restButton = findViewById(R.id.resetButton)
        gridLayout = findViewById(R.id.gridLayout)

        initalizeBoard()
        restButton.setOnClickListener {
            restGame()
        }
    }


    private fun initalizeBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                val button = Button(this).apply {
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 0
                        height = 0
                        columnSpec = GridLayout.spec(j, 1f)
                        rowSpec = GridLayout.spec(i, 1f)
                        setMargins(8, 8, 8, 8)
                    }
                    textSize = 48f
                    setBackgroundColor(ContextCompat.getColor(context, R.color.button_bg))
                    setOnClickListener { onCellClick(i, j, this) }
                }
                buttons[i][j] = button
                gridLayout.addView(button)
            }

        }
        updateStatus()
    }

    private fun updateStatus() {
        statusText.text = "Player $currentPlayer turn"
    }


    private fun onCellClick(row: Int, col: Int, button: Button) {

        if (!gameActive || gameBoard[row][col] != null) return
        gameBoard[row][col] = currentPlayer
        button.text = currentPlayer
        button.isEnabled = false
        roundCount++
        if (checkWinner()) {
            gameActive = false
            statusText.text = "Player ${this.currentPlayer} won"
            disableBoard()
            highlightWiningCells()

        } else if (roundCount == 9) {
            gameActive = false
            statusText.text = "Its a tie !"
            disableBoard()
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            updateStatus()
        }


    }

    private fun highlightWiningCells() {
        val winColor = ContextCompat.getColor(this, R.color.win_color)
        //rows
        for (i in 0..2)
        {
            if (gameBoard[i][0] != null &&
                        gameBoard[i][0] == gameBoard[i][1] &&
                        gameBoard[i][1] == gameBoard[i][2]){
                buttons[i][0]?.setBackgroundColor(winColor)
                buttons[i][1]?.setBackgroundColor(winColor)
                buttons[i][2]?.setBackgroundColor(winColor)

            }
        }
        //cols
        for(j in 0..2) {
            if (gameBoard[0][j] != null &&
                gameBoard[0][j] == gameBoard[1][j] &&
                gameBoard[1][j] == gameBoard[2][j]
            ) {
                buttons[0][j]?.setBackgroundColor(winColor)
                buttons[1][j]?.setBackgroundColor(winColor)
                buttons[2][j]?.setBackgroundColor(winColor)
            }
        }
        //others
        if (gameBoard[0][0] != null &&
            gameBoard[0][0] == gameBoard[1][1] &&
            gameBoard[1][1] == gameBoard[2][2]) {
            buttons[0][0]?.setBackgroundColor(winColor)
            buttons[1][1]?.setBackgroundColor(winColor)
            buttons[2][2]?.setBackgroundColor(winColor)
        }

        if (gameBoard[0][2] != null &&
            gameBoard[0][2] == gameBoard[1][1] &&
            gameBoard[1][1] == gameBoard[2][0]) {
            buttons[0][2]?.setBackgroundColor(winColor)
            buttons[1][1]?.setBackgroundColor(winColor)
            buttons[2][0]?.setBackgroundColor(winColor)
        }

    }

    private fun disableBoard() {
        for (i in 0..2) {
            for (j in 0..2) {

                buttons[i][j]?.isEnabled = false
            }
        }
    }


    private fun checkWinner(): Boolean {
        //check rows
        for (i in 0..2) {
            if (gameBoard[i][0] != null &&
                gameBoard[i][0] == gameBoard[i][1] &&
                gameBoard[i][1] == gameBoard[i][2]
            ) {
                return true
            }
        }
        //check cols
        for (j in 0..2) {
            if (gameBoard[0][j] != null &&
                gameBoard[0][j] == gameBoard[1][j] &&
                gameBoard[1][j] == gameBoard[2][j]
            ) {
                return true
            }
        }
        //check another
        if (gameBoard[0][0] != null &&
            gameBoard[0][0] == gameBoard[1][1] &&
            gameBoard[1][1] == gameBoard[2][2]
        ) {
            return true
        }
        if (gameBoard[0][2] != null &&
            gameBoard[0][2] == gameBoard[1][1] &&
            gameBoard[1][1] == gameBoard[2][0]
        ) {
            return true

        }
        return false
    }


    private fun restGame() {
        currentPlayer = "X"
        gameActive = true
        roundCount = 0
        gameBoard = Array(3) { arrayOfNulls<String>(3) }
        val defaultColor = ContextCompat.getColor(this, R.color.button_bg)
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j]?.apply {
                    text = ""
                    isEnabled = true
                    setBackgroundColor(defaultColor)
                }

            }
        }
    }
}