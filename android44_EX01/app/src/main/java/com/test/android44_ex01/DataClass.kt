package com.test.android44_ex01

// 어떤 activity에서도 접근 가능하도록 정적 멤버 사용
class DataClass {

    companion object {
        // 과일 정보를 담을 클래스
        val fruitList = mutableListOf<Fruit>()
    }
}

class Fruit(var name:String,var number:Int, var region:String)