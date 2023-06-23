package com.test.android48_ex01

class DataClass {

    companion object {
        var personList = mutableListOf<PersonInfo>()
    }
}

class PersonInfo(var name:String, var date:String, var gender:String, var hobbyList:MutableList<String>)