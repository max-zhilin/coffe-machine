package machine

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    while(true) {
        CoffeeMachine.printPrompt()
        CoffeeMachine.userInput(scanner.next())
        if (CoffeeMachine.isShutingDown()) return
    }
}

object CoffeeMachine {
    var water = 400
    var milk = 540
    var coffee = 120
    var cups = 9
    var money = 550
    var state = State.CHOOSING_AN_ACTION

    enum class State {
        CHOOSING_AN_ACTION,
        CHOOSING_A_VARIANT_OF_COFFEE,
        ADDING_WATER,
        ADDING_MILK,
        ADDING_COFFEE,
        ADDING_CUPS,
        SHUT_DOWN,
    }

    fun isShutingDown(): Boolean {
        return state == State.SHUT_DOWN
    }

    fun printPrompt() {
        when (state) {
            State.CHOOSING_AN_ACTION -> print("Write action (buy, fill, take, remaining, exit):")
            State.CHOOSING_A_VARIANT_OF_COFFEE -> print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
            State.ADDING_WATER -> print("Write how many ml of water do you want to add:")
            State.ADDING_MILK -> print("Write how many ml of milk do you want to add:")
            State.ADDING_COFFEE -> print("Write how many grams of coffee beans do you want to add:")
            State.ADDING_CUPS -> print("Write how many disposable cups of coffee do you want to add:")
        }
    }

    fun userInput(input: String) {
        when (state) {
            State.CHOOSING_AN_ACTION -> when (input) {
                    "buy" -> state = State.CHOOSING_A_VARIANT_OF_COFFEE
                    "fill" -> state = State.ADDING_WATER
                    "take" -> take()
                    "remaining" -> printState()
                    "exit" -> state = State.SHUT_DOWN
                }
            State.CHOOSING_A_VARIANT_OF_COFFEE -> state = if(input == "back") {
                    State.CHOOSING_AN_ACTION
                } else {
                    when (input.toInt()) {
                        1 -> spendAndAccept(250, 0, 16, 4)
                        2 -> spendAndAccept(350, 75, 20, 7)
                        3 -> spendAndAccept(200, 100, 12, 6)
                    }
                    State.CHOOSING_AN_ACTION
                }
            State.ADDING_WATER -> {
                water += input.toInt()
                state = State.ADDING_MILK
            }
            State.ADDING_MILK -> {
                milk += input.toInt()
                state = State.ADDING_COFFEE
            }
            State.ADDING_COFFEE -> {
                coffee += input.toInt()
                state = State.ADDING_CUPS
            }
            State.ADDING_CUPS -> {
                cups += input.toInt()
                state = State.CHOOSING_AN_ACTION
            }
        }
    }

    fun take() {
        println("I gave you \$$money")
        money = 0
    }

    fun printState() {
        println("The coffee machine has:")
        println("$water of water")
        println("$milk of milk")
        println("$coffee of coffee beans")
        println("$cups of disposable cups")
        println("$money of money")
    }

    fun spendAndAccept(spendWater: Int, spendMilk: Int, spendCoffee: Int, acceptMoney: Int) {

        when {
            spendWater > water -> println("Sorry, not enough water!")
            spendMilk > milk -> println("Sorry, not enough milk!")
            spendCoffee > coffee -> println("Sorry, not enough coffee!")
            cups < 1 -> println("Sorry, not enough cups!")
            else -> {
                println("I have enough resources, making you a coffee!")
                water -= spendWater
                milk -= spendMilk
                coffee -= spendCoffee
                cups -= 1
                money += acceptMoney
            }
        }
    }
}
