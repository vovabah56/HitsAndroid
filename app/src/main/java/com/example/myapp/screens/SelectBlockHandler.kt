package com.example.myapp.screens

import com.example.myapp.model.Block
import com.example.myapp.model.DoWhileBlock
import com.example.myapp.model.ElseBlock
import com.example.myapp.model.ForBlock
import com.example.myapp.model.IfBlock
import com.example.myapp.model.InputBlock
import com.example.myapp.model.PrintBlock
import com.example.myapp.model.VarBlock
import com.example.myapp.model.WhileBlock


fun clickHandler(buttonType: ButtonType, listOfBlocks: MutableList<Block>) {
    var lastId = 0
    if (listOfBlocks.size > 0) {
        lastId = listOfBlocks.last().id + 1
    }

    if (buttonType == ButtonType.Variable) {
        listOfBlocks.add(Block(lastId, VarBlock("", "")))
    }

    if (buttonType == ButtonType.Print) {
        listOfBlocks.add(Block(lastId, PrintBlock("")))
    }

    if (buttonType == ButtonType.Input) {
        listOfBlocks.add(Block(lastId, InputBlock("")))
    }

    if (buttonType == ButtonType.If) {
        listOfBlocks.add(Block(lastId, IfBlock("", mutableListOf())))
    }

    if (buttonType == ButtonType.Else) {
        listOfBlocks.add(Block(lastId, ElseBlock(mutableListOf())))
    }

    if (buttonType == ButtonType.While) {
        listOfBlocks.add(Block(lastId, WhileBlock("", mutableListOf())))
    }

    if (buttonType == ButtonType.For) {
        listOfBlocks.add(Block(lastId, ForBlock("", "", mutableListOf())))
    }

    if (buttonType == ButtonType.DoWhile) {
        listOfBlocks.add(Block(lastId, DoWhileBlock("", mutableListOf())))
    }


}