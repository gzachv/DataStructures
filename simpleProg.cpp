/* 
 * A very simple C++ program demonstrating simple console I/O.
 *
 * FILE NAME: simpleProg.cpp
 *
 * Note: type the commands below at a Linux prompt
 *
 * TO COMPILE:
 *
 *     g++ simpleProg.cpp
 *
 *
 *
 *
 *
 * TO RUN: 
 *
 *     a.out
 *
 */

#include <iostream>

using namespace std;



int main( ) {


    cout << "Please enter a number and a letter: " << flush;


    int a;
    char b;

    cin >> a >> b;
    cout << endl;


    cout << "The number you entered was: " << a << endl;
    cout << "The letter you entered was: " << b << endl;


    return 0;
}
