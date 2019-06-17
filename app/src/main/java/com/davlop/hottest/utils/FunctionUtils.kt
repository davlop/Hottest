package com.davlop.hottest.utils

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.davlop.hottest.data.model.Person
import com.davlop.hottest.ui.ProductListAdapter

fun <T1: Any, T2: Any, R: Any> doubleNullCheckLet(p1: T1?, p2: T2?, executeBlock: (T1, T2)->R?): R? {
    return if (p1 != null && p2 != null) executeBlock(p1, p2) else null
}

fun Fragment.startBrowserIntent(url: String) {
    if (!url.startsWith("http://", true) && !url.startsWith("https://", true)) return

    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}

fun View.revertVisibility() {
    if (this.visibility == View.GONE) this.visibility = View.VISIBLE
    else if (this.visibility == View.VISIBLE) this.visibility = View.GONE
}

fun RecyclerView.setUpWithProductListAdapter(listAdapter: ProductListAdapter) {
    this.apply {
        setHasFixedSize(true)
        adapter = listAdapter
    }
}

fun functional1 {
    val array = arrayOf(1, "2", 3, 4, "5", 6.0)
    array.forEach { print(it) }
}

fun functional2 {
    val array = arrayOf(1, 2, 3, 4, 5, 6)
    array.map { it + 1) }
        .forEach { print(it) }
}

// Return type is inferred (Int)
fun double(x: Int) = x * 2

// No need to specify Unit (void) return type
fun addToList(list: MutableList<Int>, element: Int) {
    list.add(element)
}

fun modifyPerson() {
    // no new keyword
    val person = Person("David Lopez Martinez", 23, "SFSU Campus")
    person.age = 34





    person.address = "San Jose"



    // can treat fields normally, no need to call setters or getters
    person.address = "San Jose"
    println(person.address)


}



// extension functions => adds a swap function to MutableList<Int> class
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' corresponds to the list
    this[index1] = this[index2]
    this[index2] = tmp
}




















fun showNullability(argument: CharSequence) {
    val nullableName: String? = "David Lopez"
    val notNullName: String = "David Lopez"

    // can call normally, can be certain that no NPE will be thrown
    notNullName.toUpperCase()

    // can't call normally => nullableName may be null and therefore throw NPE
    nullableName.toUpperCase()




    // fix it (if nullableName is null, toUpperCase won't be called)
    nullableName?.toUpperCase()



    // smart casts
    if (argument is String) {
        argument.toUpperCase()
    }





    // string templates
    println("2*2=${2*2}")
    println("argument passed is $argument")
    var x: Int = 3

    val validNumbers = arrayOf(1)




    // when replaces switch statements
    // 1
    when (x) {
        0, 1 -> print("x == 0 or x == 1")
        else -> print("otherwise")
    }

    // 2 - can use is keyword
    when (x) {
        is Int -> print(x)
        else -> print("Error")
    }

    // 3 - can use ranges
    when (x) {
        in 1..10 -> print("x is in the range")
        in validNumbers -> print("x is valid")
        !in 10..20 -> print("x is outside the range")
        else -> print("none of the above")
    }





    // operator overloading - overloading "+" operator to user-defined class Vector
    data class Vec(val x: Float, val y: Float) {
        operator fun plus(v: Vec) = Vec(x + v.x, y + v.y)
    }

    val v = Vec(2f, 3f) + Vec(4f, 1f)




    // ranges - add code readablity
    val persons = List<Person>





    // more functional style
    // lambdas - each of these functions accepts another function as argument
    persons
        .filter { it.age >= 18 }
        .sortedBy { it.name }
        .map { it.address }
        .forEach { print(it) }




    // Anko - DSL creation thanks to lambdas
    verticalLayout {
        padding = dip(30)
        editText {
            hint = “Name”
            textSize = 24f
        }
        editText {
            hint = “Password”
            textSize = 24f
        }
        button(“Login”) {
        textSize = 26f
        }
    }








    // type inference => no need to declare val or var type
    val name = "David Lopez" // name.class = String
    val age = 23 // age.class = Int
    var address = "SFSU Campus" // address.class = String


}
