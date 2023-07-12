package com.example.ive.utils

import androidx.navigation.NavDestination

fun isActiveFragment(currentId:Int, id:Int):Boolean{
    return currentId == id
}